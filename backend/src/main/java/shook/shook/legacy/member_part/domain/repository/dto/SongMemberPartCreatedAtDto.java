package shook.shook.legacy.member_part.domain.repository.dto;

import shook.shook.legacy.member_part.domain.MemberPart;
import shook.shook.legacy.song.domain.Song;

public interface SongMemberPartCreatedAtDto {

    Song getSong();

    MemberPart getMemberPart();
}
