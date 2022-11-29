import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PruebaSharedModule } from 'app/shared';
import {
  TrabajadorComponent,
  TrabajadorDetailComponent,
  TrabajadorUpdateComponent,
  TrabajadorDeletePopupComponent,
  TrabajadorDeleteDialogComponent,
  trabajadorRoute,
  trabajadorPopupRoute
} from './';
import { ModalComisionTrabajadorComponent } from './modal-comision-trabajador/modal-comision-trabajador.component';

const ENTITY_STATES = [...trabajadorRoute, ...trabajadorPopupRoute];

@NgModule({
  imports: [PruebaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TrabajadorComponent,
    TrabajadorDetailComponent,
    TrabajadorUpdateComponent,
    TrabajadorDeleteDialogComponent,
    TrabajadorDeletePopupComponent,
    ModalComisionTrabajadorComponent
  ],
  entryComponents: [
    TrabajadorComponent,
    ModalComisionTrabajadorComponent,
    TrabajadorUpdateComponent,
    TrabajadorDeleteDialogComponent,
    TrabajadorDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PruebaTrabajadorModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
