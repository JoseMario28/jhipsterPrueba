import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocumento } from 'app/shared/model/documento.model';

type EntityResponseType = HttpResponse<IDocumento>;
type EntityArrayResponseType = HttpResponse<IDocumento[]>;

@Injectable({ providedIn: 'root' })
export class DocumentoService {
  public resourceUrl = SERVER_API_URL + 'api/documentos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/documentos';

  constructor(protected http: HttpClient) {}

  create(documento: IDocumento): Observable<EntityResponseType> {
    return this.http.post<IDocumento>(this.resourceUrl, documento, { observe: 'response' });
  }

  update(documento: IDocumento): Observable<EntityResponseType> {
    return this.http.put<IDocumento>(this.resourceUrl, documento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocumento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocumento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocumento[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
