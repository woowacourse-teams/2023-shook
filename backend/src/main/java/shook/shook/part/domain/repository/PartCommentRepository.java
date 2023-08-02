package shook.shook.part.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.part.domain.PartComment;

@Repository
public interface PartCommentRepository extends JpaRepository<PartComment, Long> {

}
