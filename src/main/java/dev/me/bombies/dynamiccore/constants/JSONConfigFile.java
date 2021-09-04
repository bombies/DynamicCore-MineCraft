package dev.me.bombies.dynamiccore.constants;

import dev.me.bombies.dynamiccore.utils.database.DatabaseUtils;
import lombok.SneakyThrows;

public enum JSONConfigFile {
    ENVOY(getDir() + "/envoy.json");

    private final String str;

    JSONConfigFile(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

    @SneakyThrows
    private static String getDir() {
        String fileName = DatabaseUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile().substring(
                DatabaseUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile().lastIndexOf("/")+1
        );
        String uri = DatabaseUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI()
                + Databases.DIRECTORY.toString();
        String path = uri.substring(6).replace(fileName + "/", "").replace("%20", " ");
        return path;
    }
}
