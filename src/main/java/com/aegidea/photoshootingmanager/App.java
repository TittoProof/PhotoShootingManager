/*
 * This is the entry point for spring boot
 * AVAILABLE COMMANDS:
 * - mvn spring-boot:run (run the project in maven)
 * - mvn verify (execute the integration test - build the project, start a tomcat container, deploy the project and perform the integration test against it)
 * and for swagger http://localhost:8080/swagger-ui.html
 */
package com.aegidea.photoshootingmanager;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author Tiziano (Titto) Fortin <tiz.fortin@gmail.com>
 */
@SpringBootApplication
public class App {

    private final static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    public void run(String... args) throws Exception {
        logger.info("Application is running");

    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true); // if something is null, skip

        // Components should define specific behaviour as needed
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

}
