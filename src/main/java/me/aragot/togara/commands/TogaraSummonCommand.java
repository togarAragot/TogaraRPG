package me.aragot.togara.commands;

import me.aragot.togara.Togara;
import me.aragot.togara.entities.TogaraEntity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TogaraSummonCommand implements CommandExecutor{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(args.length != 1) return false;
        if(!(sender instanceof Player)) return false;

        TogaraEntity entity = Togara.entityHandler.getEntityInstance(args[0]);

        entity.spawn(((Player) sender).getLocation());
        sender.sendMessage("Spawned " + entity.getEntityName());

        return true;
    }
}
