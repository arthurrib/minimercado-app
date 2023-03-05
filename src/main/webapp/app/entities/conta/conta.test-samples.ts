import { IConta, NewConta } from './conta.model';

export const sampleWithRequiredData: IConta = {
  id: 17789,
  nome: 'RAM reboot',
};

export const sampleWithPartialData: IConta = {
  id: 11912,
  nome: 'Czech',
};

export const sampleWithFullData: IConta = {
  id: 99072,
  nome: 'Director Flats Account',
  valor: 74851,
};

export const sampleWithNewData: NewConta = {
  nome: 'hard methodologies Soft',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
