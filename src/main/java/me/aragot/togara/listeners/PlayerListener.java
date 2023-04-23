package me.aragot.togara.listeners;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import me.aragot.togara.Togara;
import me.aragot.togara.items.TogaraItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInventorySlotChange(PlayerInventorySlotChangeEvent e){
        if(e.getNewItemStack().getType().equals(Material.AIR)) return;
        ItemMeta meta = e.getNewItemStack().getItemMeta();
        if(meta.getPersistentDataContainer().get(new NamespacedKey(Togara.instance, "itemId"), PersistentDataType.STRING) == null){
            TogaraItem item = Togara.itemHandler.getTogaraItemByMaterial(e.getNewItemStack().getType());
            if(item == null) return;
            e.getPlayer().getOpenInventory().getInventory(e.getRawSlot()).getItem(e.getSlot()).setItemMeta(item.getItemMeta());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event){
        Togara.playerHandler.addPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLeave(PlayerQuitEvent event){
        Togara.playerHandler.removePlayer(event.getPlayer());
    }

}
