package me.lam.huyen.config;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2ConsoleConfiguration {

    private Logger logger = LoggerFactory.getLogger(H2ConsoleConfiguration.class);

    private int h2PortNumber = 8082;

    @Bean(initMethod = "start", destroyMethod="stop")
    public Server h2WebConsonleServer () throws SQLException {
        logger.info("Starting H2 console: http://localhost:{}", h2PortNumber);
        return Server.createWebServer("-web","-webAllowOthers","-webDaemon","-webPort", String.valueOf(h2PortNumber));
    }
}
