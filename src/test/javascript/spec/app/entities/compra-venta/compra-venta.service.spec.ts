/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CompraVentaService } from 'app/entities/compra-venta/compra-venta.service';
import { ICompraVenta, CompraVenta } from 'app/shared/model/compra-venta.model';

describe('Service Tests', () => {
  describe('CompraVenta Service', () => {
    let injector: TestBed;
    let service: CompraVentaService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompraVenta;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CompraVentaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CompraVenta(0, currentDate, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fechaVenta: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a CompraVenta', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaVenta: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaVenta: currentDate
          },
          returnedFromService
        );
        service
          .create(new CompraVenta(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CompraVenta', async () => {
        const returnedFromService = Object.assign(
          {
            fechaVenta: currentDate.format(DATE_FORMAT),
            garantia: 1,
            precioTotal: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaVenta: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of CompraVenta', async () => {
        const returnedFromService = Object.assign(
          {
            fechaVenta: currentDate.format(DATE_FORMAT),
            garantia: 1,
            precioTotal: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaVenta: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CompraVenta', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
