import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ComisionService } from 'app/entities/comision';
import { Comision } from 'app/shared/model/comision.model';
import { ITotalVentas, ITrabajador } from 'app/shared/model/trabajador.model';

@Component({
  selector: 'jhi-modal-comision-trabajador',
  templateUrl: './modal-comision-trabajador.component.html',
  styles: []
})
export class ModalComisionTrabajadorComponent implements OnInit {
  @Input() trabajador: ITrabajador;
  @Input() totalNumeroVentas: ITotalVentas;

  tiposComisiones: Comision[];

  selectVisible: boolean = false;
  visible: boolean = false;

  disabled: boolean = true;
  numeroComision: number;

  totalConComision: number = 0;

  constructor(public activeModal: NgbActiveModal, protected comisionService: ComisionService) {}

  ngOnInit() {
    // console.log(Number.isNaN(this.totalNumeroVentas.total.valueOf()))
    // console.log(typeof(this.totalNumeroVentas.total))
    // // console.log(this.totalNumeroVentas.total)
    // console.log(this.trabajador)
  }

  calcularComision(numeroComision: number) {
    this.totalConComision = (numeroComision * this.totalNumeroVentas.total) / 100;
  }

  comisionLibre() {
    this.selectVisible = false;
    this.visible = !this.visible;
    this.totalConComision = 0;
  }

  comisionAsignada() {
    this.visible = false;
    this.selectVisible = !this.selectVisible;
    this.comisionService.query().subscribe(res => {
      (this.tiposComisiones = res.body), console.log(this.tiposComisiones);
    });
    this.totalConComision = 0;
  }

  onChange(porcentaje) {
    this.totalConComision = (this.totalNumeroVentas.total * porcentaje) / 100;
    console.log(this.totalConComision);
  }
}
