import { rest } from 'msw';
import searchedSingerPreview from '@/mocks/fixtures/searchedSingerPreview.json';
import searchedSingers from '@/mocks/fixtures/searchedSingers.json';

const { BASE_URL } = process.env;

const searchHandlers = [
  // 검색 미리보기, 검색완료 페이지
  rest.get(`${BASE_URL}/search`, (req, res, ctx) => {
    const query = req.url.searchParams.get('keyword') ?? '';
    const [singer, song] = req.url.searchParams.getAll('type');
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
];

export default searchHandlers;
