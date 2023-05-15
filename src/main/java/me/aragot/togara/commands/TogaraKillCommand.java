package me.aragot.togara.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TogaraKillCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            Player player = ((Player) commandSender).getPlayer();

            for(Entity entity : player.getWorld().getEntities()){
                if(entity instanceof Mob){
                    ((Mob) entity).setHealth(0);
                }
            }
            return true;
        }

        return false;
    }
}
