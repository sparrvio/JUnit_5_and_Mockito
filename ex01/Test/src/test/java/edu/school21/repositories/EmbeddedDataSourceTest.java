package edu.school21.repositories;

import org.junit.jupiter.api.*;
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
    void init(){
        db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:./schema.sql")
                .addScript("classpath:./data.sql")
                .build();
    }

    @Test
    void isConnectionTrue() throws SQLException {
        Connection conn = db.getConnection();
        assertNotNull(conn);
        assertFalse(conn.isClosed());
    }

    @AfterEach
    void tearDown(){
        db.shutdown();
    }

}





