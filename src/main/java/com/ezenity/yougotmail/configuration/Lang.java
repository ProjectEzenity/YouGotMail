package com.ezenity.yougotmail.configuration;

import com.ezenity.yougotmail.Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * YouGotMail Language Class
 *
 * Here you will find all the language options
 *
 * @author Ezenity
 * @version 0.0.2
 * @since 0.0.1
 */
public class Lang {
    /**
     * Initialize plugin instance. we use this to initialize the config file.
     */
    private final Main plugin;

    private Config config;
    /**
     * Language file. Gets the default set language file from the default config file.
     */
    private final String langFile;
    /**
     * Configuration File. Gets the default plugin folder and creates a new config file from the default provided config file with comments.
     */
    private final File configFile;// = new File(plugin.getDataFolder(), plugin.getConfig().getString("language-file"));
    /**
     * Config file configuration. This is used for creating the files settings general speaking.
     */
    private FileConfiguration fileConfiguration;

    public Lang(Main plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
        this.langFile = plugin.getConfig().getString("language-file");
        this.configFile = new File(plugin.getDataFolder(), langFile);

    }

    /**
     * This method is utilized for applying all the language settings. This allows us to grab any of these static variables and apply them
     * throughout the rest of the plugin.
     */
    private void init() {
        reload();

        fileConfiguration.getString("command.no-permission", "&4You do not have permission to use {getCommand}!");
        fileConfiguration.getString("command.disabled","&cThe {getDisabledCommand} &cis disabled.");
        fileConfiguration.getString("command.player","&4This command is only available to players!");
    }

    /**
     * Colorize a string.
     * <p>
     * This method is utilized for colorizing the text of a message.
     *
     * @param str String to colorize
     * @return Return a colorized string
     */
    public String colorize(String str) {
        if (str == null) {
            return "";
        }
        str = ChatColor.translateAlternateColorCodes('&', str);
        if (ChatColor.stripColor(str).isEmpty()) {
            return "";
        }
        return str;
    }

    /**
     * Colorize a string list.
     *
     * This method is utilized for colorizing the text of messages in a list.
     *
     * @param str string to colorize
     * @return a colorized string list
     */
    public List<String> colorizeListString(List<String> str) {
        final List<String> string = new ArrayList<>();
        if (str != null) {
            for (String s : str) {
                string.add(colorize(s));
            }
        }
        return string;
    }

    /**
     * This method will reload the language file and its contents. If there is no language
     * file, then a default language file will be generated and then will load the language
     * file. The comments that are set within the language file will also not be overwritten.
     */
    public void reload() {
        if (!configFile.exists()) {
            plugin.saveResource(langFile, false);
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
        /**
         * This is the header file for YouGotMail Language. This will be displayed as a comment above all
         * the settings regardless of what was inputted.
         */
        String HEADER = "Main language file for YouGotMail";
        fileConfiguration.options().header(HEADER);
        init();
    }

    /**
     * Send message.
     * <p>
     * This method will grab the inputted recipient and send the corresponding message accordingly. Message are also
     * colorized and can add new lines with "\n" inputted at end of line.
     *
     * @param recipient Recipient of message
     * @param message   Message to send
     */
    public void send(CommandSender recipient, String message) {
        if (recipient != null) {
            for (String part : colorize(message).split("\n")) {
                if (part != null && !part.isEmpty()) {
                    recipient.sendMessage(part);
                }
            }
        }
    }

    /**
     * Send List Message
     *
     * This will send a message to the recipient. This is an option to display your message in a list format within your config. Messages
     * will be outputted in color
     *
     * @param sender get recipient
     * @param message message to send
     * @param replacements get each list of strings for message
     */
    public void sendStringListMessage(CommandSender sender, List<String> message, HashMap<String, String> replacements) {
        for (String string : message) {
            if (replacements != null) {
                for (String replace : replacements.keySet()) {
                    string = string.replace(replace, replacements.get(replace));
                }
            }
            sender.sendMessage(colorize(string));
        }
    }

    /**
     * Broadcast message.
     * <p>
     * This will broadcast a message to every recipient to the server along with console. Messages are also colorized and can
     * add new lines with '\n' inputted at end of line.
     *
     * @param message message to broadcast
     */
    public void broadcast(String message) {
        for (String part : colorize(message).split("\n")) {
            Bukkit.getOnlinePlayers().forEach(recipient -> recipient.sendMessage(part));
            Bukkit.getConsoleSender().sendMessage(part);
        }
    }
}
