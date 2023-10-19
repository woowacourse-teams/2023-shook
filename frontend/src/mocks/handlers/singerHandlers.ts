import { rest } from 'msw';
import searchedSingers from '@/mocks/fixtures/searchedSingers.json';

const { BASE_URL } = process.env;

const singerHandlers = [
  // 가수 상세페이지
  rest.get(`${BASE_URL}/singers/:singerId`, (req, res, ctx) => {
    const { singerId } = req.params;

    const numberSingerId = Number(singerId as string);
    const searchedSinger = searchedSingers[numberSingerId - 1];

    if (searchedSinger !== undefined) {
      return res(ctx.status(200), ctx.json(searchedSinger));
    }

    return res(ctx.status(400), ctx.json({}));
  }),
];

export default singerHandlers;
