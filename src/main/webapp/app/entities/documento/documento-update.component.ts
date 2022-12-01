import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDocumento, Documento } from 'app/shared/model/documento.model';
import { DocumentoService } from './documento.service';

@Component({
  selector: 'jhi-documento-update',
  templateUrl: './documento-update.component.html'
})
export class DocumentoUpdateComponent implements OnInit {
  documento: IDocumento;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    path: [],
    nombre: [],
    contentType: []
  });

  constructor(protected documentoService: DocumentoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ documento }) => {
      this.updateForm(documento);
      this.documento = documento;
    });
  }

  updateForm(documento: IDocumento) {
    this.editForm.patchValue({
      id: documento.id,
      path: documento.path,
      nombre: documento.nombre,
      contentType: documento.contentType
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const documento = this.createFromForm();
    if (documento.id !== undefined) {
      this.subscribeToSaveResponse(this.documentoService.update(documento));
    } else {
      this.subscribeToSaveResponse(this.documentoService.create(documento));
    }
  }

  private createFromForm(): IDocumento {
    const entity = {
      ...new Documento(),
      id: this.editForm.get(['id']).value,
      path: this.editForm.get(['path']).value,
      nombre: this.editForm.get(['nombre']).value,
      contentType: this.editForm.get(['contentType']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumento>>) {
    result.subscribe((res: HttpResponse<IDocumento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
