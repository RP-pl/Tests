package Spr;
import Spr.Data.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication(scanBasePackages={"Spr.Data", "Spr.web"})
@EnableAutoConfiguration
@EnableJdbcRepositories(basePackageClasses = UserRepository.class)
@ComponentScan(basePackages = {"Spr.Data","Spr.web"})
@EntityScan("Spr")

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
