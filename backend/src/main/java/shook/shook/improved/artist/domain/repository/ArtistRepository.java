package shook.shook.improved.artist.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shook.shook.improved.artist.domain.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("SELECT COUNT(a) FROM Artist a WHERE a.id IN :ids")
    Long countByIds(@Param("ids") List<Long> ids);

}
