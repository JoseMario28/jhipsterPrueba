import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PruebaSharedModule } from 'app/shared';
import {
  DocumentoComponent,
  DocumentoDetailComponent,
  DocumentoUpdateComponent,
  DocumentoDeletePopupComponent,
  DocumentoDeleteDialogComponent,
  documentoRoute,
  documentoPopupRoute
} from './';

const ENTITY_STATES = [...documentoRoute, ...documentoPopupRoute];

@NgModule({
  imports: [PruebaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DocumentoComponent,
    DocumentoDetailComponent,
    DocumentoUpdateComponent,
    DocumentoDeleteDialogComponent,
    DocumentoDeletePopupComponent
  ],
  entryComponents: [DocumentoComponent, DocumentoUpdateComponent, DocumentoDeleteDialogComponent, DocumentoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PruebaDocumentoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
