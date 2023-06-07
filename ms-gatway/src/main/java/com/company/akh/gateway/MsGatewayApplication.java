package com.company.akh.gateway;

import com.company.akh.gateway.config.properties.JwtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableConfigurationProperties(value = {JwtProperties.class})
public class MsGatewayApplication {

    private static final Logger log = LoggerFactory.getLogger(MsGatewayApplication.class);

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication app = new SpringApplication(MsGatewayApplication.class);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String serverPort = env.getProperty("local.server.port");
        log.info("Application is running in {} port, profile: {}",
                serverPort, env.getActiveProfiles());
    }

}
