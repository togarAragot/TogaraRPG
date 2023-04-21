package me.aragot.togara.commands;

import me.aragot.togara.Togara;
import me.aragot.togara.storage.StorageGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StorageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if(Togara.storageManager.getStorage(player.getUniqueId()) == null){
                Togara.storageManager.createStorage(player.getUniqueId());
            }
            if(args.length > 0){
                StorageGui.openStorageGui(player, Togara.storageManager.getStorage(player.getUniqueId()), Integer.parseInt(args[0]));
            } else {
                StorageGui.openStorageGui(player, Togara.storageManager.getStorage(player.getUniqueId()), 1);
            }
        }

        return false;
    }
}
