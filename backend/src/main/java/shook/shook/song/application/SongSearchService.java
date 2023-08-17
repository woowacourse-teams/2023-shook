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

    public List<SearchedSongResponse> findAllBySingerAndTitle(
        final String singer,
        final String title
    ) {
        final List<Song> foundSongs = findAllSongWithExistingProperty(singer, title);

        return toSearchResponse(foundSongs);
    }

    private List<Song> findAllSongWithExistingProperty(final String singer, final String title) {
        final boolean isSingerEmpty = StringChecker.isNullOrBlank(singer);
        final boolean isTitleEmpty = StringChecker.isNullOrBlank(title);

        if (isSingerEmpty && isTitleEmpty) {
            return Collections.emptyList();
        }
        if (isSingerEmpty) {
            return songRepository.findAllByTitleIgnoringCase(title.strip());
        }
        if (isTitleEmpty) {
            return songRepository.findAllBySingerIgnoringCase(singer.strip());
        }

        return songRepository.findAllByTitleAndSingerIgnoringCase(title.strip(), singer.strip());
    }

    private List<SearchedSongResponse> toSearchResponse(final List<Song> songs) {
        return songs.stream()
            .map(SearchedSongResponse::from)
            .toList();
    }
}
