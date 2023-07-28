package shook.shook.song.application;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.song.application.dto.SearchedSongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.util.StringChecker;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SongSearchService {

    private final SongRepository songRepository;

    public List<SearchedSongResponse> findAllBySinger(final String singer) {
        if (StringChecker.isNullOrBlank(singer)) {
            return toSearchResponse(Collections.emptyList());
        }

        final List<Song> songs = songRepository.findAllBySingerIgnoringCase(singer.strip());

        return toSearchResponse(songs);
    }

    private List<SearchedSongResponse> toSearchResponse(final List<Song> songs) {
        return songs.stream()
            .map(SearchedSongResponse::from)
            .toList();
    }

    public List<SearchedSongResponse> findAllByTitle(final String title) {
        if (StringChecker.isNullOrBlank(title)) {
            return toSearchResponse(Collections.emptyList());
        }

        final List<Song> songs = songRepository.findAllByTitleIgnoringCase(title.strip());

        return toSearchResponse(songs);
    }

    public List<SearchedSongResponse> findAllBySingerAndTitle(
        final String singer,
        final String title
    ) {
        if (StringChecker.isNullOrBlank(singer)) {
            return findAllByTitle(title);
        }
        if (StringChecker.isNullOrBlank(title)) {
            return findAllBySinger(singer);
        }

        final List<Song> songs = songRepository.findAllByTitleAndSingerIgnoringCase(title, singer);

        return toSearchResponse(songs);
    }
}
