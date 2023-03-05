import dayjs from 'dayjs/esm';
import { IProduto } from 'app/entities/produto/produto.model';

export interface IVenda {
  id: number;
  data?: dayjs.Dayjs | null;
  status?: string | null;
  produtos?: Pick<IProduto, 'id'>[] | null;
}

export type NewVenda = Omit<IVenda, 'id'> & { id: null };
