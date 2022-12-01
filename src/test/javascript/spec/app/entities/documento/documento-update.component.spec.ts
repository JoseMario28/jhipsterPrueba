/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PruebaTestModule } from '../../../test.module';
import { DocumentoUpdateComponent } from 'app/entities/documento/documento-update.component';
import { DocumentoService } from 'app/entities/documento/documento.service';
import { Documento } from 'app/shared/model/documento.model';

describe('Component Tests', () => {
  describe('Documento Management Update Component', () => {
    let comp: DocumentoUpdateComponent;
    let fixture: ComponentFixture<DocumentoUpdateComponent>;
    let service: DocumentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaTestModule],
        declarations: [DocumentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DocumentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Documento(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Documento();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
