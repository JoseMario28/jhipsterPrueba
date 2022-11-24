import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { icon } from '@fortawesome/fontawesome-svg-core';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ICliente } from 'app/shared/model/cliente.model';
import { JhiEventManager } from 'ng-jhipster';
import Swal from 'sweetalert2';

import { ClienteService } from '../cliente.service';

@Component({
  selector: 'jhi-cliente-info-dialog',
  templateUrl: './cliente-info-dialog.component.html',
  styles: []
})
export class ClienteInfoDialogComponent {
  cliente: ICliente;

  constructor(protected clienteService: ClienteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete() {
    Swal.fire({
      position: 'center',
      type: 'success',
      title: 'Informacion mostrada con exito',
      showConfirmButton: false,
      timer: 1500
    });

    this.activeModal.dismiss('cancel');
  }
}

@Component({
  selector: 'jhi-cliente-info-popup',
  template: ''
})
export class ClienteInfoPopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClienteInfoDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cliente = cliente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cliente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cliente', { outlets: { popup: null } }]);
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
