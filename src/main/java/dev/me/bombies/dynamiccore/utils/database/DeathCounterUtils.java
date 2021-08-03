package dev.me.bombies.dynamiccore.utils.database;

import dev.me.bombies.dynamiccore.constants.DATABASES;
import dev.me.bombies.dynamiccore.constants.PLUGIN;
import dev.me.bombies.dynamiccore.constants.TABLES;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class DeathCounterUtils extends DatabaseUtils implements IDeathCounterUtils {

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
    private HashMap<UUID, Integer> getPlayerMap() {
        HashMap<UUID, Integer> ret = new HashMap<>();
        Statement dbStat = getCon().createStatement();
        String sql = "SELECT * FROM " + TABLES.PLAYER_DEATHS + ";";
        ResultSet dbRes = dbStat.executeQuery(sql);

        while (dbRes.next())
            ret.put(UUID.fromString(dbRes.getString("uuid")), dbRes.getInt("count"));

        return ret;
    }

    public HashMap<UUID, Integer> getSortedPlayerMap() {
        return getPlayerMap().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal, LinkedHashMap::new
                ));
    }

    public Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    @SneakyThrows
    public int getPlayerCount() {
        return getPlayerMap().size();
    }

    public void closeConnection() {
        super.closeConnection(DATABASES.DEATHS);
    }
}
