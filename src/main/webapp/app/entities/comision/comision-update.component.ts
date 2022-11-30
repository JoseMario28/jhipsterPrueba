import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IComision, Comision } from 'app/shared/model/comision.model';
import { ComisionService } from './comision.service';

@Component({
  selector: 'jhi-comision-update',
  templateUrl: './comision-update.component.html'
})
export class ComisionUpdateComponent implements OnInit {
  comision: IComision;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    rango: [],
    porcentaje: []
  });

  constructor(protected comisionService: ComisionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ comision }) => {
      this.updateForm(comision);
      this.comision = comision;
    });
  }

  updateForm(comision: IComision) {
    this.editForm.patchValue({
      id: comision.id,
      nombre: comision.nombre,
      rango: comision.rango,
      porcentaje: comision.porcentaje
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const comision = this.createFromForm();
    if (comision.id !== undefined) {
      this.subscribeToSaveResponse(this.comisionService.update(comision));
    } else {
      this.subscribeToSaveResponse(this.comisionService.create(comision));
    }
  }

  private createFromForm(): IComision {
    const entity = {
      ...new Comision(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      rango: this.editForm.get(['rango']).value,
      porcentaje: this.editForm.get(['porcentaje']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComision>>) {
    result.subscribe((res: HttpResponse<IComision>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
