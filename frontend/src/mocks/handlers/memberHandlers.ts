import { rest } from 'msw';

const { BASE_URL } = process.env;

const memberHandlers = [
  rest.get(`${BASE_URL}/my-page`, (req, res, ctx) => {
    return res(
      ctx.json([
        {
          songId: 1,
          title: 'Super Shy',
          singer: 'New Jeans',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 11,
          start: 10,
          end: 20,
        },
        {
          songId: 2,
          title:
            '제목이 너무 길다면 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데?',
          singer:
            '가수 이름도 너무 길다면 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데?',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 12,
          start: 5,
          end: 15,
        },
        {
          songId: 3,
          title: 'Super Shy',
          singer: 'New Jeans',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 13,
          start: 66,
          end: 71,
        },
        {
          songId: 4,
          title: 'Super Shy',
          singer: 'New Jeans',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 14,
          start: 100,
          end: 115,
        },
        {
          songId: 5,
          title: 'Super Shy',
          singer: 'New Jeans',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 15,
          start: 72,
          end: 82,
        },
        {
          songId: 6,
          title: 'Super Shy',
          singer: 'New Jeans',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 66,
          end: 76,
        },
      ])
    );
  }),
];

export default memberHandlers;
