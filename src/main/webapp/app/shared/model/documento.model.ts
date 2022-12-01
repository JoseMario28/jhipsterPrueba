export interface IDocumento {
  id?: number;
  path?: string;
  nombre?: string;
  contentType?: string;
}

export class Documento implements IDocumento {
  constructor(public id?: number, public path?: string, public nombre?: string, public contentType?: string) {}
}
