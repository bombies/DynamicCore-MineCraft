package dev.me.bombies.dynamiccore.commands.commands;

import org.bukkit.entity.Player;

public interface IDynamicCommand {

    public String getName();

    public String getDescription();

    public String getSyntax();

    public void perform(Player player, String[] args);

}
