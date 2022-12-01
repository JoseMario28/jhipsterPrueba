import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'vehiculo',
        loadChildren: './vehiculo/vehiculo.module#PruebaVehiculoModule'
      },
      {
        path: 'cliente',
        loadChildren: './cliente/cliente.module#PruebaClienteModule'
      },
      {
        path: 'compra-venta',
        loadChildren: './compra-venta/compra-venta.module#PruebaCompraVentaModule'
      },
      {
        path: 'trabajador',
        loadChildren: './trabajador/trabajador.module#PruebaTrabajadorModule'
      },
      {
        path: 'vehiculo',
        loadChildren: './vehiculo/vehiculo.module#PruebaVehiculoModule'
      },
      {
        path: 'cliente',
        loadChildren: './cliente/cliente.module#PruebaClienteModule'
      },
      {
        path: 'compra-venta',
        loadChildren: './compra-venta/compra-venta.module#PruebaCompraVentaModule'
      },
      {
        path: 'trabajador',
        loadChildren: './trabajador/trabajador.module#PruebaTrabajadorModule'
      },
      {
        path: 'compra-venta',
        loadChildren: './compra-venta/compra-venta.module#PruebaCompraVentaModule'
      },
      {
        path: 'vehiculo',
        loadChildren: './vehiculo/vehiculo.module#PruebaVehiculoModule'
      },
      {
        path: 'compra-venta',
        loadChildren: './compra-venta/compra-venta.module#PruebaCompraVentaModule'
      },
      {
        path: 'compra-venta',
        loadChildren: './compra-venta/compra-venta.module#PruebaCompraVentaModule'
      },
      {
        path: 'compra-venta',
        loadChildren: './compra-venta/compra-venta.module#PruebaCompraVentaModule'
      },
      {
        path: 'comision',
        loadChildren: './comision/comision.module#PruebaComisionModule'
      },
      {
        path: 'documento',
        loadChildren: './documento/documento.module#PruebaDocumentoModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PruebaEntityModule {}
