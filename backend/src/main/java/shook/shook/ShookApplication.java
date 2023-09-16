package shook.shook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ShookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShookApplication.class, args);
    }
}
