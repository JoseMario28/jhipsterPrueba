/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PruebaTestModule } from '../../../test.module';
import { ComisionDeleteDialogComponent } from 'app/entities/comision/comision-delete-dialog.component';
import { ComisionService } from 'app/entities/comision/comision.service';

describe('Component Tests', () => {
  describe('Comision Management Delete Component', () => {
    let comp: ComisionDeleteDialogComponent;
    let fixture: ComponentFixture<ComisionDeleteDialogComponent>;
    let service: ComisionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaTestModule],
        declarations: [ComisionDeleteDialogComponent]
      })
        .overrideTemplate(ComisionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComisionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComisionService);
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
