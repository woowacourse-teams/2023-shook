export interface SongDetailEntries {
  prevSongs: SongDetail[];
  currentSong: SongDetail;
  nextSongs: SongDetail[];
}

export interface SongDetail {
  id: number;
  title: string;
  singer: string;
  videoLength: number;
  songVideoId: string;
  albumCoverUrl: string;
  killingParts: KillingPart[];
}

export interface KillingPart {
  id: number;
  rank: number;
  start: number;
  end: number;
  voteCount: number;
  likeCount: number;
  partVideoUrl: string;
  partLength: number;
  likeStatus: boolean;
}

export interface SongVoting {
  id: number;
  title: string;
  singer: string;
  videoLength: number;
  songVideoId: string;
  albumCoverUrl: string;
  killingParts: KillingPart[];
}

export interface VotingSongList {
  prevSongs: SongVoting[];
  currentSong: SongVoting;
  nextSongs: SongVoting[];
}

export interface RecentSong {
  id: number;
  title: string;
  singer: string;
  videoLength: number;
  albumCoverUrl: string;
}
