import { setupWorker } from 'msw';
import { songsHandlers } from './handlers/songsHandlers';

export const worker = setupWorker(...songsHandlers);
