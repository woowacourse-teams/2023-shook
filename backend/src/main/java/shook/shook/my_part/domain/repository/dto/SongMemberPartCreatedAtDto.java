package shook.shook.my_part.domain.repository.dto;

import shook.shook.my_part.domain.MemberPart;
import shook.shook.song.domain.Song;

public interface SongMemberPartCreatedAtDto {

    Song getSong();

    MemberPart getMemberPart();
}
