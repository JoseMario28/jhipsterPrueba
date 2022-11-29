import { Moment } from 'moment';

export interface IVehiculo {
  id?: number;
  modelo?: string;
  marca?: string;
  km?: number;
  anno?: Moment;
  precio?: number;
  patente?: string;
  usado?: boolean;
}

export class Vehiculo implements IVehiculo {
  constructor(
    public id?: number,
    public modelo?: string,
    public marca?: string,
    public km?: number,
    public anno?: Moment,
    public precio?: number,
    public patente?: string,
    public usado?: boolean
  ) {}
}
