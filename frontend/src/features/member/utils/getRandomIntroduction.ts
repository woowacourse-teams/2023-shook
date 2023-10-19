import { introductions } from '../constants/introductions';

const getRandomIntroduction = (index = Math.floor(Math.random() * introductions.length)) => {
  return introductions[index];
};

export default getRandomIntroduction;
