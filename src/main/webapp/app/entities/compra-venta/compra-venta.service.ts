import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompraVenta } from 'app/shared/model/compra-venta.model';

type EntityResponseType = HttpResponse<ICompraVenta>;
type EntityArrayResponseType = HttpResponse<ICompraVenta[]>;

@Injectable({ providedIn: 'root' })
export class CompraVentaService {
  public resourceUrl = SERVER_API_URL + 'api/compra-ventas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/compra-ventas';

  constructor(protected http: HttpClient) {}

  create(compraVenta: ICompraVenta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compraVenta);
    return this.http
      .post<ICompraVenta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(compraVenta: ICompraVenta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compraVenta);
    return this.http
      .put<ICompraVenta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompraVenta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompraVenta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompraVenta[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(compraVenta: ICompraVenta): ICompraVenta {
    const copy: ICompraVenta = Object.assign({}, compraVenta, {
      fechaVenta: compraVenta.fechaVenta != null && compraVenta.fechaVenta.isValid() ? compraVenta.fechaVenta.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaVenta = res.body.fechaVenta != null ? moment(res.body.fechaVenta) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((compraVenta: ICompraVenta) => {
        compraVenta.fechaVenta = compraVenta.fechaVenta != null ? moment(compraVenta.fechaVenta) : null;
      });
    }
    return res;
  }
}
