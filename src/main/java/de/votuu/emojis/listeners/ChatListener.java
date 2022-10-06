package de.votuu.emojis.listeners;

import de.votuu.emojis.Emojis;
import de.votuu.emojis.customchar.CharCategory;
import de.votuu.emojis.customchar.CustomChar;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public record ChatListener(Emojis core) implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event) {
        String legacyMessage = LegacyComponentSerializer.legacyAmpersand().serialize(event.message());
        legacyMessage = CustomChar.format(legacyMessage, CharCategory.EMOTE);
        event.message(Component.text(legacyMessage));
    }
}
