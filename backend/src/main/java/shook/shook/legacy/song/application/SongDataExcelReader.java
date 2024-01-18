package shook.shook.legacy.song.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import shook.shook.legacy.song.domain.Artist;
import shook.shook.legacy.song.domain.Genre;
import shook.shook.legacy.song.domain.KillingParts;
import shook.shook.legacy.song.domain.Song;
import shook.shook.legacy.song.domain.killingpart.KillingPart;
import shook.shook.legacy.song.exception.SongDataFileReadException;

@Component
public class SongDataExcelReader {

    private static final DataFormatter stringDataFormatter = new DataFormatter();
    private static final int VIDEO_ID_INDEX = 1;
    private static final int FIRST_PAGE_INDEX = 0;
    private static final int SONG_LENGTH_INDEX = 1;
    private static final int START_SECOND_INDEX = 0;

    private final String videoUrlDelimiter;
    private final String killingpartDataDelimiter;
    private final String songLengthSuffix;

    public SongDataExcelReader(
        @Value("${excel.video-url-delimiter}") final String videoUrlDelimiter,
        @Value("${excel.killingpart-data-delimiter}") final String killingpartDataDelimiter,
        @Value("${excel.song-length-suffix}") final String songLengthSuffix
    ) {
        this.videoUrlDelimiter = videoUrlDelimiter;
        this.killingpartDataDelimiter = killingpartDataDelimiter;
        this.songLengthSuffix = songLengthSuffix;
    }

    public List<Song> parseToSongs(final MultipartFile excel) {
        final List<Song> songs = new ArrayList<>();
        try (
            final InputStream inputStream = excel.getInputStream();
            final XSSFWorkbook workbook = new XSSFWorkbook(inputStream)
        ) {
            final XSSFSheet sheet = workbook.getSheetAt(FIRST_PAGE_INDEX);
            final Iterator<Row> sheetIterator = sheet.iterator();

            passColumnNameRow(sheetIterator);
            sheetIterator.forEachRemaining(row -> {
                final Optional<Song> song = parseToSong(row);
                song.ifPresent(songs::add);
            });
        } catch (IOException e) {
            throw new SongDataFileReadException();
        }
        Collections.reverse(songs);

        return songs;
    }

    private void passColumnNameRow(final Iterator<Row> iterator) {
        if (iterator.hasNext()) {
            iterator.next();
        }
    }

    private Optional<Song> parseToSong(final Row currentRow) {
        final Iterator<Cell> cellIterator = currentRow.iterator();

        final String title = getString(cellIterator);
        final String singer = getString(cellIterator);
        final String albumCoverUrl = getString(cellIterator);
        final String genre = getString(cellIterator);
        final int length = getIntegerCell(cellIterator);
        final String videoUrl = getString(cellIterator);

        if (isNotValidData(title, singer, albumCoverUrl, genre, length, videoUrl)) {
            return Optional.empty();
        }

        final String videoId = videoUrl.split(videoUrlDelimiter)[VIDEO_ID_INDEX];
        final Optional<KillingParts> killingParts = getKillingParts(cellIterator);

        return killingParts.map(
            parts -> new Song(title, videoId, albumCoverUrl,
                              new Artist("image", "name"), length,
                              Genre.from(genre),
                              parts));
    }

    private String getString(final Iterator<Cell> iterator) {
        final String cellValue = stringDataFormatter.formatCellValue(iterator.next());

        return cellValue.trim();
    }

    private int getIntegerCell(final Iterator<Cell> iterator) {
        return (int) iterator.next().getNumericCellValue();
    }

    private boolean isNotValidData(
        final String title,
        final String singer,
        final String albumCoverUrl,
        final String genre,
        final int length,
        final String videoUrl
    ) {
        return title.isBlank() ||
            singer.isBlank() ||
            albumCoverUrl.isBlank() ||
            genre.isBlank() ||
            videoUrl.isBlank() || !videoUrl.contains(videoUrlDelimiter) ||
            length == 0;
    }

    private Optional<KillingParts> getKillingParts(final Iterator<Cell> cellIterator) {
        final Optional<KillingPart> first = toKillingPart(getString(cellIterator));
        final Optional<KillingPart> second = toKillingPart(getString(cellIterator));
        final Optional<KillingPart> third = toKillingPart(getString(cellIterator));

        if (first.isEmpty() || second.isEmpty() || third.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new KillingParts(List.of(first.get(), second.get(), third.get())));
    }

    private Optional<KillingPart> toKillingPart(final String killingPart) {
        final String[] killingPartDataElements = killingPart.split(killingpartDataDelimiter);
        if (killingPartDataElements.length != 2) {
            return Optional.empty();
        }

        final int start = Integer.parseInt(killingPartDataElements[START_SECOND_INDEX].strip());
        final String songLength = killingPartDataElements[SONG_LENGTH_INDEX].strip();
        if (!songLength.endsWith(songLengthSuffix)) {
            return Optional.empty();
        }
        final int length = Integer.parseInt(songLength.split(songLengthSuffix)[0]);

        return Optional.of(KillingPart.forSave(start, length));
    }
}
