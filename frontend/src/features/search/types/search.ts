interface ArtistsSong {
  id: number;
  title: string;
  albumCoverUrl: string;
  videoLength: number;
}

export interface ArtistSearchPreview {
  id: number;
  singer: string;
  profileImageUrl: string;
}

export interface ArtistSearchResult {
  id: number;
  singer: string;
  profileImageUrl: string;
  totalSongCount: number;
  songs: ArtistsSong[];
}
