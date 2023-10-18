interface SingersSong {
  id: number;
  title: string;
  albumCoverUrl: string;
  videoLength: number;
}

export interface SingerDetail {
  id: number;
  singer: string;
  profileImageUrl: string;
  totalSongCount: number;
  songs: SingersSong[];
}
