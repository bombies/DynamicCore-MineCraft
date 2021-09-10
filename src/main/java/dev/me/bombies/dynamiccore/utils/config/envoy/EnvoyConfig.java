package dev.me.bombies.dynamiccore.utils.config.envoy;

import com.google.common.util.concurrent.AtomicDouble;
import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.constants.JSONConfigFile;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.config.JSONConfig;
import dev.me.bombies.dynamiccore.utils.plugin.Coordinates;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class EnvoyConfig extends JSONConfig {

    @SneakyThrows
    public EnvoyConfig() {
        super(JSONConfigFile.ENVOY);
    }

    @Override
    protected void initConfig() {
        JSONObject obj = new JSONObject();

        JSONObject rewardsObj = new JSONObject();
        rewardsObj.put(EnvoyConfigField.SILVER.toString(), new JSONArray());
        rewardsObj.put(EnvoyConfigField.RUBY.toString(), new JSONArray());
        rewardsObj.put(EnvoyConfigField.GOLD.toString(), new JSONArray());
        rewardsObj.put(EnvoyConfigField.PLATINUM.toString(), new JSONArray());
        obj.put(EnvoyConfigField.REWARDS.toString(), rewardsObj);

        JSONObject envoyInfo = new JSONObject();

        JSONObject envoyChances = new JSONObject();
        envoyChances.put(EnvoyConfigField.SILVER.toString(), 0.25D);
        envoyChances.put(EnvoyConfigField.RUBY.toString(), 0.25D);
        envoyChances.put(EnvoyConfigField.GOLD.toString(), 0.25D);
        envoyChances.put(EnvoyConfigField.PLATINUM.toString(), 0.25D);
        envoyInfo.put(EnvoyConfigField.CHANCES.toString(), envoyChances);

        JSONObject envoyNames = new JSONObject();
        envoyNames.put(EnvoyConfigField.SILVER.toString(), "&7&lSilver Envoy Crate");
        envoyNames.put(EnvoyConfigField.RUBY.toString(), "&c&lRuby Envoy Crate");
        envoyNames.put(EnvoyConfigField.GOLD.toString(), "&6&lGold Envoy Crate");
        envoyNames.put(EnvoyConfigField.PLATINUM.toString(), "&b&lPlatinum Envoy Crate");
        envoyInfo.put(EnvoyConfigField.NAMES.toString(), envoyNames);

        envoyInfo.put(EnvoyConfigField.WORLD.toString(), "world");
        envoyInfo.put(EnvoyConfigField.TIMES.toString(), new JSONArray());
        envoyInfo.put(EnvoyConfigField.REMINDERS.toString(), new JSONArray());
        envoyInfo.put(EnvoyConfigField.MAX_ENVOYS.toString(), 40);
        obj.put(EnvoyConfigField.INFO.toString(), envoyInfo);

        obj.put(EnvoyConfigField.POSITIONS.toString(), new JSONArray());

        setJSON(obj);
    }

    /**
     * Set rewards for a specific envoy
     * @param envoy Envoy to set the rewards for
     * @param rewards List of commands that is to be run for the envoy
     */
    public void setRewards(EnvoyConfigField envoy, List<String> rewards) {
        if (!isEnvoyType(envoy))
            throw new IllegalArgumentException("The field passed is invalid!");

        JSONObject obj = getJSONObject();
        JSONArray rewardsArr = obj.getJSONObject(EnvoyConfigField.REWARDS.toString())
                .getJSONArray(envoy.toString());

        rewardsArr.clear();
        rewardsArr.put(rewards);

        setJSON(obj);
    }

    /**
     * Add a reward to a specific envoy
     * @param envoy Envoy to add the reward to
     * @param reward Command to be run as the reward
     */
    public void addReward(EnvoyConfigField envoy, String reward, double chance) {
        if (!isEnvoyType(envoy))
            throw new IllegalArgumentException("The field passed is invalid!");

        JSONObject obj = getJSONObject();
        JSONArray rewardsArr = obj.getJSONObject(EnvoyConfigField.REWARDS.toString())
                .getJSONArray(envoy.toString());

        JSONObject rewardObj = new JSONObject();
        rewardObj.put(EnvoyConfigField.COMMAND.toString(), reward.toLowerCase());
        rewardObj.put(EnvoyConfigField.CHANCE.toString(), chance);
        rewardsArr.put(rewardObj);

        setJSON(obj);
    }

    /**
     * Remove a specific reward from an envoy
     * @param envoy Envoy for the reward to be removed from
     * @param reward Specific command to remove
     */
    public void removeReward(EnvoyConfigField envoy, String reward) {
        if (!isEnvoyType(envoy))
            throw new IllegalArgumentException("The field passed is invalid!");

        JSONObject obj = getJSONObject();
        JSONArray rewardsArr = obj.getJSONObject(EnvoyConfigField.REWARDS.toString())
                .getJSONArray(envoy.toString());

        if (!arrayHasObject(rewardsArr, EnvoyConfigField.COMMAND, reward.toLowerCase()))
            throw new NullPointerException("There was no reward \""+reward+"\" found for this envoy!");

        rewardsArr.remove(getIndexOfObjectInArray(rewardsArr, EnvoyConfigField.COMMAND, reward));
        setJSON(obj);
    }

    /**
     * Get all rewards for a specific envoy
     * @param envoy Envoy to get the reward for
     * @return List of strings containing the commands to be run as rewards for the specific envoy
     */
    public HashMap<Double, String> getRewards(EnvoyConfigField envoy) {
        if (!isEnvoyType(envoy))
            throw new IllegalArgumentException("The field passed is invalid!");

        JSONObject obj = getJSONObject();
        JSONArray rewardsArr = obj.getJSONObject(EnvoyConfigField.REWARDS.toString())
                .getJSONArray(envoy.toString());

        HashMap<Double, String> rewards = new HashMap<>();

        for (int i = 0; i < rewardsArr.length(); i++) {
            JSONObject rewardObj = rewardsArr.getJSONObject(i);
            rewards.put(
                    rewardObj.getDouble(EnvoyConfigField.CHANCE.toString()),
                    rewardObj.getString(EnvoyConfigField.COMMAND.toString())
            );
        }

        return rewards;
    }

    /**
     * Get a random reward based off the chance provided in the JSON file.
     * @param envoyType Envoy type to get the rewards from
     * @return A reward selected by corresponding weighted chance
     */
    public String getRandomReward(String envoyType) {
        if (!isEnvoyType(envoyType))
            throw new IllegalArgumentException("The envoy type passed is invalid!");

        HashMap<Double, String> rewards = getRewards(EnvoyConfigField.parseEnvoyType(envoyType));

        if (rewards.isEmpty())
            throw new NullPointerException("There are no rewards!");

        NavigableMap<Double, String> navigableMap = new TreeMap<>();

        double selected = Math.random();

        AtomicDouble total = new AtomicDouble();
        rewards.forEach((k, v) -> {
            total.addAndGet(k);
            navigableMap.put(total.get(), v);
        });

        return navigableMap.higherEntry(selected * total.get())
                .getValue();
    }

    public String parseReward(Player player, String reward) {
        return reward.replace("{name}", player.getName()).replaceFirst("/", "");
    }

    /**
     * Get the spawn chance for a specific envoy
     * @param envoy Envoy to get the chance for
     * @return The chance for the envoy to be spawned as a decimal
     */
    public double getEnvoyChance(EnvoyConfigField envoy) {
        if (!isEnvoyType(envoy))
            throw new IllegalArgumentException("The field passed is invalid!");

        JSONObject obj = getJSONObject();
        JSONObject chanceObj = obj.getJSONObject(EnvoyConfigField.INFO.toString())
                .getJSONObject(EnvoyConfigField.CHANCES.toString());

        return chanceObj.getDouble(envoy.toString());
    }

    /**
     * Get all the chances for envoys to spawn in a sorted manner
     * @return Hashmap of the chances attached to the envoy
     */
    public HashMap<EnvoyConfigField, Double> getEnvoyChances() {
        HashMap<EnvoyConfigField, Double> ret = new HashMap<>();

        for (EnvoyConfigField envoy : EnvoyConfigField.getEnvoyTypes())
            ret.put(envoy, getEnvoyChance(envoy));

        ret = GeneralUtils.getSortedDoubleValueMap(ret);

        return ret;
    }

    /**
     * Get the colored name for the envoy
     * @param envoy Envoy to get the name for
     * @return Returns the display name for the envoy
     */
    public String getEnvoyName(EnvoyConfigField envoy) {
        if (!isEnvoyType(envoy))
            throw new IllegalArgumentException("The field passed is invalid!");

        JSONObject obj = getJSONObject();
        JSONObject nameObj = obj.getJSONObject(EnvoyConfigField.INFO.toString())
                .getJSONObject(EnvoyConfigField.NAMES.toString());

        return GeneralUtils.getColoredString(nameObj.getString(envoy.toString()));
    }

    /**
     * Get all the times envoys will spawn
     * @return A list of integers containing the hours at which envoys will spawn
     */
    public List<Integer> getTimes() {
        JSONObject obj = getJSONObject();
        JSONArray timesArr = obj.getJSONObject(EnvoyConfigField.INFO.toString())
                .getJSONArray(EnvoyConfigField.TIMES.toString());

        List<Integer> times = new ArrayList<>();

        for (int i = 0; i < timesArr.length(); i++)
            times.add(timesArr.getInt(i));

        return times;
    }

    /**
     * Get all the times before an envoy event in which all users will be reminded.
     * Each value represents the reminder times in minutes before the actual event
     * @return A list of integers containing the times in minutes to announce the envoy event.
     */
    public List<Integer> getReminderTimes() {
        JSONObject obj = getJSONObject();
        JSONArray remindersArr = obj.getJSONObject(EnvoyConfigField.INFO.toString())
                .getJSONArray(EnvoyConfigField.REMINDERS.toString());

        List<Integer> reminders = new ArrayList<>();

        for (int i = 0; i < remindersArr.length(); i++)
            reminders.add(remindersArr.getInt(i));

        return reminders;
    }

    /**
     * Gets the name of the world envoys will spawn in
     * @return The name of the world
     */
    public String getWorldString() {
        return getJSONObject().getJSONObject(EnvoyConfigField.INFO.toString())
                .getString(EnvoyConfigField.WORLD.toString());
    }

    public World getWorld() {
        return Bukkit.getWorld(getWorldString());
    }


    /**
     * Get the max amount of envoys that will spawn
     * @return Integer max envoys
     */
    public int getMaxEnvoys() {
        return getJSONObject().getJSONObject(EnvoyConfigField.INFO.toString())
                .getInt(EnvoyConfigField.MAX_ENVOYS.toString());
    }

    /**
     * Add an envoy spawn location
     * @param location Current location of the player running the command
     * @return ID of the location
     */
    public int addPosition(Location location) {
        JSONObject obj = getJSONObject();
        JSONArray posArr = obj.getJSONArray(EnvoyConfigField.POSITIONS.toString());

        JSONObject posObj = new JSONObject();
        posObj.put(EnvoyConfigField.ID.toString(), posArr.length()+1);
        posObj.put(EnvoyConfigField.X_POS.toString(), location.getBlockX());
        posObj.put(EnvoyConfigField.Y_POS.toString(), location.getBlockY());
        posObj.put(EnvoyConfigField.Z_POS.toString(), location.getBlockZ());
        posArr.put(posObj);

        setJSON(obj);

        return posArr.length();
    }

    /**
     * Remove a specific position from the JSON file
     * @param id ID of the location
     * @throws IllegalArgumentException Thrown when the ID passed is invalid
     */
    public void removePosition(int id) {
        JSONObject obj = getJSONObject();
        JSONArray posArr = obj.getJSONArray(EnvoyConfigField.POSITIONS.toString());

        if (id < 0 || id > posArr.length())
            throw new IllegalArgumentException("The ID passed is invalid!");

        posArr.remove(getIndexOfObjectInArray(posArr, EnvoyConfigField.ID, id));

        if (posArr.length() > 0)
            for (int i = 0; i < posArr.length(); i++) {
                int posID = posArr.getJSONObject(i).getInt(EnvoyConfigField.ID.toString());

                if (posID > id)
                    posArr.getJSONObject(i).put(EnvoyConfigField.ID.toString(), posID-1);
            }

        setJSON(obj);
    }

    /**
     * Get the positions with their IDs attached as keys.
     * @return A hashmap containing the positions and their IDs
     */
    public HashMap<Integer, Coordinates> getHashedPositions() {
        JSONObject obj = getJSONObject();
        JSONArray posArr = obj.getJSONArray(EnvoyConfigField.POSITIONS.toString());

        HashMap<Integer, Coordinates> positions = new HashMap<>();

        for (int i = 0; i < posArr.length(); i++) {
            JSONObject currentObj = posArr.getJSONObject(i);
            positions.put(
                    currentObj.getInt(EnvoyConfigField.ID.toString()),
                    new Coordinates(
                            currentObj.getInt(EnvoyConfigField.X_POS.toString()),
                            currentObj.getInt(EnvoyConfigField.Y_POS.toString()),
                            currentObj.getInt(EnvoyConfigField.Z_POS.toString())
                    )
            );
        }

        return GeneralUtils.getSortedIntMap(positions);
    }

    /**
     * Get all positions without their IDs
     * @return A list of all positions
     */
    public List<Coordinates> getPositions() {
        JSONObject obj = getJSONObject();
        JSONArray posArr = obj.getJSONArray(EnvoyConfigField.POSITIONS.toString());

        List<Coordinates> coordinates = new ArrayList<>();

        for (int i = 0; i < posArr.length(); i++)
            coordinates.add(new Coordinates(
                    posArr.getJSONObject(i).getInt(EnvoyConfigField.X_POS.toString()),
                    posArr.getJSONObject(i).getInt(EnvoyConfigField.Y_POS.toString()),
                    posArr.getJSONObject(i).getInt(EnvoyConfigField.Z_POS.toString())
            ));

        return coordinates;
    }

    /**
     * Get a list of randomly selected positions.
     * This list of positions has a maximum size of the
     * maximum amount of envoys that can be spawned.
     * @return
     */
    public List<Coordinates> getRandomPositions() {
        List<Coordinates> allPositions = getPositions();

        int maxEnvoys = getMaxEnvoys();

        if (allPositions.size() <= maxEnvoys)
            return allPositions;

        List<Coordinates> randomPositions = new ArrayList<>();
        int[] indexes = GeneralUtils.getUniqueRandomIntegers(allPositions.size(), maxEnvoys);

        for (int i = 0; i < maxEnvoys; i++)
            randomPositions.add(allPositions.get(indexes[i]));

        return randomPositions;
    }

    /**
     * Get a randomly selected envoy type based on the chances set in the JSON file
     * @return String of the randomly selected type
     */
    public String getRandomType() {
        double selected = Math.random();

        String ret;

        NavigableMap<Double, EnvoyConfigField> navigableChancesMap = new TreeMap<>();
        HashMap<EnvoyConfigField, Double> chances = getEnvoyChances();

        AtomicDouble total = new AtomicDouble();
        chances.forEach((k,v) -> {
            total.addAndGet(v);
            navigableChancesMap.put(total.get(), k);
        });

        ret = navigableChancesMap.higherEntry(selected * total.get()).getValue().toString();

        return ret;
    }

    /**
     * Checks if the EnvoyConfigField passed is an envoy type
     * @param envoy Envoy field to be checked
     * @return Returns true if the field is an envoy type, else false will be returned.
     */
    private boolean isEnvoyType(EnvoyConfigField envoy) {
        switch (envoy) {
            case SILVER, RUBY, GOLD, PLATINUM -> {
               return true;
            }
            default -> {
                return false;
            }
        }
    }

    private boolean isEnvoyType(String envoy) {
        return envoy.equalsIgnoreCase(EnvoyConfigField.SILVER.toString())
        || envoy.equalsIgnoreCase(EnvoyConfigField.RUBY.toString())
        || envoy.equalsIgnoreCase(EnvoyConfigField.GOLD.toString())
        || envoy.equalsIgnoreCase(EnvoyConfigField.PLATINUM.toString());
    }
}
