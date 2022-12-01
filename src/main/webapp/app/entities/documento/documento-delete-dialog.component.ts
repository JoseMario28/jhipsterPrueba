import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumento } from 'app/shared/model/documento.model';
import { DocumentoService } from './documento.service';

@Component({
  selector: 'jhi-documento-delete-dialog',
  templateUrl: './documento-delete-dialog.component.html'
})
export class DocumentoDeleteDialogComponent {
  documento: IDocumento;

  constructor(protected documentoService: DocumentoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.documentoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'documentoListModification',
        content: 'Deleted an documento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-documento-delete-popup',
  template: ''
})
export class DocumentoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ documento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DocumentoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.documento = documento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/documento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/documento', { outlets: { popup: null } }]);
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
