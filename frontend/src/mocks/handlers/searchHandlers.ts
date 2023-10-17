import { rest } from 'msw';
import searchedSinger from '@/mocks/fixtures/searchedSinger.json';
import searchedSingerPreview from '@/mocks/fixtures/searchedSingerPreview.json';

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
      return res(ctx.status(200), ctx.json(searchedSinger));
    }

    if (!isInTestQueries) {
      return res(ctx.status(200), ctx.json([]));
    }
  }),
];

export default searchHandlers;
