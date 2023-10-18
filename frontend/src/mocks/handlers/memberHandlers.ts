import { rest } from 'msw';

const { BASE_URL } = process.env;

export const memberHandlers = [
  rest.get(`${BASE_URL}/my-page/like-parts`, (req, res, ctx) => {
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
        {
          songId: 7,
          title: 'Super Shy',
          singer: 'New Jeans',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 66,
          end: 76,
        },
        {
          songId: 8,
          title: 'Super Shy',
          singer: 'New Jeans',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 66,
          end: 76,
        },
        {
          songId: 9,
          title: 'Super Shy',
          singer: 'New Jeans',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 66,
          end: 76,
        },
        {
          songId: 10,
          title: 'Super Shy',
          singer: 'New Jeans',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 66,
          end: 76,
        },
        {
          songId: 11,
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

  rest.get(`${BASE_URL}/my-page/my-parts`, (req, res, ctx) => {
    return res(
      ctx.json([
        {
          songId: 1,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 11,
          start: 1,
          end: 11,
        },
        {
          songId: 2,
          title:
            '제목이 너무 길다면 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데?',
          singer:
            '가수 이름도 너무 길다면 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데? 어떻게 할 건데?',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 12,
          start: 13,
          end: 23,
        },
        {
          songId: 3,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 13,
          start: 36,
          end: 46,
        },
        {
          songId: 4,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 14,
          start: 77,
          end: 87,
        },
        {
          songId: 5,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 15,
          start: 102,
          end: 112,
        },
        {
          songId: 6,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 190,
          end: 200,
        },
        {
          songId: 7,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 190,
          end: 200,
        },

        {
          songId: 8,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 190,
          end: 200,
        },
        {
          songId: 9,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 190,
          end: 200,
        },
        {
          songId: 10,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 190,
          end: 200,
        },
        {
          songId: 11,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 190,
          end: 200,
        },
        {
          songId: 12,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 190,
          end: 200,
        },
        {
          songId: 13,
          title: '후라이의 꿈',
          singer: 'AKMU (악뮤)',
          albumCoverUrl:
            'https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg/melon/resize/120/quality/80/optimize',
          partId: 16,
          start: 190,
          end: 200,
        },
      ])
    );
  }),
];

export default memberHandlers;
