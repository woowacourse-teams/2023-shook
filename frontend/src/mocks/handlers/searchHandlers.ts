import { rest } from 'msw';
import searchedSingerPreview from '@/mocks/fixtures/searchedSingerPreview.json';
import searchedSingers from '@/mocks/fixtures/searchedSingers.json';

const { BASE_URL } = process.env;

const searchHandlers = [
  rest.get(`${BASE_URL}/singers`, (req, res, ctx) => {
    const query = req.url.searchParams.get('name') ?? '';
    const [singer, song] = req.url.searchParams.getAll('search');
    const testQueries = ['악동뮤지션', '악동', '뮤지션'];

    const isPreviewRequest = singer !== undefined && song === undefined;
    const isSearchRequest = song !== undefined;

    const isInTestQueries = testQueries.some(
      (testQuery) => encodeURIComponent(testQuery) === encodeURIComponent(query)
    );

    if (isPreviewRequest && isInTestQueries) {
      return res(ctx.status(200), ctx.json(searchedSingerPreview));
    }

    if (isSearchRequest && isInTestQueries) {
      return res(ctx.status(200), ctx.json(searchedSingers));
    }

    if (!isInTestQueries) {
      return res(ctx.status(200), ctx.json([]));
    }
  }),

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

export default searchHandlers;
