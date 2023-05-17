package me.aragot.togara.commands;

import me.aragot.togara.admin.items.ItemDealerGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DealerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;

        ItemDealerGui.addInfo(new ItemDealerGui.Info((Player) sender));
        ItemDealerGui.openItemDealer(((Player) sender).getPlayer(), 1);

        return true;
    }
}
