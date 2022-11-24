import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PruebaSharedModule } from 'app/shared';
import {
  CompraVentaComponent,
  CompraVentaDetailComponent,
  CompraVentaUpdateComponent,
  CompraVentaDeletePopupComponent,
  CompraVentaDeleteDialogComponent,
  compraVentaRoute,
  compraVentaPopupRoute
} from './';

const ENTITY_STATES = [...compraVentaRoute, ...compraVentaPopupRoute];

@NgModule({
  imports: [PruebaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompraVentaComponent,
    CompraVentaDetailComponent,
    CompraVentaUpdateComponent,
    CompraVentaDeleteDialogComponent,
    CompraVentaDeletePopupComponent
  ],
  entryComponents: [CompraVentaComponent, CompraVentaUpdateComponent, CompraVentaDeleteDialogComponent, CompraVentaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PruebaCompraVentaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
