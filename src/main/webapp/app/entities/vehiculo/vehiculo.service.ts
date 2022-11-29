import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVehiculo } from 'app/shared/model/vehiculo.model';

type EntityResponseType = HttpResponse<IVehiculo>;
type EntityArrayResponseType = HttpResponse<IVehiculo[]>;

@Injectable({ providedIn: 'root' })
export class VehiculoService {
  public resourceUrl = SERVER_API_URL + 'api/vehiculos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/vehiculos';

  constructor(protected http: HttpClient) {}

  create(vehiculo: IVehiculo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehiculo);
    return this.http
      .post<IVehiculo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehiculo: IVehiculo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehiculo);
    return this.http
      .put<IVehiculo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehiculo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehiculo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehiculo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(vehiculo: IVehiculo): IVehiculo {
    const copy: IVehiculo = Object.assign({}, vehiculo, {
      anno: vehiculo.anno != null && vehiculo.anno.isValid() ? vehiculo.anno.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.anno = res.body.anno != null ? moment(res.body.anno) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehiculo: IVehiculo) => {
        vehiculo.anno = vehiculo.anno != null ? moment(vehiculo.anno) : null;
      });
    }
    return res;
  }

  vehiculosFiltrados(marca: string, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehiculo[]>(`${this.resourceUrl}/filtrarVehiculo/${marca}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
}
