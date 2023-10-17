import memberHandlers from './memberHandlers';
import searchHandlers from './searchHandlers';
import songsHandlers from './songsHandlers';

const handlers = [...memberHandlers, ...searchHandlers, ...songsHandlers];

export default handlers;
