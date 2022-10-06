package de.votuu.emojis;

import de.votuu.emojis.command.EmojisCommand;
import de.votuu.emojis.config.Config;
import de.votuu.emojis.listeners.ChatListener;
import de.votuu.emojis.listeners.JoinListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

import java.io.FileNotFoundException;

public class Emojis extends JavaPlugin {

    private TextColor theme;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        this.theme = TextColor.color(0xffcb19);

        try {
            Config config = new Config(this);
            config.load();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);

        getCommand("emojis").setExecutor(new EmojisCommand(this));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public TextColor theme() {
        return theme;
    }
    public void theme(TextColor theme) {
        this.theme = theme;
    }

    public Component prefix() {
        return Component.text(" \u25cf ", NamedTextColor.DARK_GRAY)
                .append(Component.text("Emojis", theme))
                .append(Component.text(" | ", NamedTextColor.DARK_GRAY))
                .append(Component.text("", NamedTextColor.GRAY));
    }
}
