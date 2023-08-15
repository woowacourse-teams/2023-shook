package shook.shook.voting_song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shook.shook.voting_song.application.dto.VotingSongResponse;
import shook.shook.voting_song.application.dto.VotingSongSwipeResponse;

@Tag(name = "VotingSong", description = "파트 수집 중인 노래 API")
public interface VotingSongApi {

    @Operation(
        summary = "파트 수집 중인 노래 전체 조회",
        description = "파트 수집 중인 노래 전체를 id 오름차 순으로 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "파트 수집 중인 노래 전체 조회 성공"
    )
    @GetMapping
    ResponseEntity<List<VotingSongResponse>> findAll();

    @Operation(
        summary = "파트 수집 중인 노래와 이전, 다음 노래 조회",
        description = "파트 수집 중인 노래와 이전, 다음 노래 리스트를 함께 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "파트 수집 중인 노래와 이전, 다음 노래 조회 성공"
    )
    @Parameter(
        name = "voting_song_id",
        description = "파트 수집 중인 노래의 id",
        required = true
    )
    @GetMapping("/{voting_song_id}")
    ResponseEntity<VotingSongSwipeResponse> findByIdForSwipe(
        @PathVariable("voting_song_id") final Long votingSongId
    );
}
