type VideoUrl = `https://www.youtube.com/${string}`;

export interface SongDetail {
  title: string;
  singer: string;
  videoLength: number;
  videoUrl: VideoUrl;
}
