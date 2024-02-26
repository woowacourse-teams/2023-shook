package shook.shook.improved.song.exception;

import java.util.Map;
import shook.shook.improved.globalexception.CustomException;
import shook.shook.improved.globalexception.ErrorCode;

public class SongDataFileReadException extends CustomException {

    public SongDataFileReadException() {
        super(ErrorCode.CAN_NOT_READ_SONG_DATA_FILE);
    }

    public SongDataFileReadException(final Map<String, String> inputValuesByProperty) {
        super(ErrorCode.CAN_NOT_READ_SONG_DATA_FILE, inputValuesByProperty);
    }
}
