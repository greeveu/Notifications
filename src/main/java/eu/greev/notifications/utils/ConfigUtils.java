package eu.greev.notifications.utils;

import eu.greev.notifications.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigUtils {

    private final Main main;

    public ConfigUtils(Main main) {
        this.main = main;
    }

    /**
     * Get custom config and save default if not already exists
     *
     * @param configName Name of custom config (with suffix)
     * @return Configuration
     */
    public Configuration getCustomConfig(String configName) throws IOException {
        saveCustomConfigIfNotExist(configName);
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(main.getDataFolder(), configName));
    }

    /**
     * Save default config if not exist
     *
     * @param configName Name of custom config (with suffix)
     */
    public void saveCustomConfigIfNotExist(String configName) {
        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }

        File file = new File(main.getDataFolder(), configName);

        if (!file.exists()) {
            try (InputStream in = main.getResourceAsStream(configName)) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}