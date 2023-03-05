import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { VendaFormService, VendaFormGroup } from './venda-form.service';
import { IVenda } from '../venda.model';
import { VendaService } from '../service/venda.service';
import { IProduto } from 'app/entities/produto/produto.model';
import { ProdutoService } from 'app/entities/produto/service/produto.service';

@Component({
  selector: 'jhi-venda-update',
  templateUrl: './venda-update.component.html',
})
export class VendaUpdateComponent implements OnInit {
  isSaving = false;
  venda: IVenda | null = null;

  produtosSharedCollection: IProduto[] = [];

  editForm: VendaFormGroup = this.vendaFormService.createVendaFormGroup();

  constructor(
    protected vendaService: VendaService,
    protected vendaFormService: VendaFormService,
    protected produtoService: ProdutoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProduto = (o1: IProduto | null, o2: IProduto | null): boolean => this.produtoService.compareProduto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venda }) => {
      this.venda = venda;
      if (venda) {
        this.updateForm(venda);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const venda = this.vendaFormService.getVenda(this.editForm);
    if (venda.id !== null) {
      this.subscribeToSaveResponse(this.vendaService.update(venda));
    } else {
      this.subscribeToSaveResponse(this.vendaService.create(venda));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenda>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(venda: IVenda): void {
    this.venda = venda;
    this.vendaFormService.resetForm(this.editForm, venda);

    this.produtosSharedCollection = this.produtoService.addProdutoToCollectionIfMissing<IProduto>(
      this.produtosSharedCollection,
      ...(venda.produtos ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.produtoService
      .query()
      .pipe(map((res: HttpResponse<IProduto[]>) => res.body ?? []))
      .pipe(
        map((produtos: IProduto[]) =>
          this.produtoService.addProdutoToCollectionIfMissing<IProduto>(produtos, ...(this.venda?.produtos ?? []))
        )
      )
      .subscribe((produtos: IProduto[]) => (this.produtosSharedCollection = produtos));
  }
}
