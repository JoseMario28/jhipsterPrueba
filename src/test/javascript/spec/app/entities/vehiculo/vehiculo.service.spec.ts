/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { VehiculoService } from 'app/entities/vehiculo/vehiculo.service';
import { IVehiculo, Vehiculo } from 'app/shared/model/vehiculo.model';

describe('Service Tests', () => {
  describe('Vehiculo Service', () => {
    let injector: TestBed;
    let service: VehiculoService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehiculo;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(VehiculoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Vehiculo(0, 'AAAAAAA', 'AAAAAAA', 0, currentDate, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            anno: currentDate.format(DATE_FORMAT)
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

      it('should create a Vehiculo', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            anno: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            anno: currentDate
          },
          returnedFromService
        );
        service
          .create(new Vehiculo(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Vehiculo', async () => {
        const returnedFromService = Object.assign(
          {
            modelo: 'BBBBBB',
            marca: 'BBBBBB',
            km: 1,
            anno: currentDate.format(DATE_FORMAT),
            precio: 1,
            patente: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            anno: currentDate
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

      it('should return a list of Vehiculo', async () => {
        const returnedFromService = Object.assign(
          {
            modelo: 'BBBBBB',
            marca: 'BBBBBB',
            km: 1,
            anno: currentDate.format(DATE_FORMAT),
            precio: 1,
            patente: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            anno: currentDate
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

      it('should delete a Vehiculo', async () => {
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
