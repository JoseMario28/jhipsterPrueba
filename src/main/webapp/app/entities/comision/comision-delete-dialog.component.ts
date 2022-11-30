import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComision } from 'app/shared/model/comision.model';
import { ComisionService } from './comision.service';

@Component({
  selector: 'jhi-comision-delete-dialog',
  templateUrl: './comision-delete-dialog.component.html'
})
export class ComisionDeleteDialogComponent {
  comision: IComision;

  constructor(protected comisionService: ComisionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.comisionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'comisionListModification',
        content: 'Deleted an comision'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-comision-delete-popup',
  template: ''
})
export class ComisionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comision }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ComisionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.comision = comision;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/comision', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/comision', { outlets: { popup: null } }]);
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
