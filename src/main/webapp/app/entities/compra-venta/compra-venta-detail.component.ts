import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompraVenta } from 'app/shared/model/compra-venta.model';

@Component({
  selector: 'jhi-compra-venta-detail',
  templateUrl: './compra-venta-detail.component.html'
})
export class CompraVentaDetailComponent implements OnInit {
  compraVenta: ICompraVenta;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compraVenta }) => {
      this.compraVenta = compraVenta;
    });
  }

  previousState() {
    window.history.back();
  }
}
