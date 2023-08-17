package shook.shook.voting_song.application;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.voting_song.application.dto.VotingSongRegisterRequest;
import shook.shook.voting_song.application.dto.VotingSongResponse;
import shook.shook.voting_song.application.dto.VotingSongSwipeResponse;
import shook.shook.voting_song.domain.VotingSong;
import shook.shook.voting_song.domain.repository.VotingSongRepository;
import shook.shook.voting_song.exception.VotingSongException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VotingSongService {

    private static final int BEFORE_SONG_COUNT = 4;
    private static final int AFTER_SONG_COUNT = 4;

    private final VotingSongRepository votingSongRepository;

    @Transactional
    public void register(final VotingSongRegisterRequest request) {
        votingSongRepository.save(request.getVotingSong());
    }

    //기존에 정렬후 조회 2번 쿼리에서 정렬하지 않고 startId <= && <= endId 하는 쿼리1번으로 바꿔보는거 어떨까요?
    //현재 테스트 메서드에서 테스트해보니 시간이 아래와 같이 걸리더라구요
    // 기존 쿼리 : 95ms
    // 변경 쿼리 : 5ms
    public VotingSongSwipeResponse findAllForSwipeById(final Long id) {
        final VotingSong votingSong = votingSongRepository.findById(id)
            .orElseThrow(VotingSongException.VotingSongNotExistException::new);

        final long startId = Math.max(0, id - BEFORE_SONG_COUNT);
        final long endId = id + AFTER_SONG_COUNT;

        final List<VotingSong> songsForSwipe =
            votingSongRepository.findByIdGreaterThanEqualAndIdLessThanEqual(startId, endId)
                .stream()
                .sorted(Comparator.comparing(VotingSong::getId))
                .toList();

        return VotingSongSwipeResponse.of(songsForSwipe, votingSong);
    }

    //findAll 할 경우 jpa 가 select 쿼리를 통해서 모든 데이터를 읽어오기 때문에 비즈니스 로직으로 sort 를 해도 충분할 것 같아요~
    //현재 테스트 메서드에서 테스트해보니 시간이 아래와 같이 걸리더라구요
    // DB로 정렬 : 87ms
    // 비즈니스 로직으로 정렬 : 6ms
    public List<VotingSongResponse> findAll() {
        return votingSongRepository.findAll().stream()
            .sorted(Comparator.comparing(VotingSong::getId))
            .map(VotingSongResponse::from)
            .toList();
    }
}
