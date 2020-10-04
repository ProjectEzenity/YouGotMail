package com.ezenity.yougotmail.conversations;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * General Prompt to ask the user what they would like to do
 *
 * @author Eznity
 * @version 0.0.4
 * @since 0.0.4
 */
public class GeneralPrompt extends FixedSetPrompt {

    public GeneralPrompt() {
        super("envTitle", "sealEnv");
    }

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String s) {
        if (s.equalsIgnoreCase("stop") || s.equalsIgnoreCase("end") || s.equalsIgnoreCase("quit") || s.equals("cancel") || s.equalsIgnoreCase("back")) {
            conversationContext.setSessionData("envTitle", null);
            conversationContext.setSessionData("sealEnv", null);

            return END_OF_CONVERSATION;
        } else if (s.equals("envTitle")) {
            return new EnvelopeTitlePrompt();
        } else if (s.equals("sealEnv")) {
            if (conversationContext.getSessionData("envTitle") == null) {
                conversationContext.getForWhom().sendRawMessage(ChatColor.DARK_RED + "Please set a envelope title first before sealing.");
                return new EnvelopeTitlePrompt();
            }
            return new EnvelopeSealedPrompt();
        }
        return END_OF_CONVERSATION;
    }

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext conversationContext) {
        conversationContext.getForWhom().sendRawMessage(ChatColor.AQUA + "Welcome, here you can select what you would like to do.");
        conversationContext.getForWhom().sendRawMessage(ChatColor.AQUA + "What do you need help with?");
        return ChatColor.GRAY + "EnvTitle, sealEnv";
    }
}
