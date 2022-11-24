import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IVehiculo, Vehiculo } from 'app/shared/model/vehiculo.model';
import { Observable } from 'rxjs';
import { VehiculoService } from '../vehiculo.service';

@Component({
  selector: 'jhi-vehiculo-edit-dialog',
  templateUrl: './vehiculo-edit-dialog.component.html',
  styles: []
})
export class VehiculoEditDialogComponent {
  isSaving: boolean;
  /**
   *
   */
  constructor(private fb: FormBuilder, private vehiculoService: VehiculoService) {}

  @Input() vehiculo: IVehiculo;

  editForm = this.fb.group({
    id: [],
    modelo: [],
    marca: [],
    km: [],
    anno: [],
    precio: [],
    patente: []
  });

  private createFromForm(): IVehiculo {
    const entity = {
      ...new Vehiculo(),
      id: this.editForm.get(['id']).value,
      modelo: this.editForm.get(['modelo']).value,
      marca: this.editForm.get(['marca']).value,
      km: this.editForm.get(['km']).value,
      anno: this.editForm.get(['anno']).value,
      precio: this.editForm.get(['precio']).value,
      patente: this.editForm.get(['patente']).value
    };
    return entity;
  }

  updateForm(vehiculo: IVehiculo) {
    this.editForm.patchValue({
      id: vehiculo.id,
      modelo: vehiculo.modelo,
      marca: vehiculo.marca,
      km: vehiculo.km,
      anno: vehiculo.anno,
      precio: vehiculo.precio,
      patente: vehiculo.patente
    });
  }

  ngOnInit() {
    if (this.vehiculo !== undefined) {
      this.updateForm(this.vehiculo);
    } else {
      this.isSaving = false;
      console.log('entro');
      this.vehiculo = new Vehiculo();
      this.updateForm(this.vehiculo);
      console.log(this.vehiculo.id);
    }
  }

  save() {
    const vehiculo = this.createFromForm();
    if (vehiculo.id !== undefined) {
      this.subscribeToSaveResponse(this.vehiculoService.update(vehiculo));
      console.log('con id');
    } else {
      console.log('sin id');
      this.subscribeToSaveResponse(this.vehiculoService.create(vehiculo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehiculo>>) {
    result.subscribe((res: HttpResponse<IVehiculo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  /*
  //vehiculo: IVehiculo;
  isSaving: boolean;
  annoDp: any;



  constructor(public activeModal: NgbActiveModal,protected vehiculoService: VehiculoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) { }



  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vehiculo }) => {
      this.updateForm(vehiculo);
      this.vehiculo = vehiculo;
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
      patente: this.editForm.get(['patente']).value
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
*/
}
