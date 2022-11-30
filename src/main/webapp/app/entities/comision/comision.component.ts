import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IComision } from 'app/shared/model/comision.model';
import { AccountService } from 'app/core';
import { ComisionService } from './comision.service';

@Component({
  selector: 'jhi-comision',
  templateUrl: './comision.component.html'
})
export class ComisionComponent implements OnInit, OnDestroy {
  comisions: IComision[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected comisionService: ComisionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.comisionService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IComision[]>) => res.ok),
          map((res: HttpResponse<IComision[]>) => res.body)
        )
        .subscribe((res: IComision[]) => (this.comisions = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.comisionService
      .query()
      .pipe(
        filter((res: HttpResponse<IComision[]>) => res.ok),
        map((res: HttpResponse<IComision[]>) => res.body)
      )
      .subscribe(
        (res: IComision[]) => {
          this.comisions = res;
          this.currentSearch = '';
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInComisions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IComision) {
    return item.id;
  }

  registerChangeInComisions() {
    this.eventSubscriber = this.eventManager.subscribe('comisionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
