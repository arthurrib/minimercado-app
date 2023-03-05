import dayjs from 'dayjs/esm';

import { IVenda, NewVenda } from './venda.model';

export const sampleWithRequiredData: IVenda = {
  id: 28928,
  data: dayjs('2023-03-05T07:30'),
};

export const sampleWithPartialData: IVenda = {
  id: 6637,
  data: dayjs('2023-03-05T12:37'),
  status: 'Chicken Practical',
};

export const sampleWithFullData: IVenda = {
  id: 24267,
  data: dayjs('2023-03-05T02:26'),
  status: 'transmit integrate',
};

export const sampleWithNewData: NewVenda = {
  data: dayjs('2023-03-05T05:31'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
