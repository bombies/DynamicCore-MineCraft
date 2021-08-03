package dev.me.bombies.dynamiccore.utils.database;

import dev.me.bombies.dynamiccore.constants.DATABASES;
import dev.me.bombies.dynamiccore.constants.PLUGIN;
import dev.me.bombies.dynamiccore.constants.TABLES;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
    @Getter
    private Connection con = null;

    public DatabaseUtils(DATABASES db) {
        try {
            String fileName = DatabaseUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile().substring(
                    DatabaseUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile().lastIndexOf("/")+1
            );
            String uri = DatabaseUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI()
                    + DATABASES.DIRECTORY.toString();
            String path = uri.substring(6).replace(fileName + "/", "").replace("%20", " ");
            String file = "/" + db;

            boolean dbExists = true;

            if (!Files.exists(Path.of(path)))
                Files.createDirectories(Paths.get(path));

            if (!Files.exists(Path.of(path + file))) {
                new File(path + file).createNewFile();
                dbExists = false;
            }

            String url = "jdbc:sqlite:" + path + file;
            con = DriverManager.getConnection(url);

            if (!dbExists)
                createTable(db);

            System.out.println(PLUGIN.PREFIX + "Successfully made a connection to: " + db.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable(DATABASES db) throws SQLException {
        switch (db) {
            case DEATHS -> {
                Statement statement = con.createStatement();
                String sql = "CREATE TABLE " + TABLES.PLAYER_DEATHS + "(" +
                        " uuid TEXT PRIMARY KEY," +
                        " count INTEGER" +
                        ");";
                statement.execute(sql);
            }
            case DIRECTORY -> throw new IllegalArgumentException("Invalid database enum!");
        }
    }

    @SneakyThrows
    public void closeConnection(DATABASES db) {
        this.getCon().close();
        System.out.println(PLUGIN.PREFIX + "Successfully closed connection to: "+ db.toString());
    }
}
