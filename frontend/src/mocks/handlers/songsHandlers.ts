import { rest } from 'msw';
import popularSongs from '../fixtures/popularSongs.json';
import songs from '../fixtures/songs.json';

const { BASE_URL } = process.env;

export const songsHandlers = [
  rest.get(`${BASE_URL}/songs/high-voted`, (req, res, ctx) => {
    return res(ctx.json(popularSongs));
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
    const { songId, partId } = req.params;
    const content = await req.json();

    console.log(songId, partId, JSON.parse(content));

    return res(ctx.status(201));
  }),

  rest.get(`${BASE_URL}/songs/:songId`, (req, res, ctx) => {
    const { songId } = req.params;

    const song = songs.find((song) => song.id == Number(songId));

    if (!song) {
      return res(
        ctx.status(404),
        ctx.json({ message: `id:${songId}에 해당되는 노래가 없습니다.` })
      );
    }

    return res(ctx.status(200), ctx.json(song));
  }),
];
