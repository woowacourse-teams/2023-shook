package shook.shook.improved.support;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public abstract class UsingJpaTest {

    @Autowired
    protected EntityManager entityManager;

    protected void saveAndClearEntityManager() {
        entityManager.flush();
        entityManager.clear();
    }
}
