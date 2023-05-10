package me.aragot.togara.commands;

import me.aragot.togara.Togara;
import me.aragot.togara.items.TogaraItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TogaraGiveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!(sender instanceof Player)) return false;
        TogaraItem item = Togara.itemHandler.getTogaraItemById(strings[0]);
        if(item == null){
            sender.sendMessage("item id is wrong");
            return false;
        }
        int itemAmount = 1;
        if(strings.length != 1 && strings.length != 2){
            sender.sendMessage("wrong usage");
            return false;
        } else if(strings.length == 2){
            itemAmount = Integer.parseInt(strings[1]);
        }

        item.give((Player) sender, itemAmount);


        return false;
    }
}
