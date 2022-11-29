import { ICompraVenta } from 'app/shared/model/compra-venta.model';

export interface ITrabajador {
  id?: number;
  dni?: number;
  nombre?: string;
  apellido?: string;
  cargo?: string;
  vendedors?: ICompraVenta[];
}

export interface ITotalVentas {
  idTrabajador: number;
  total: number;
}

export class Trabajador implements ITrabajador {
  constructor(
    public id?: number,
    public dni?: number,
    public nombre?: string,
    public apellido?: string,
    public cargo?: string,
    public vendedors?: ICompraVenta[]
  ) {}
}
