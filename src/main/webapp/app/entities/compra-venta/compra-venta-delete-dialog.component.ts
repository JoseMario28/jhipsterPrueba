import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompraVenta } from 'app/shared/model/compra-venta.model';
import { CompraVentaService } from './compra-venta.service';

@Component({
  selector: 'jhi-compra-venta-delete-dialog',
  templateUrl: './compra-venta-delete-dialog.component.html'
})
export class CompraVentaDeleteDialogComponent {
  compraVenta: ICompraVenta;

  constructor(
    protected compraVentaService: CompraVentaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.compraVentaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'compraVentaListModification',
        content: 'Deleted an compraVenta'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-compra-venta-delete-popup',
  template: ''
})
export class CompraVentaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compraVenta }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompraVentaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.compraVenta = compraVenta;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/compra-venta', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/compra-venta', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
