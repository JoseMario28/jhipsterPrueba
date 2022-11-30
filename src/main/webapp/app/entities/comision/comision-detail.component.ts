import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComision } from 'app/shared/model/comision.model';

@Component({
  selector: 'jhi-comision-detail',
  templateUrl: './comision-detail.component.html'
})
export class ComisionDetailComponent implements OnInit {
  comision: IComision;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comision }) => {
      this.comision = comision;
    });
  }

  previousState() {
    window.history.back();
  }
}
