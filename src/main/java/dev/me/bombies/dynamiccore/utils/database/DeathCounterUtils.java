package dev.me.bombies.dynamiccore.utils.database;

import dev.me.bombies.dynamiccore.constants.DATABASES;
import dev.me.bombies.dynamiccore.constants.TABLES;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeathCounterUtils extends DatabaseUtils {

    public DeathCounterUtils() {
        super(DATABASES.DEATHS);
    }

    @SneakyThrows
    private void addUser(String uuid) {
        Statement statement = this.getCon().createStatement();
        String sql = "INSERT INTO " + TABLES.PLAYER_DEATHS + " VALUES( '"+uuid+"', 0 )";
        statement.executeUpdate(sql);
    }

    @SneakyThrows
    public void incrementDeathCount(String uuid) {
        if (getDeathCount(uuid) == -1)
            addUser(uuid);

        int newCount = getDeathCount(uuid)+1;

        Statement statement = this.getCon().createStatement();
        String sql = "UPDATE " + TABLES.PLAYER_DEATHS + " SET count="+newCount+" WHERE uuid='"+uuid+"';";
        statement.executeUpdate(sql);
    }

    @SneakyThrows
    public int getDeathCount(String uuid) {
        Statement statement = this.getCon().createStatement();
        String sql = "SELECT * FROM " + TABLES.PLAYER_DEATHS + " WHERE uuid='"+uuid+"';";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next())
            return resultSet.getInt("count");
        return -1;
    }

    @SneakyThrows
    public void closeConnection() {
        this.getCon().close();
    }
}
