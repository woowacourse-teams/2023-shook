import { rest } from 'msw';
import searchedArtistPreview from '@/mocks/fixtures/searchedArtistPreview.json';

const { BASE_URL } = process.env;

const searchHandlers = [
  rest.get(`${BASE_URL}/singers`, (req, res, ctx) => {
    const query = req.url.searchParams.get('name') ?? '';
    const search = req.url.searchParams.get('search');
    const testQueries = ['악동뮤지션', '악동', '뮤지션'];

    const isPreviewRequest = search === 'singer';
    const isInTestQueries = testQueries.some(
      (testQuery) => encodeURIComponent(testQuery) === encodeURIComponent(query)
    );

    if (isPreviewRequest && isInTestQueries) {
      return res(ctx.status(200), ctx.json(searchedArtistPreview));
    }

    return res(ctx.status(200), ctx.json([]));
  }),
];

export default searchHandlers;
