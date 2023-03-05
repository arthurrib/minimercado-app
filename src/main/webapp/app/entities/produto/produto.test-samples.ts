import { IProduto, NewProduto } from './produto.model';

export const sampleWithRequiredData: IProduto = {
  id: 79836,
  nome: 'Extended end-to-end Steel',
};

export const sampleWithPartialData: IProduto = {
  id: 87796,
  nome: 'SMTP Loan',
};

export const sampleWithFullData: IProduto = {
  id: 8772,
  nome: 'hybrid Market Rustic',
  valor: 10056,
};

export const sampleWithNewData: NewProduto = {
  nome: 'withdrawal compressing mission-critical',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
