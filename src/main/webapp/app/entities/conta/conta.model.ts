import { IVenda } from 'app/entities/venda/venda.model';

export interface IConta {
  id: number;
  nome?: string | null;
  valor?: number | null;
  venda?: Pick<IVenda, 'id'> | null;
}

export type NewConta = Omit<IConta, 'id'> & { id: null };
