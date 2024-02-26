package shook.shook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EntityScan(basePackages = "shook.shook.improved")
@EnableJpaRepositories(basePackages = "shook.shook.improved")
@SpringBootApplication(scanBasePackages = "shook.shook.improved")
public class ShookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShookApplication.class, args);
    }
}
