/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PruebaTestModule } from '../../../test.module';
import { ComisionComponent } from 'app/entities/comision/comision.component';
import { ComisionService } from 'app/entities/comision/comision.service';
import { Comision } from 'app/shared/model/comision.model';

describe('Component Tests', () => {
  describe('Comision Management Component', () => {
    let comp: ComisionComponent;
    let fixture: ComponentFixture<ComisionComponent>;
    let service: ComisionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaTestModule],
        declarations: [ComisionComponent],
        providers: []
      })
        .overrideTemplate(ComisionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComisionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComisionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Comision(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.comisions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
