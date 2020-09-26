package com.ezenity.yougotmail.command;

import com.ezenity.yougotmail.Main;
import com.ezenity.yougotmail.configuration.Config;
import com.ezenity.yougotmail.configuration.Lang;
import java.util.logging.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

/**
 * YouGotMail Reload Class
 *
 * This will allow you to reload the follow classes:
 * <ul>
 *     <li>Config</li>
 *     <li>Lang</li>
 * </ul>
 *
 * @author Ezenity
 * @version 0.0.2
 * @since  0.0.1
 */
public class CmdReload implements TabExecutor {
    /**
     * Plugin instance. Initialize the plugin instance.
     */
    private final Main plugin;
    /**
     * Config variable. This variable is used for reloading the configuration file.
     */
    private final Config config;
    /**
     * Lang variable. this variable is used for reload the language file and obtaining its contents.
     */
    private final Lang lang;

    /**
     * Constructor. Initializing the plugin object.
     *
     * @param plugin load instance of plugin
     * @param config load instance of config
     */
    public CmdReload(Main plugin, Config config, Lang lang) {
        this.plugin = plugin;
        this.config = config;
        this.lang = lang;
    }

    /**
     * Tab Autocompletion
     * <p>
     * Will auto populate the reload option in the text box to allow the user to tab in the remainder of the option "reload".
     *
     * @param sender get sender
     * @param command get command object info
     * @param label get typed input from sender
     * @param args get arguments after inputted command
     * @return command options
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if ( args.length == 1 && "reload".startsWith(args[0].toLowerCase()) && sender.hasPermission("command.yougotmail.reload") ) {
            return Collections.singletonList("reload");
        }

        return Collections.emptyList();
    }

    /**
     * Reload command.
     * <p>
     * This method will reload the Config and Lang yml files. If the sender does not have permission an error message will output and
     * notify that sender
     *
     * @param sender get sender
     * @param command get command object info
     * @param label get typed input from sender
     * @param args get arguments after inputted command
     * @return true if the yml files are load, if not return error message to sender.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("command.yougotmail.reload")) {
            sender.sendMessage("No permission to command");
            plugin.getLogger().log(Level.WARNING, sender.getName() + " tried to reload " + plugin.getName() + " v" + plugin.getDescription().getVersion() + " but had no permissions to.");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            config.reload();
            lang.reload();
            plugin.getLogger().log(Level.INFO, plugin.getName() + " v" + plugin.getDescription().getVersion() + " was reloaded by " + sender.getName());
        }

        lang.send(sender, plugin.getName() + " v" + plugin.getDescription().getVersion() + " reloaded");

        return true;
    }
}
