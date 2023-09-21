package shook.shook.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.dto.SongSwipeResponse;

@Tag(name = "Song Swipe", description = "노래 스와이프 조회 API")
public interface SongSwipeApi {

    @Operation(
        summary = "이전 노래, 이후 노래, 현재 노래 조회",
        description = "스와이프 첫 시작 시, 이전 노래와 현재 노래, 이후 노래를 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "노래 조회 성공"
    )
    @Parameter(
        name = "songId",
        description = "노래 id",
        required = true
    )
    @GetMapping
    ResponseEntity<SongSwipeResponse> showSongById(
        @PathVariable(name = "song_id") final Long songId,
        @Authenticated final MemberInfo memberInfo
    );

    @Operation(
        summary = "이전 노래 조회",
        description = "노래 id를 기준으로 좋아요 내림차 순, id 내림차 순 이전 노래 리스트를 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "이전 노래 조회 성공"
    )
    @Parameter(
        name = "songId",
        description = "노래 id",
        required = true
    )
    @GetMapping("/prev")
    ResponseEntity<List<SongResponse>> showSongsBeforeSongWithId(
        @PathVariable(name = "song_id") final Long songId,
        @Authenticated final MemberInfo memberInfo
    );

    @Operation(
        summary = "이후 노래 조회",
        description = "노래 id를 기준으로 좋아요 내림차 순, id 내림차 순 이후 노래 리스트를 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "이후 노래 조회 성공"
    )
    @Parameter(
        name = "songId",
        description = "노래 id",
        required = true
    )
    @GetMapping("/next")
    ResponseEntity<List<SongResponse>> showSongsAfterSongWithId(
        @PathVariable(name = "song_id") final Long songId,
        @Authenticated final MemberInfo memberInfo
    );

    @Operation(
        summary = "특정 장르에서 이전 노래, 이후 노래, 현재 노래 조회",
        description = "스와이프 첫 시작 시, 특정 장르의 이전 노래와 현재 노래, 이후 노래를 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "이후 노래 조회 성공"
    )
    @Parameters(
        value = {
            @Parameter(
                name = "songId",
                description = "노래 id",
                required = true
            ),
            @Parameter(
                name = "genre",
                description = "장르",
                required = true
            )
        }
    )
    @GetMapping(params = "genre")
    ResponseEntity<SongSwipeResponse> showSongsWithGenreForSwipe(
        @PathVariable(name = "song_id") final Long songId,
        @RequestParam(name = "genre") final String genre,
        @Authenticated final MemberInfo memberInfo
    );

    @Operation(
        summary = "특정 장르 이전 노래 조회",
        description = "특정 장르에서 노래 id를 기준으로 좋아요 내림차 순, id 내림차 순 이전 노래 리스트를 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "특정 장르의 이전 노래 조회 성공"
    )
    @Parameters(
        value = {
            @Parameter(
                name = "songId",
                description = "노래 id",
                required = true
            ),
            @Parameter(
                name = "genre",
                description = "장르",
                required = true
            )
        }
    )
    @GetMapping(value = "/prev", params = "genre")
    ResponseEntity<List<SongResponse>> showPrevSongsWithGenre(
        @PathVariable(name = "song_id") final Long songId,
        @RequestParam(name = "genre") final String genre,
        @Authenticated final MemberInfo memberInfo
    );

    @Operation(
        summary = "특정 장르 이후 노래 조회",
        description = "특정 장르에서 노래 id를 기준으로 좋아요 내림차 순, id 내림차 순 이후 노래 리스트를 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "특정 장르의 이후 노래 조회 성공"
    )
    @Parameters(
        value = {
            @Parameter(
                name = "songId",
                description = "노래 id",
                required = true
            ),
            @Parameter(
                name = "genre",
                description = "장르",
                required = true
            )
        }
    )
    @GetMapping(value = "/next", params = "genre")
    ResponseEntity<List<SongResponse>> showNextSongsWithGenre(
        @PathVariable(name = "song_id") final Long songId,
        @RequestParam(name = "genre") final String genre,
        @Authenticated final MemberInfo memberInfo
    );
}
