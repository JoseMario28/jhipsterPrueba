import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IVehiculo, Vehiculo } from 'app/shared/model/vehiculo.model';
import { VehiculoService } from './vehiculo.service';

@Component({
  selector: 'jhi-vehiculo-update',
  templateUrl: './vehiculo-update.component.html'
})
export class VehiculoUpdateComponent implements OnInit {
  vehiculo: IVehiculo;
  isSaving: boolean;
  annoDp: any;

  editForm = this.fb.group({
    id: [],
    modelo: [],
    marca: [],
    km: [],
    anno: [],
    precio: [],
    patente: [],
    usado: []
  });

  constructor(protected vehiculoService: VehiculoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vehiculo }) => {
      this.updateForm(vehiculo);
      this.vehiculo = vehiculo;
    });
  }

  updateForm(vehiculo: IVehiculo) {
    this.editForm.patchValue({
      id: vehiculo.id,
      modelo: vehiculo.modelo,
      marca: vehiculo.marca,
      km: vehiculo.km,
      anno: vehiculo.anno,
      precio: vehiculo.precio,
      patente: vehiculo.patente,
      usado: vehiculo.usado
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vehiculo = this.createFromForm();
    if (vehiculo.id !== undefined) {
      this.subscribeToSaveResponse(this.vehiculoService.update(vehiculo));
    } else {
      this.subscribeToSaveResponse(this.vehiculoService.create(vehiculo));
    }
  }

  private createFromForm(): IVehiculo {
    const entity = {
      ...new Vehiculo(),
      id: this.editForm.get(['id']).value,
      modelo: this.editForm.get(['modelo']).value,
      marca: this.editForm.get(['marca']).value,
      km: this.editForm.get(['km']).value,
      anno: this.editForm.get(['anno']).value,
      precio: this.editForm.get(['precio']).value,
      patente: this.editForm.get(['patente']).value,
      usado: this.editForm.get(['usado']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehiculo>>) {
    result.subscribe((res: HttpResponse<IVehiculo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
