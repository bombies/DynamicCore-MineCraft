package dev.me.bombies.dynamiccore.utils.database;

import dev.me.bombies.dynamiccore.constants.Databases;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.utils.plugin.Coordinates;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeUtils extends DatabaseUtils {

    public HomeUtils() {
        super(Databases.HOMES);
    }

    /**
     * Add a home for a player to the database
     * @param uuid UUID of the player
     * @param home Name of the home the player wants to set
     * @param loc The location of the home
     */
    @SneakyThrows
    public void addHome(String uuid, String home, Location loc) {
        Statement dbStat = getCon().createStatement();
        String sql;
        if (hasHome(uuid, home.toLowerCase())) {
            sql = "UPDATE " + Tables.PLAYER_HOMES +
                    " SET x_coordinate="+loc.getBlockX()+" , y_coordinate="+loc.getBlockY()+" , z_coordinate="+loc.getBlockZ()+" " +
                    " WHERE uuid='"+uuid+"' AND home_name='"+home.toLowerCase()+"';";
        } else
            sql = "INSERT INTO " + Tables.PLAYER_HOMES + " VALUES(" +
                "'"+uuid+"'," +
                " '"+home.toLowerCase()+"'," +
                " '"+loc.getWorld().getName()+"'," +
                " "+loc.getBlockX()+"," +
                " "+loc.getBlockY()+"," +
                " "+loc.getBlockZ()+
                ");";
        dbStat.executeUpdate(sql);
    }

    /**
     * Remove a specific home from a user
     * @param uuid UUID of the player
     * @param home Name of the home to be removed
     */
    @SneakyThrows
    public void removeHome(String uuid, String home) {
        if (!hasHome(uuid, home.toLowerCase()))
            throw new NullPointerException("The player with uuid "+uuid+" doesn't have any homes called "+home);

        Statement dbStat = getCon().createStatement();
        String sql = "DELETE FROM " + Tables.PLAYER_HOMES + " WHERE uuid='"+uuid+"' AND home_name='"+home.toLowerCase()+"';";
        dbStat.executeUpdate(sql);
    }

    public void teleportPlayerToHome(String uuid, String home) {
        if (Bukkit.getPlayer(UUID.fromString(uuid)) != null)
            teleportPlayerToHome(Bukkit.getPlayer(UUID.fromString(uuid)), home.toLowerCase());
        throw new NullPointerException("A user with the uuid '"+uuid+"' doesn't exist or isn't online!");
    }

    public void teleportPlayerToHome(Player player, String home) {
        if (hasHome(player.getUniqueId().toString(), home.toLowerCase()))
            player.teleport(getLocationOfHome(player.getUniqueId().toString(), home.toLowerCase()));
        else throw new NullPointerException("The player with uuid "+ player.getUniqueId() +" doesn't have any homes called "+home.toLowerCase());
    }

    /**
     * Get the name of the homes set by the player
     * @param uuid UUID of the player
     * @return A list of strings with the name of the homes set by the user
     */
    @SneakyThrows
    public List<String> getHomes(String uuid) {
        if (!hasHomes(uuid))
            throw new NullPointerException("This user has set no homes!");

        List<String> ret = new ArrayList<>();

        Statement dbStat = getCon().createStatement();
        String sql = "SELECT home_name FROM " + Tables.PLAYER_HOMES + " WHERE uuid='"+uuid+"';";
        ResultSet dbRes = dbStat.executeQuery(sql);

        while (dbRes.next())
            ret.add(dbRes.getString("home_name"));

        return ret;
    }

    /**
     * Gets the location of a specific home for a specific user
     * @param uuid UUID of the player
     * @param home Name of the home of the player
     * @return The Location object of the home of the player
     */
    @SneakyThrows
    public Location getLocationOfHome(String uuid, String home) {
        if (!hasHome(uuid, home.toLowerCase()))
            throw new NullPointerException("The user with uuid '"+uuid+"' has no home called '"+home.toLowerCase()+"'.");

        Coordinates coords = getCoordinatesOfHome(uuid, home.toLowerCase());
        return new Location(getWorldOfHome(uuid, home.toLowerCase()), coords.x(), coords.y(), coords.z());
    }

    /**
     * Get the world a specific home for a specific user is located in
     * @param uuid UUID of the player
     * @param home Name of the home of the player
     * @return The world the home can be found in
     */
    @SneakyThrows
    public World getWorldOfHome(String uuid, String home) {
        if (!hasHome(uuid, home.toLowerCase()))
            throw new NullPointerException("The user with uuid '"+uuid+"' has no home called '"+home.toLowerCase()+"'.");

        Statement dbStat = getCon().createStatement();
        String sql = "SELECT world_name FROM " + Tables.PLAYER_HOMES + " WHERE uuid='"+uuid+"';";
        ResultSet dbRes = dbStat.executeQuery(sql);

        while (dbRes.next())
            return Bukkit.getWorld(dbRes.getString("world_name"));
        return null;
    }

    /**
     * Get the specific coordinates of a home of a user
     * @param uuid UUID of the player
     * @param home Name of the home
     * @return Coordinates object which contains the coordinates of the home
     */
    @SneakyThrows
    public Coordinates getCoordinatesOfHome(String uuid, String home) {
        if (!hasHome(uuid, home.toLowerCase()))
            throw new NullPointerException("The user with uuid '"+uuid+"' has no home called '"+home.toLowerCase()+"'.");

        Statement dbStat = getCon().createStatement();
        String sql = "SELECT * FROM " +  Tables.PLAYER_HOMES + " WHERE uuid='"+uuid+"' AND home_name='"+home.toLowerCase()+"';";
        ResultSet dbRes = dbStat.executeQuery(sql);

        while (dbRes.next())
            return new Coordinates(
                    dbRes.getInt("x_coordinate"),
                    dbRes.getInt("y_coordinate"),
                    dbRes.getInt("z_coordinate")
            );
        return null;
    }

    /**
     * Gets the number of homes a player has set
     * @param uuid The uuid of the player
     * @return The number of homes the player has.
     *          Returns 0 is the player isn't in the database.
     */
    @SneakyThrows
    public int getHomeCount(String uuid) {
        Statement dbStat = getCon().createStatement();
        String sql = "SELECT * FROM " + Tables.PLAYER_HOMES + " WHERE uuid='"+uuid+"'";
        ResultSet dbRes = dbStat.executeQuery(sql);

        int count = 0;
        while (dbRes.next())
            count++;
        return count;
    }

    /**
     * Checks if a specific player has a home.
     * @param uuid UUID of the player
     * @return True - Player has homes
     *         False - Player does not have any homes
     */
    @SneakyThrows
    public boolean hasHomes(String uuid) {
        return getHomeCount(uuid) > 0;
    }

    /**
     * Checks if a specific player has a home with a specific name
     * @param uuid UUID of the player
     * @param home Name of the home to check for (Case insensitive)
     * @return True - Player has a home with the passed name
     *         False - Player doesn't have a home with the passed name
     */
    public boolean hasHome(String uuid, String home) {
        if (!hasHomes(uuid)) return false;
        return getHomes(uuid).contains(home.toLowerCase());
    }

    /**
     * Closes the database connection
     */
    public void closeConnection() {
        super.closeConnection(Databases.HOMES);
    }
}
