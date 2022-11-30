/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruebaTestModule } from '../../../test.module';
import { ComisionDetailComponent } from 'app/entities/comision/comision-detail.component';
import { Comision } from 'app/shared/model/comision.model';

describe('Component Tests', () => {
  describe('Comision Management Detail Component', () => {
    let comp: ComisionDetailComponent;
    let fixture: ComponentFixture<ComisionDetailComponent>;
    const route = ({ data: of({ comision: new Comision(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaTestModule],
        declarations: [ComisionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ComisionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComisionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.comision).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
