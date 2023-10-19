import { rest } from 'msw';
import likeParts from '@/mocks/fixtures/likeParts.json';
import myParts from '@/mocks/fixtures/myParts.json';

const { BASE_URL } = process.env;

const memberHandlers = [
  rest.get(`${BASE_URL}/my-page/like-parts`, (req, res, ctx) => {
    return res(ctx.json(likeParts));
  }),

  rest.get(`${BASE_URL}/my-page/my-parts`, (req, res, ctx) => {
    return res(ctx.json(myParts));
  }),
];

export default memberHandlers;
