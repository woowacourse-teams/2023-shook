import { rest } from 'msw';
import popularSongs from '../fixtures/popularSongs.json';
import songs from '../fixtures/songs.json';

const { BASE_URL } = process.env;

export const songsHandlers = [
  rest.get(`${BASE_URL}/songs/recommended`, (req, res, ctx) => {
    return res(ctx.json(popularSongs));
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

    return res(
      ctx.json({
        id: song.id,
        title: song.title,
        singer: song.singer,
        videoLength: song.length,
        songVideoUrl: song.video_url,
        killingParts: [],
      })
    );
  }),
];
