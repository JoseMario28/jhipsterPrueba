/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruebaTestModule } from '../../../test.module';
import { CompraVentaDetailComponent } from 'app/entities/compra-venta/compra-venta-detail.component';
import { CompraVenta } from 'app/shared/model/compra-venta.model';

describe('Component Tests', () => {
  describe('CompraVenta Management Detail Component', () => {
    let comp: CompraVentaDetailComponent;
    let fixture: ComponentFixture<CompraVentaDetailComponent>;
    const route = ({ data: of({ compraVenta: new CompraVenta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaTestModule],
        declarations: [CompraVentaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompraVentaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompraVentaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.compraVenta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
