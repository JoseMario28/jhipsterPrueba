import { Moment } from 'moment';
import { ICliente } from 'app/shared/model/cliente.model';
import { IVehiculo } from 'app/shared/model/vehiculo.model';
import { ITrabajador } from 'app/shared/model/trabajador.model';

export interface ICompraVenta {
  id?: number;
  fechaVenta?: Moment;
  garantia?: number;
  precioTotal?: number;
  cliente?: ICliente;
  vehiculo?: IVehiculo;
  trabajador?: ITrabajador;
}

export class CompraVenta implements ICompraVenta {
  constructor(
    public id?: number,
    public fechaVenta?: Moment,
    public garantia?: number,
    public precioTotal?: number,
    public cliente?: ICliente,
    public vehiculo?: IVehiculo,
    public trabajador?: ITrabajador
  ) {}
}
