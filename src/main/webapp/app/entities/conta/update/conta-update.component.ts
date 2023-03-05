import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ContaFormService, ContaFormGroup } from './conta-form.service';
import { IConta } from '../conta.model';
import { ContaService } from '../service/conta.service';
import { IVenda } from 'app/entities/venda/venda.model';
import { VendaService } from 'app/entities/venda/service/venda.service';

@Component({
  selector: 'jhi-conta-update',
  templateUrl: './conta-update.component.html',
})
export class ContaUpdateComponent implements OnInit {
  isSaving = false;
  conta: IConta | null = null;

  vendasSharedCollection: IVenda[] = [];

  editForm: ContaFormGroup = this.contaFormService.createContaFormGroup();

  constructor(
    protected contaService: ContaService,
    protected contaFormService: ContaFormService,
    protected vendaService: VendaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareVenda = (o1: IVenda | null, o2: IVenda | null): boolean => this.vendaService.compareVenda(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conta }) => {
      this.conta = conta;
      if (conta) {
        this.updateForm(conta);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conta = this.contaFormService.getConta(this.editForm);
    if (conta.id !== null) {
      this.subscribeToSaveResponse(this.contaService.update(conta));
    } else {
      this.subscribeToSaveResponse(this.contaService.create(conta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConta>>): void {
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

  protected updateForm(conta: IConta): void {
    this.conta = conta;
    this.contaFormService.resetForm(this.editForm, conta);

    this.vendasSharedCollection = this.vendaService.addVendaToCollectionIfMissing<IVenda>(this.vendasSharedCollection, conta.venda);
  }

  protected loadRelationshipsOptions(): void {
    this.vendaService
      .query()
      .pipe(map((res: HttpResponse<IVenda[]>) => res.body ?? []))
      .pipe(map((vendas: IVenda[]) => this.vendaService.addVendaToCollectionIfMissing<IVenda>(vendas, this.conta?.venda)))
      .subscribe((vendas: IVenda[]) => (this.vendasSharedCollection = vendas));
  }
}
