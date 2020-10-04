package com.ezenity.yougotmail.conversations;

import java.util.regex.Pattern;
import static org.bukkit.Bukkit.getLogger;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Simple Prompt. Use static logger for simplicity and quick MVP
 *
 * @author Ezenity
 * @version 0.0.4
 * @since 0.0.4
 */
public class EnvelopeTitlePrompt extends ValidatingPrompt {
    @Override // Called to validate the input (For testing not validating and applied true)
    protected boolean isInputValid(@NotNull ConversationContext conversationContext, @NotNull String s) {
        getLogger().info("Input valid for convo EnvelopeTitlePrompt");// using strictly for testing purposes

        if (s.equalsIgnoreCase("stop") || s.equalsIgnoreCase("end") || s.equalsIgnoreCase("quit") || s.equals("cancel"))
            return true;

        if (conversationContext.getSessionData("envTitle") == null) {
            if (s.length() == 0)
                return false;
            if (Pattern.matches("[a-zA-Z]", s)) {
                return true;
            } else {
                conversationContext.getForWhom().sendRawMessage(ChatColor.DARK_RED + "Please make sure your title is " + ChatColor.AQUA +" letters" + ChatColor.DARK_RED + " only.");
                return false;
            }
        }

        return true;
    }

    @Override // Is called when user input is deemed valid and will return the next prompt to display to user
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String s) {
        getLogger().info("hit accept validated input for EnvelopeTitlePrompt");// using strictly for testing purposes

        if(s.equalsIgnoreCase("stop") || s.equalsIgnoreCase("end") || s.equalsIgnoreCase("quit") || s.equals("cancel") || s.equalsIgnoreCase("back")) {
            conversationContext.setSessionData("envTitle", null);
            conversationContext.setSessionData("sealEnv", null);

            return s.equalsIgnoreCase("back") ? new GeneralPrompt() : END_OF_CONVERSATION;
        } else if (conversationContext.getSessionData("envTitle") == null ) {
            conversationContext.setSessionData("envTitle", s); // save title in session storage
        }

        return new EnvelopeSealedPrompt();
    }

    @Override  // Called when the prompt wil display text to the one in convo (Text to user)
    public @NotNull String getPromptText(@NotNull ConversationContext conversationContext) {
        getLogger().info("get prompt text for convo EnvelopeTitlePrompt."); // using strictly for testing purposes

        if (conversationContext.getSessionData("envTitle") == null) {
            return ChatColor.BLUE + "Hello, Please type in the title of your Envelope.";
        } else {
            return ChatColor.BLUE + "Your envelope has a title already.";
        }
    }
}
