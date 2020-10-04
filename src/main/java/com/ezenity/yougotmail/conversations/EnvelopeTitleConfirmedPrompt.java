package com.ezenity.yougotmail.conversations;

import static org.bukkit.Bukkit.getLogger;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnvelopeTitleConfirmedPrompt extends ValidatingPrompt {
    @Override
    protected boolean isInputValid(@NotNull ConversationContext conversationContext, @NotNull String s) {
        return true;
    }

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String s) {
        getLogger().info("hit accept validated input for EnvelopeSealedPrompt");// using strictly for testing purposes

        if(s.equalsIgnoreCase("stop") || s.equalsIgnoreCase("end") || s.equalsIgnoreCase("quit") || s.equals("cancel") || s.equalsIgnoreCase("back")) {
            conversationContext.setSessionData("envTitle", null);
            conversationContext.setSessionData("sealEnv", null);

            return s.equalsIgnoreCase("back") ? new EnvelopeTitlePrompt() : END_OF_CONVERSATION;
        } else if (conversationContext.getSessionData("envTitle") == null ) {
            if (s.equalsIgnoreCase("yes")) {
                conversationContext.setSessionData("envTitle", s); // save title in session storage
            } else {
                conversationContext.setSessionData("envTitle", null);
                conversationContext.setSessionData("sealEnv", null);

                return END_OF_CONVERSATION;
            }
        }

        return this;
    }

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext conversationContext) {
        conversationContext.getForWhom().sendRawMessage(ChatColor.AQUA + "Would you like to confirm your envelope title?");
        return ChatColor.GREEN + "Yes, No";
    }
}
