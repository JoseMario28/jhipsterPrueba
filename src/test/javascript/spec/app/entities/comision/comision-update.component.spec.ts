/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PruebaTestModule } from '../../../test.module';
import { ComisionUpdateComponent } from 'app/entities/comision/comision-update.component';
import { ComisionService } from 'app/entities/comision/comision.service';
import { Comision } from 'app/shared/model/comision.model';

describe('Component Tests', () => {
  describe('Comision Management Update Component', () => {
    let comp: ComisionUpdateComponent;
    let fixture: ComponentFixture<ComisionUpdateComponent>;
    let service: ComisionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaTestModule],
        declarations: [ComisionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ComisionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComisionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComisionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Comision(123);
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
        const entity = new Comision();
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
