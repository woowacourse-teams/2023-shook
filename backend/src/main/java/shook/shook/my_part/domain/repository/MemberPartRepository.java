package shook.shook.my_part.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shook.shook.my_part.domain.MemberPart;

public interface MemberPartRepository extends JpaRepository<MemberPart, Long> {

}
