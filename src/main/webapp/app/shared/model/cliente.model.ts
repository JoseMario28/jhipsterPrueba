import { Moment } from 'moment';
import { ICompraVenta } from 'app/shared/model/compra-venta.model';

export interface ICliente {
  id?: number;
  dni?: string;
  nombre?: string;
  apellido?: string;
  fechaNacimiento?: Moment;
  clientes?: ICompraVenta[];
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public dni?: string,
    public nombre?: string,
    public apellido?: string,
    public fechaNacimiento?: Moment,
    public clientes?: ICompraVenta[]
  ) {}
}
