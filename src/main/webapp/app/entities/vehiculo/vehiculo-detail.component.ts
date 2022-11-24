import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehiculo } from 'app/shared/model/vehiculo.model';

@Component({
  selector: 'jhi-vehiculo-detail',
  templateUrl: './vehiculo-detail.component.html'
})
export class VehiculoDetailComponent implements OnInit {
  vehiculo: IVehiculo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vehiculo }) => {
      this.vehiculo = vehiculo;
    });
  }

  previousState() {
    window.history.back();
  }
}
