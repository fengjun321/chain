package org.sparkera.nft;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;




//@MapperScan({"org.sparkera.nft.dao.mapper"})


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ServletComponentScan(basePackages = "org.sparkera.nft.filter")
@EnableScheduling
public class SparkEraNftApplication {
    public static void main(String[] args) {
        SpringApplication.run(SparkEraNftApplication.class, args);
    }

}
