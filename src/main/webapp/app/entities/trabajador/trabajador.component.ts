import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITotalVentas, ITrabajador } from 'app/shared/model/trabajador.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { TrabajadorService } from './trabajador.service';
import { CompraVentaService } from '../compra-venta';
import { element } from '@angular/core/src/render3';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComisionTrabajadorComponent } from './modal-comision-trabajador/modal-comision-trabajador.component';
import { VehiculoEditDialogComponent } from '../vehiculo/vehiculo-edit-dialog/vehiculo-edit-dialog.component';

@Component({
  selector: 'jhi-trabajador',
  templateUrl: './trabajador.component.html'
})
export class TrabajadorComponent implements OnInit, OnDestroy {
  currentAccount: any;
  trabajadors: ITrabajador[];

  totalVentas: ITotalVentas[] = [];

  error: any;
  success: any;
  eventSubscriber: Subscription;
  currentSearch: string;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    private modalService: NgbModal,
    protected compraVentaService: CompraVentaService,
    protected trabajadorService: TrabajadorService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  open(trabajador: ITrabajador) {
    const modalRef = this.modalService.open(ModalComisionTrabajadorComponent);
    modalRef.componentInstance.trabajador = trabajador;

    for (var as of this.totalVentas) {
      if (trabajador.id == as.idTrabajador) {
        modalRef.componentInstance.totalNumeroVentas = as;
      }

      //   console.log(as.total)
    }
  }

  open_dialog(trabajador: ITrabajador) {
    console.log(trabajador);

    const modalRef = this.modalService.open(VehiculoEditDialogComponent);
    modalRef.componentInstance.trabajador = trabajador;
  }

  loadAll() {
    if (this.currentSearch) {
      this.trabajadorService
        .search({
          page: this.page - 1,
          query: this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<ITrabajador[]>) => this.paginateTrabajadors(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.trabajadorService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ITrabajador[]>) => this.paginateTrabajadors(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/trabajador'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        search: this.currentSearch,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.currentSearch = '';
    this.router.navigate([
      '/trabajador',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.page = 0;
    this.currentSearch = query;
    this.router.navigate([
      '/trabajador',
      {
        search: this.currentSearch,
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.compraVentaService.buscarTotalVentas().subscribe(res => {
      this.totalVentas = res.body;
    });
    // this.totalVentas.forEach(element =>{
    //   this.totalVentas2.push(element.idTrabajador)
    //   console.log(this.totalVentas2)
    // })
    // console.log(this.totalVentas)
    // // //console.log(this.totalVentas[0].idTrabajador)

    // for (var as of this.totalVentas) {

    //   console.log(as.total)
    // }

    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTrabajadors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITrabajador) {
    return item.id;
  }

  registerChangeInTrabajadors() {
    this.eventSubscriber = this.eventManager.subscribe('trabajadorListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTrabajadors(data: ITrabajador[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.trabajadors = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
