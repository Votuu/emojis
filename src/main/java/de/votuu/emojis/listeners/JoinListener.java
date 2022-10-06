package de.votuu.emojis.listeners;

import de.votuu.emojis.Emojis;
import de.votuu.emojis.customchar.CharCategory;
import de.votuu.emojis.customchar.CustomChar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public record JoinListener(Emojis core) implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for(CustomChar c : CustomChar.customChars()) {
            if (c.category() == CharCategory.EMOTE) {
                player.addAdditionalChatCompletions(List.of(":" + c.id() + ":"));
            }
        }
    }
}
