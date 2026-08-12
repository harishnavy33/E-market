package com.harishmart.listener;

import com.harishmart.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(AppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("HarishMart starting up — initializing schema...");
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("schema.sql")) {
            if (is == null) {
                log.warn("schema.sql not found on classpath; skipping schema init");
                return;
            }
            String sql;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                sql = reader.lines().collect(Collectors.joining("\n"));
            }
            try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement()) {
                for (String statement : sql.split(";")) {
                    if (!statement.trim().isEmpty()) {
                        stmt.execute(statement);
                    }
                }
            }
            log.info("Schema initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize schema", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("HarishMart shutting down...");
        DBUtil.shutdown();
    }
}