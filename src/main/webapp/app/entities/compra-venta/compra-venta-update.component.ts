import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICompraVenta, CompraVenta } from 'app/shared/model/compra-venta.model';
import { CompraVentaService } from './compra-venta.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IVehiculo } from 'app/shared/model/vehiculo.model';
import { VehiculoService } from 'app/entities/vehiculo';
import { ITrabajador } from 'app/shared/model/trabajador.model';
import { TrabajadorService } from 'app/entities/trabajador';
import { resolveSoa } from 'dns';

@Component({
  selector: 'jhi-compra-venta-update',
  templateUrl: './compra-venta-update.component.html'
})
export class CompraVentaUpdateComponent implements OnInit {
  compraVenta: ICompraVenta;
  isSaving: boolean;

  clientes: ICliente[];

  vehiculos: IVehiculo[] = [];

  trabajadors: ITrabajador[];
  fechaVentaDp: any;

  editForm = this.fb.group({
    id: [],
    fechaVenta: [],
    garantia: [],
    precioTotal: [],
    cliente: [],
    vehiculo: [],
    trabajador: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compraVentaService: CompraVentaService,
    protected clienteService: ClienteService,
    protected vehiculoService: VehiculoService,
    protected trabajadorService: TrabajadorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compraVenta }) => {
      this.updateForm(compraVenta);
      this.compraVenta = compraVenta;
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.vehiculoService
      .query({ filter: 'compraventa-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IVehiculo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVehiculo[]>) => response.body)
      )
      .subscribe(
        (res: IVehiculo[]) => {
          if (!this.compraVenta.vehiculo || !this.compraVenta.vehiculo.id) {
            res.forEach(element => {
              if (element.usado == true) {
                if (Array.isArray(this.vehiculos)) {
                  this.vehiculos.push(element);
                }
              }
            });
            //this.vehiculos = res;
          } else {
            this.vehiculoService
              .find(this.compraVenta.vehiculo.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IVehiculo>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IVehiculo>) => subResponse.body)
              )
              .subscribe(
                (subRes: IVehiculo) => (this.vehiculos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.trabajadorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITrabajador[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITrabajador[]>) => response.body)
      )
      .subscribe((res: ITrabajador[]) => (this.trabajadors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(compraVenta: ICompraVenta) {
    this.editForm.patchValue({
      id: compraVenta.id,
      fechaVenta: compraVenta.fechaVenta,
      garantia: compraVenta.garantia,
      precioTotal: compraVenta.precioTotal,
      cliente: compraVenta.cliente,
      vehiculo: compraVenta.vehiculo,
      trabajador: compraVenta.trabajador
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const compraVenta = this.createFromForm();
    if (compraVenta.id !== undefined) {
      this.subscribeToSaveResponse(this.compraVentaService.update(compraVenta));
    } else {
      this.subscribeToSaveResponse(this.compraVentaService.create(compraVenta));
      compraVenta.vehiculo.usado = false;
      this.subscribeToSaveResponse(this.vehiculoService.update(compraVenta.vehiculo));
    }
  }

  private createFromForm(): ICompraVenta {
    const entity = {
      ...new CompraVenta(),
      id: this.editForm.get(['id']).value,
      fechaVenta: this.editForm.get(['fechaVenta']).value,
      garantia: this.editForm.get(['garantia']).value,
      precioTotal: this.editForm.get(['precioTotal']).value,
      cliente: this.editForm.get(['cliente']).value,
      vehiculo: this.editForm.get(['vehiculo']).value,
      trabajador: this.editForm.get(['trabajador']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompraVenta>>) {
    result.subscribe((res: HttpResponse<ICompraVenta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }

  trackVehiculoById(index: number, item: IVehiculo) {
    return item.id;
  }

  trackTrabajadorById(index: number, item: ITrabajador) {
    return item.id;
  }
}
