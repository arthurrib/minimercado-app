import { IEstoque } from 'app/entities/estoque/estoque.model';
import { IVenda } from 'app/entities/venda/venda.model';

export interface IProduto {
  id: number;
  nome?: string | null;
  valor?: number | null;
  estoque?: Pick<IEstoque, 'id'> | null;
  vendas?: Pick<IVenda, 'id'>[] | null;
}

export type NewProduto = Omit<IProduto, 'id'> & { id: null };
