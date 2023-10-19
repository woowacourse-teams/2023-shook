import memberHandlers from './memberHandlers';
import searchHandlers from './searchHandlers';
import singerHandlers from './singerHandlers';
import songsHandlers from './songsHandlers';

const handlers = [...memberHandlers, ...searchHandlers, ...singerHandlers, ...songsHandlers];

export default handlers;
