export interface IComision {
  id?: number;
  nombre?: string;
  rango?: number;
  porcentaje?: number;
}

export class Comision implements IComision {
  constructor(public id?: number, public nombre?: string, public rango?: number, public porcentaje?: number) {}
}
