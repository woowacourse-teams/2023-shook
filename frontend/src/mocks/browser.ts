import { setupWorker } from 'msw';
import { memberHandlers } from './handlers/memberHandlers';
import { searchHandlers } from './handlers/searchHandlers';
import { songsHandlers } from './handlers/songsHandlers';

export const worker = setupWorker(...songsHandlers, ...memberHandlers, ...searchHandlers);
