import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumento } from 'app/shared/model/documento.model';

@Component({
  selector: 'jhi-documento-detail',
  templateUrl: './documento-detail.component.html'
})
export class DocumentoDetailComponent implements OnInit {
  documento: IDocumento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ documento }) => {
      this.documento = documento;
    });
  }

  previousState() {
    window.history.back();
  }
}
