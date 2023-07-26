import { rest } from 'msw';
import songsData from './mockingData.json';

const { BASE_URL } = process.env;

export const songsHandlers = [
  rest.get(`${BASE_URL}/songs/:songId`, (req, res, ctx) => {
    const { songId } = req.params;

    const song = songsData.find((song) => song.id == Number(songId));

    if (!song) {
      console.error(`id:${songId}에 해당되는 노래가 없습니다.`);
      return;
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
