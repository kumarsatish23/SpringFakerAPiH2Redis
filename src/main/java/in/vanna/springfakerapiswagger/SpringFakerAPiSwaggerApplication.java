package in.vanna.springfakerapiswagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringFakerAPiSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFakerAPiSwaggerApplication.class, args);
    }

}
