package de.votuu.emojis.command;

import de.votuu.emojis.Emojis;
import de.votuu.emojis.customchar.CharCategory;
import de.votuu.emojis.customchar.CustomChar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmojisCommand implements CommandExecutor, TabCompleter {

    protected final Emojis core;
    protected final Map<String, Function> functionMap;

    protected String label;
    protected String description;

    public EmojisCommand(Emojis core) {
        this.core = core;
        this.functionMap = new HashMap<>();

        this.label = "emojis";
        this.description = "Manage the emoji system";

        functionMap.put("usage", new Function() {
            @Override
            public void execute(CommandSender sender, String[] args) {
                usage(sender);
            }

            @Override
            public String description() {
                return "Shows all usages of command";
            }

            @Override
            public String suggest() {
                return "usage";
            }
        });
        functionMap.put("list", new ListFunction());
    }

    protected interface Function {
        void execute(CommandSender sender, String[] args);

        String description();

        default String suggest() {
            return "$none";
        }

        default boolean hasPermission(CommandSender sender) {
            return true;
        }
    }

    public void single(CommandSender sender) {

    }

    protected void usage(CommandSender sender) {
        sender.sendMessage(core.prefix().append(Component.text("Hilfe für ", NamedTextColor.GRAY))
                .append(Component.text("/" + label.toLowerCase(), core.theme()))
                .append(Component.text(":", NamedTextColor.DARK_GRAY)));
        sender.sendMessage(Component.text(" "));

        sender.sendMessage(core.prefix().append(Component.text("/" + label, core.theme()))
                .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
                .append(Component.text(description, NamedTextColor.GRAY)));

        for (String s : functionMap.keySet()) {
            sender.sendMessage(core.prefix().append(Component.text("/" + label + " " + s, core.theme()))
                    .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
                    .append(Component.text(functionMap.get(s).description(), NamedTextColor.GRAY)));
        }
    }

    private class ListFunction implements Function {

        @Override
        public void execute(CommandSender sender, String[] args) {
            sender.sendMessage(core.prefix().append(Component.text("List of ", NamedTextColor.GRAY))
                    .append(Component.text("Emojis", core.theme()))
                    .append(Component.text(":", NamedTextColor.DARK_GRAY)));
            sender.sendMessage(Component.text(" "));

            for(CustomChar c : CustomChar.customChars()) {
                if(c.category() == CharCategory.EMOTE) {
                    sender.sendMessage(core.prefix().append(Component.text(c.code(), core.theme()))
                            .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
                            .append(Component.text(":" + c.id() + ":", NamedTextColor.GRAY))
                            .append(Component.text(" (", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, true))
                            .append(Component.text(c.name(), NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, true))
                            .append(Component.text(")", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, true)));
                }
            }
        }

        @Override
        public String description() {
            return "Shows all emojis";
        }

        @Override
        public String suggest() {
            return "list";
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            single(sender);
            return false;
        }

        String search = args[0];
        if (functionMap.containsKey(search.toLowerCase())) {
            functionMap.get(search).execute(sender, args);
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 0) return list;

        if (args.length == 1) {
            for (String optional : functionMap.keySet()) {
                if (!functionMap.get(optional).suggest().equalsIgnoreCase("$none")) {
                    list.add(optional);
                }
            }
        }

        List<String> completer = new ArrayList<>();
        String current = args[args.length - 1].toLowerCase();
        for (String s : list) {
            if (s.startsWith(current)) {
                completer.add(s);
            }
        }
        return completer;
    }

    public String label() {
        return label;
    }

    public String description() {
        return description;
    }
}
