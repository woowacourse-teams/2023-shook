import { setupWorker } from 'msw';
import { memberHandlers } from './handlers/memberHandlers';
import { songsHandlers } from './handlers/songsHandlers';

export const worker = setupWorker(...songsHandlers, ...memberHandlers);
