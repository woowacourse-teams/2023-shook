import { rest } from 'msw';
import popularSongs from '../fixtures/popularSongs.json';
import songsTemp from '../fixtures/songsTemp.json';
import type { KillingPartPostRequest } from '@/shared/types/killingPart';

const { BASE_URL } = process.env;

export const songsHandlers = [
  rest.get(`${BASE_URL}/songs/high-liked`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(popularSongs));
  }),

  rest.get(`${BASE_URL}/songs/:songId/parts/:partId/comments`, (req, res, ctx) => {
    const comments = [
      { id: 1, content: '1번 댓글입니다.', createdAt: '2023-08-01T16:02:13.422Z' },
      {
        id: 2,
        content: '2번 댓글입니다. 200자 입니다. '.repeat(10),
        createdAt: '2023-08-02T16:02:13.422Z',
      },
      { id: 3, content: '3번 댓글입니다.', createdAt: '2023-08-02T16:02:13.422Z' },
      { id: 4, content: '4번 댓글입니다.', createdAt: '2023-08-02T16:02:13.422Z' },
      { id: 5, content: '5번 댓글입니다.', createdAt: '2023-08-02T16:02:13.422Z' },
      { id: 6, content: '6번 댓글입니다.', createdAt: '2023-08-02T16:02:13.422Z' },
      { id: 7, content: '7번 댓글입니다.', createdAt: '2023-08-02T16:02:13.422Z' },
      { id: 8, content: '8번 댓글입니다.', createdAt: '2023-08-02T16:02:13.422Z' },
      { id: 9, content: '9번 댓글입니다.', createdAt: '2023-08-02T16:02:13.422Z' },
    ];

    return res(ctx.json(comments));
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
    return res(ctx.status(200), ctx.json(songsTemp));
  }),

  // rest.get(`${BASE_URL}/songs/:song_id:/prev`, (req, res, ctx) => {}),

  // rest.get(`${BASE_URL}/songs/:song_id/next`, (req, res, ctx) => {}),
];
