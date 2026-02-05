package uz.anvarovich.barber_personal_website_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BarberPersonalWebsiteApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BarberPersonalWebsiteApiApplication.class, args);
    }

}