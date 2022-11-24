/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PruebaTestModule } from '../../../test.module';
import { CompraVentaDeleteDialogComponent } from 'app/entities/compra-venta/compra-venta-delete-dialog.component';
import { CompraVentaService } from 'app/entities/compra-venta/compra-venta.service';

describe('Component Tests', () => {
  describe('CompraVenta Management Delete Component', () => {
    let comp: CompraVentaDeleteDialogComponent;
    let fixture: ComponentFixture<CompraVentaDeleteDialogComponent>;
    let service: CompraVentaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaTestModule],
        declarations: [CompraVentaDeleteDialogComponent]
      })
        .overrideTemplate(CompraVentaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompraVentaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompraVentaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
