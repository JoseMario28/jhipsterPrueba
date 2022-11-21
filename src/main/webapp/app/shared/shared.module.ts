import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PruebaSharedLibsModule, PruebaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [PruebaSharedLibsModule, PruebaSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [PruebaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PruebaSharedModule {
  static forRoot() {
    return {
      ngModule: PruebaSharedModule
    };
  }
}
