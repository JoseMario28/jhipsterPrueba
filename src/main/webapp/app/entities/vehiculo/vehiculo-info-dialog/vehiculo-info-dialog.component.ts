import { Component, Input, OnInit } from '@angular/core';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IVehiculo } from 'app/shared/model/vehiculo.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'jhi-vehiculo-info-dialog',
  templateUrl: './vehiculo-info-dialog.component.html'
})
export class VehiculoInfoDialogComponent {
  @Input() Vehiculo: IVehiculo;

  constructor(public activeModal: NgbActiveModal) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete() {
    Swal.fire({
      position: 'center',
      type: 'success',
      title: 'Informacion mostrada con exito',
      showConfirmButton: false,
      timer: 1500
    });

    this.activeModal.dismiss('cancel');
  }
}
