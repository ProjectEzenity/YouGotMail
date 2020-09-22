package com.ezenity.yougotmail.configuration;

import com.ezenity.yougotmail.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * YouGotMail Main Config Class
 * <p>
 * Here you will find all the default configuration options that are available with YouGotMail
 *
 * @author Ezenity
 * @version 0.0.2
 * @since 0.0.1
 */
public class Config {

    /**
     * Plugin variable.
     */
    private final Main plugin;

    /**
     * Configuration File. Gets the default plugin folder and creates a new config file from the default provided config file with comments.
     */
    private final File configFile = new File(getPlugin().getDataFolder(), "config.yml");

    /**
     * Config file configuration. This is used for creating the files settings general speaking.
     */
    private FileConfiguration config;

    /**
     * Initialize plugin instance. We use this to initialize the config file.
     */
    public Config(Main getPlugin) {
        this.plugin = getPlugin;
    }

    /**
     * Get plugin. This getter will return the plugin instance.
     *
     * @return plugin instance
     */
    public Main getPlugin() {
        return plugin;
    }

    /**
     * This method is utilized from applying all the configuration settings. This allows us to grab any of these static variables and apply them
     * throughout the rest of the plugin.
     */
    private void init() {
        reload();

        config.getBoolean("debug-mode", true);
        config.getBoolean("color-logs", true);
        config.getBoolean("logging.info", true);
        config.getBoolean("logging.warn", true);
        config.getBoolean("logging.error", true);
        config.getString("language-file", "lang-en.yml");
    }

    /**
     * This method will reload the configuration file(s) and its contents. If there is no configuration,
     * then the default configuration file will be generated and then will load the configuration file.
     * Another thing to note is that this will not overwrite the comments that are set inside a config file.
     */
    public void reload() {
        if (!configFile.exists())
            plugin.saveResource("config.yml", false);

        config = YamlConfiguration.loadConfiguration(configFile);

        String HEADER = "The main configuration file for YouGotMail";

        config.options().header(HEADER);
        init();
    }
}
