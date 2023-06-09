package me.aragot.togara.listeners;

import me.aragot.togara.Togara;

import me.aragot.togara.building.dimensions.FDimension;
import me.aragot.togara.entities.player.TogaraPlayer;
import me.aragot.togara.items.TogaraItem;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class WorldListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemDropEvent(ItemSpawnEvent e) {
        ItemMeta meta = e.getEntity().getItemStack().getItemMeta();

        if(meta.getPersistentDataContainer().get(new NamespacedKey(Togara.instance, "itemId"), PersistentDataType.STRING) == null) {
            TogaraItem item = Togara.itemHandler.getTogaraItemByMaterial(e.getEntity().getItemStack().getType());
            ItemStack stack = item.getTogaraItemStack();
            meta = item.getDefaultItemMeta();
            stack.setAmount(e.getEntity().getItemStack().getAmount());
            stack.setItemMeta(meta);
            e.getEntity().setItemStack(stack);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent e) {
        TogaraPlayer player = Togara.playerHandler.getTogaraPlayer(e.getPlayer());
        if(player == null) return;
        if(player.getF() == 0) return;

        FDimension.getLayer(player.getF()).breakBlock(e.getBlock().getLocation(), e);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onWorldUnloadEvent(WorldUnloadEvent e){
        Togara.entityHandler.unregister(e.getWorld());
        FDimension.save();
    }

}
