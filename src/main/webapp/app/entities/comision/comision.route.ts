import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Comision } from 'app/shared/model/comision.model';
import { ComisionService } from './comision.service';
import { ComisionComponent } from './comision.component';
import { ComisionDetailComponent } from './comision-detail.component';
import { ComisionUpdateComponent } from './comision-update.component';
import { ComisionDeletePopupComponent } from './comision-delete-dialog.component';
import { IComision } from 'app/shared/model/comision.model';

@Injectable({ providedIn: 'root' })
export class ComisionResolve implements Resolve<IComision> {
  constructor(private service: ComisionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IComision> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Comision>) => response.ok),
        map((comision: HttpResponse<Comision>) => comision.body)
      );
    }
    return of(new Comision());
  }
}

export const comisionRoute: Routes = [
  {
    path: '',
    component: ComisionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaApp.comision.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComisionDetailComponent,
    resolve: {
      comision: ComisionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaApp.comision.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComisionUpdateComponent,
    resolve: {
      comision: ComisionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaApp.comision.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComisionUpdateComponent,
    resolve: {
      comision: ComisionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaApp.comision.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const comisionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ComisionDeletePopupComponent,
    resolve: {
      comision: ComisionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaApp.comision.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
