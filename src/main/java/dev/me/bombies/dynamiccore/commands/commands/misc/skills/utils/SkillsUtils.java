package dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils;

import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Databases;
import dev.me.bombies.dynamiccore.constants.GUIs;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.utils.database.DatabaseUtils;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class SkillsUtils extends DatabaseUtils {

    SkillsUtils() {
        super(Databases.SKILLS);
    }

    @SneakyThrows
    private boolean hasData(UUID uuid, Tables table) {
        Statement statement = getCon().createStatement();
        String sql = "SELECT current_level FROM " + table + " WHERE uuid='"+uuid+"';";
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet.next();
    }

    @SneakyThrows
    private void addPlayer(UUID uuid, Tables table) {
        if (!isValidTable(table))
            throw new IllegalArgumentException("Invalid database table!");

        if (hasData(uuid, table))
            throw new IllegalStateException("There is already an entry for user with UUID " + uuid);

        Statement statement = getCon().createStatement();
        String sql = "INSERT INTO " + table + " VALUES( '"+ uuid +"', 0, 0 );";
        statement.executeUpdate(sql);
    }

    @SneakyThrows
    public int getPlayerLevel(UUID uuid, Tables table) {
        if (!isValidTable(table))
            throw new IllegalArgumentException("Invalid database table!");

        if (!hasData(uuid, table))
            addPlayer(uuid, table);

        Statement statement = getCon().createStatement();
        String sql = "SELECT current_level FROM " + table + " WHERE uuid='"+ uuid +"';";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next())
            return resultSet.getInt("current_level");
        return -1;
    }

    @SneakyThrows
    public void setPlayerLevel(UUID uuid, Tables table, int level) {
        if (!isValidTable(table))
            throw new IllegalArgumentException("Invalid database table!");

        if (!hasData(uuid, table)) {
            addPlayer(uuid, table); return;
        }

        Statement statement = getCon().createStatement();
        String sql = "UPDATE " + table + " SET current_level="+level+" WHERE uuid='"+ uuid +"';";
        statement.executeUpdate(sql);
    }

    @SneakyThrows
    public void incrementPlayerLevel(UUID uuid, Tables table) {
        if (!isValidTable(table))
            throw new IllegalArgumentException("Invalid database table!");

        if (!hasData(uuid, table)) {
            addPlayer(uuid, table); return;
        }

        int currentLevel = getPlayerLevel(uuid, table);

        setPlayerLevel(uuid, table, ++currentLevel);
    }

    @SneakyThrows
    public int getXP(UUID uuid, Tables table) {
        if (!isValidTable(table))
            throw new IllegalArgumentException("Invalid database table!");

        if (!hasData(uuid, table))
            addPlayer(uuid, table);

        Statement statement = getCon().createStatement();
        String sql = "SELECT xp_next FROM " + table + " WHERE uuid='"+uuid+"';";
        ResultSet dbRes = statement.executeQuery(sql);
        while (dbRes.next())
            return dbRes.getInt("xp_next");
        return -1;
    }

    @SneakyThrows
    public void setXP(UUID uuid, Tables table, int xp) {
        if (!isValidTable(table))
            throw new IllegalArgumentException("Invalid database table!");

        if (!hasData(uuid, table)) {
            addPlayer(uuid, table); return;
        }

        Statement statement = getCon().createStatement();
        String sql = "UPDATE " + table + " SET xp_next="+xp+" WHERE uuid='"+uuid+"';";
        statement.executeUpdate(sql);
    }

    @SneakyThrows
    public void incrementXP(UUID uuid, Tables table) {
        if (!isValidTable(table))
            throw new IllegalArgumentException("Invalid database table!");

        if (!hasData(uuid, table)) {
            addPlayer(uuid, table); return;
        }

        int currentXP = getXP(uuid, table);

        Statement statement = getCon().createStatement();
        String sql = "UPDATE " + table + " SET xp_next="+(++currentXP)+" WHERE uuid='"+ uuid +"';";
        statement.executeUpdate(sql);
    }

    @SneakyThrows
    public void levelUp(UUID uuid, Tables table) {
        incrementPlayerLevel(uuid, table);
        setXP(uuid, table, 0);
    }

    private boolean isValidTable(Tables table) {
        switch (table) {
            case SKILLS_MINING, SKILLS_FARMING, SKILLS_GRINDING -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public int getNextXPForLevel(GUIs skill, int level) {
        final double baseAmount;
        final double increaseAmount;
        switch (skill) {
            case SKILLS_MINING ->  {
                baseAmount = Config.getDouble(Config.SKILLS_MINING_SCALE_BASE);
                increaseAmount = Config.getDouble(Config.SKILLS_MINING_SCALE_INCREASE);
            }
            case SKILLS_GRINDING -> {
                baseAmount = Config.getDouble(Config.SKILLS_GRINDING_SCALE_BASE);
                increaseAmount = Config.getDouble(Config.SKILLS_GRINDING_SCALE_INCREASE);
            }
            case SKILLS_FARMING -> {
                baseAmount = Config.getDouble(Config.SKILLS_FARMING_SCALE_BASE);
                increaseAmount = Config.getDouble(Config.SKILLS_FARMING_SCALE_INCREASE);
            }
            default -> throw new IllegalArgumentException("Invalid enum passed!");
        }
        return (int) Math.ceil(baseAmount + ((increaseAmount * (level - 1)) * 25));
    }

    public void closeConnection() {
        this.closeConnection(Databases.SKILLS);
    }

    public static SkillsUtils ins = new SkillsUtils();
}
