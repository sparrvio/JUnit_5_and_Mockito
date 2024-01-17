package edu.school21.repositories;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EmbeddedDataSourceTest {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    EmbeddedDatabase db;

    @BeforeEach
    public void init(){
        db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
    }

    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX, OS.WINDOWS})
    @DisabledOnJre({JRE.JAVA_13, JRE.JAVA_14})
    void isConnectionTrue() {
        init();
        Connection conn;
        try {
            conn = db.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(conn);
        try {
            assertFalse(conn.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterEach
    void tearDown() {
        db.shutdown();
    }
}