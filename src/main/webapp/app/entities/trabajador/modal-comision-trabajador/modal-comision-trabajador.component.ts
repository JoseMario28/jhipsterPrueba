import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ITotalVentas, ITrabajador } from 'app/shared/model/trabajador.model';

@Component({
  selector: 'jhi-modal-comision-trabajador',
  templateUrl: './modal-comision-trabajador.component.html',
  styles: []
})
export class ModalComisionTrabajadorComponent implements OnInit {
  @Input() trabajador: ITrabajador;
  @Input() totalNumeroVentas: ITotalVentas;

  disabled: boolean = true;
  numeroComision: number;

  totalConComision: number = 0;

  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit() {
    // console.log(Number.isNaN(this.totalNumeroVentas.total.valueOf()))
    // console.log(typeof(this.totalNumeroVentas.total))
    // // console.log(this.totalNumeroVentas.total)
    // console.log(this.trabajador)
  }

  calcularComision(numeroComision: number) {
    this.totalConComision = (numeroComision * this.totalNumeroVentas.total) / 100;
  }
}
