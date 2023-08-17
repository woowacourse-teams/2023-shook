import { rest } from 'msw';
import comments from '../fixtures/comments.json';
import nextSongEntries from '../fixtures/nextSongEntries.json';
import popularSongs from '../fixtures/popularSongs.json';
import prevSongEntries from '../fixtures/prevSongEntries.json';
import songEntries from '../fixtures/songEntries.json';
import type { KillingPartPostRequest } from '@/shared/types/killingPart';

const { BASE_URL } = process.env;

export const songsHandlers = [
  rest.get(`${BASE_URL}/songs/high-liked`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(popularSongs));
  }),

  rest.get(`${BASE_URL}/songs/:songId/parts/:partId/comments`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(comments));
  }),

  rest.post(`${BASE_URL}/songs/:songId/parts/:partId/comments`, async (req, res, ctx) => {
    return res(ctx.status(201));
  }),

  rest.post(`${BASE_URL}/songs/:songId/parts`, async (req, res, ctx) => {
    const { length, startSecond } = await req.json<KillingPartPostRequest>();
    const endSecond = startSecond + length;

    const response = {
      rank: 1,
      voteCount: 1,
      partVideoUrl: `https://www.youtube.com/embed/UUSbUBYqU_8?start=${startSecond}&end=${endSecond}`,
    };

    return res(ctx.status(200), ctx.json(response));
  }),

  rest.put(`${BASE_URL}/songs/:songId/parts/:partId/likes`, (req, res, ctx) => {
    return res(ctx.status(201));
  }),

  rest.get(`${BASE_URL}/songs/:song_id`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(songEntries));
  }),

  rest.get(`${BASE_URL}/songs/:song_id:/prev`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(prevSongEntries));
  }),

  rest.get(`${BASE_URL}/songs/:song_id/next`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(nextSongEntries));
  }),
];
