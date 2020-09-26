package com.ezenity.yougotmail;

import com.ezenity.yougotmail.command.CmdReload;
import com.ezenity.yougotmail.configuration.Config;
import com.ezenity.yougotmail.configuration.Lang;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * YouGotMail Main Plugin Class
 *
 * @author Ezenity
 * @version 0.0.2
 * @since 0.0.1
 */
public class Main extends JavaPlugin {
    private final Config config = new Config(this);
    private final Lang lang = new Lang(this, config);

    /**
     * This method is invoked when the plugin is enabled. When plugin is enabled, the config is reload,
     * lang is reload, events are registered, commands are registered and the plugin version is outputted.
     */
    @Override
    public void onEnable() {
        config.reload();
        lang.reload();

        getCommand("yougotmail").setExecutor(new CmdReload(this, config, lang));
    }

    /**
     * This method is invoked when the plugin is disabled. When the plugin is disabled, the plugin will output
     * to console that it has been disabled.
     */
    @Override
    public void onDisable() { }
}
