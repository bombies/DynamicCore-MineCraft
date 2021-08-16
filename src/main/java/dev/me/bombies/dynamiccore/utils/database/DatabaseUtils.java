package dev.me.bombies.dynamiccore.utils.database;

import dev.me.bombies.dynamiccore.constants.Databases;
import dev.me.bombies.dynamiccore.constants.PLUGIN;
import dev.me.bombies.dynamiccore.constants.Tables;
import lombok.Getter;
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

    public DatabaseUtils(Databases db) {
        try {
            String fileName = DatabaseUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile().substring(
                    DatabaseUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile().lastIndexOf("/")+1
            );
            String uri = DatabaseUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI()
                    + Databases.DIRECTORY.toString();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable(Databases db) throws SQLException {
        switch (db) {
            case DEATHS -> {
                Statement statement = con.createStatement();
                String sql = "CREATE TABLE " + Tables.PLAYER_DEATHS + "(" +
                        " uuid TEXT PRIMARY KEY," +
                        " count INTEGER" +
                        ");";
                statement.execute(sql);
            }
            case HOMES -> {
                Statement statement = con.createStatement();
                String sql = "CREATE TABLE " + Tables.PLAYER_HOMES + "(" +
                        " uuid TEXT," +
                        " home_name TEXT," +
                        " world_name TEXT," +
                        " x_coordinate INTEGER, " +
                        " y_coordinate INTEGER, " +
                        " z_coordinate INTEGER " +
                        ");";
                statement.execute(sql);
            }
            case SKILLS -> {
                Statement statement = con.createStatement();
                String sql1 = "CREATE TABLE " + Tables.SKILLS_MINING + "(" +
                        " uuid TEXT PRIMARY KEY," +
                        " current_level INTEGER NOT NULL ," +
                        " xp_next INTEGER NOT NULL" +
                        ");";
                statement.execute(sql1);
                String sql2 = "CREATE TABLE " + Tables.SKILLS_GRINDING + "(" +
                        " uuid TEXT PRIMARY KEY," +
                        " current_level INTEGER NOT NULL ," +
                        " xp_next INTEGER NOT NULL" +
                        ");";
                statement.execute(sql2);
                String sql3 = "CREATE TABLE " + Tables.SKILLS_FARMING + "(" +
                        " uuid TEXT PRIMARY KEY," +
                        " current_level INTEGER NOT NULL ," +
                        " xp_next INTEGER NOT NULL" +
                        ");";
                statement.execute(sql3);
            }
            case DIRECTORY -> throw new IllegalArgumentException("Invalid database enum!");
        }
    }

    @SneakyThrows
    public void closeConnection(Databases db) {
        this.getCon().close();
    }
}
