import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PruebaSharedModule } from 'app/shared';
import {
  VehiculoComponent,
  VehiculoDetailComponent,
  VehiculoUpdateComponent,
  VehiculoDeletePopupComponent,
  VehiculoDeleteDialogComponent,
  vehiculoRoute,
  vehiculoPopupRoute
} from './';
import { VehiculoInfoDialogComponent } from './vehiculo-info-dialog/vehiculo-info-dialog.component';
import { VehiculoEditDialogComponent } from './vehiculo-edit-dialog/vehiculo-edit-dialog.component';

const ENTITY_STATES = [...vehiculoRoute, ...vehiculoPopupRoute];

@NgModule({
  imports: [PruebaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VehiculoComponent,
    VehiculoDetailComponent,
    VehiculoUpdateComponent,
    VehiculoDeleteDialogComponent,
    VehiculoDeletePopupComponent,
    VehiculoInfoDialogComponent,
    VehiculoEditDialogComponent
  ],
  entryComponents: [
    VehiculoComponent,
    VehiculoUpdateComponent,
    VehiculoDeleteDialogComponent,
    VehiculoDeletePopupComponent,
    VehiculoInfoDialogComponent,
    VehiculoEditDialogComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PruebaVehiculoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
