package me.aragot.togara.storage;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import me.aragot.togara.Togara;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class StoragePacketHandler {

    public static void onUpdateSignEvent(PacketEvent event){
        Player player = event.getPlayer();
        Storage storage = Togara.storageManager.getStorage(player.getUniqueId());
        PacketContainer packet = event.getPacket();
        final String[] lines = event.getPacket().getStringArrays().getValues().get(0);

        if(!lines[3].equalsIgnoreCase("to Search for")) return;
        BlockPosition pos = event.getPacket().getBlockPositionModifier().getValues().get(0);
        Location loc = new Location(player.getWorld(), pos.getX(), pos.getY(), pos.getZ());
        storage.setSearch(lines[0]);
        player.sendBlockChange(loc, loc.getBlock().getBlockData());
        new BukkitRunnable() {
            @Override
            public void run() {
                StorageGui.openStorageGui(player, storage, 1);
            }
        }.runTaskLater(Togara.instance, 1);
    }

    public static void openSign(Player player, Location loc){
        BlockPosition pos = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

        ArrayList<Component> signText = new ArrayList<>();
        signText.add(Component.text(""));
        signText.add(Component.text("^^^^^^^^^^"));
        signText.add(Component.text("Enter a Term"));
        signText.add(Component.text("to Search for"));

        player.sendSignChange(loc, signText);

        PacketContainer openSign = new PacketContainer(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        openSign.getBlockPositionModifier().write(0, pos);
        Togara.protocolManager.sendServerPacket(player, openSign);
    }

}
