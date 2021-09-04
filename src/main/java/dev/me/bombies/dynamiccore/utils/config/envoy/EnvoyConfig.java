package dev.me.bombies.dynamiccore.utils.config.envoy;

import dev.me.bombies.dynamiccore.constants.JSONConfigFile;
import dev.me.bombies.dynamiccore.utils.config.JSONConfig;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
        obj.put(EnvoyConfigField.INFO.toString(), envoyInfo);

        obj.put(EnvoyConfigField.POSITIONS.toString(), new JSONArray());

        setJSON(obj);
    }

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

    public void addReward(EnvoyConfigField envoy, String reward) {
        if (!isEnvoyType(envoy))
            throw new IllegalArgumentException("The field passed is invalid!");

        JSONObject obj = getJSONObject();
        JSONArray rewardsArr = obj.getJSONObject(EnvoyConfigField.REWARDS.toString())
                .getJSONArray(envoy.toString());

        rewardsArr.put(reward.toLowerCase());

        setJSON(obj);
    }

    public void removeReward(EnvoyConfigField envoy, String reward) {
        if (!isEnvoyType(envoy))
            throw new IllegalArgumentException("The field passed is invalid!");

        JSONObject obj = getJSONObject();
        JSONArray rewardsArr = obj.getJSONObject(EnvoyConfigField.REWARDS.toString())
                .getJSONArray(envoy.toString());

        if (!arrayHasObject(rewardsArr, reward.toLowerCase()))
            throw new NullPointerException("There was no reward \""+reward+"\" found for this envoy!");

        rewardsArr.remove(getIndexOfObjectInArray(rewardsArr, reward));
        setJSON(obj);
    }

    public List<String> getRewards(EnvoyConfigField envoy) {
        if (!isEnvoyType(envoy))
            throw new IllegalArgumentException("The field passed is invalid!");

        JSONObject obj = getJSONObject();
        JSONArray rewardsArr = obj.getJSONObject(EnvoyConfigField.REWARDS.toString())
                .getJSONArray(envoy.toString());

        List<String> rewards = new ArrayList<>();

        for (int i = 0; i < rewardsArr.length(); i++)
            rewards.add(rewardsArr.getString(i));

        return rewards;
    }

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
}
