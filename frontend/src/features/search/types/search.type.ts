interface SingersSong {
  id: number;
  title: string;
  albumCoverUrl: string;
  videoLength: number;
}

export interface SingerSearchPreview {
  id: number;
  singer: string;
  profileImageUrl: string;
}

export interface SingerSearchResult {
  id: number;
  singer: string;
  profileImageUrl: string;
  totalSongCount: number;
  songs: SingersSong[];
}
