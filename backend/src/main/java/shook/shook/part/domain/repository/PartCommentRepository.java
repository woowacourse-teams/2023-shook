package shook.shook.part.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shook.shook.part.domain.PartComment;

public interface PartCommentRepository extends JpaRepository<PartComment, Long> {

}
