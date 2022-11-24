import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompraVenta } from 'app/shared/model/compra-venta.model';
import { CompraVentaService } from './compra-venta.service';
import { CompraVentaComponent } from './compra-venta.component';
import { CompraVentaDetailComponent } from './compra-venta-detail.component';
import { CompraVentaUpdateComponent } from './compra-venta-update.component';
import { CompraVentaDeletePopupComponent } from './compra-venta-delete-dialog.component';
import { ICompraVenta } from 'app/shared/model/compra-venta.model';

@Injectable({ providedIn: 'root' })
export class CompraVentaResolve implements Resolve<ICompraVenta> {
  constructor(private service: CompraVentaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompraVenta> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CompraVenta>) => response.ok),
        map((compraVenta: HttpResponse<CompraVenta>) => compraVenta.body)
      );
    }
    return of(new CompraVenta());
  }
}

export const compraVentaRoute: Routes = [
  {
    path: '',
    component: CompraVentaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'pruebaApp.compraVenta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompraVentaDetailComponent,
    resolve: {
      compraVenta: CompraVentaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaApp.compraVenta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompraVentaUpdateComponent,
    resolve: {
      compraVenta: CompraVentaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaApp.compraVenta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompraVentaUpdateComponent,
    resolve: {
      compraVenta: CompraVentaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaApp.compraVenta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const compraVentaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CompraVentaDeletePopupComponent,
    resolve: {
      compraVenta: CompraVentaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaApp.compraVenta.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
