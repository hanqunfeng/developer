package org.pyf.developer;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class WebApplication {


    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
