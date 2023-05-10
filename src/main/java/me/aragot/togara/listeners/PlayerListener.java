package me.aragot.togara.listeners;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import me.aragot.togara.Togara;
import me.aragot.togara.entities.DamageType;
import me.aragot.togara.items.TogaraItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
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
            e.getPlayer().getOpenInventory().getInventory(e.getRawSlot()).getItem(e.getSlot()).setItemMeta(item.getDefaultItemMeta());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onHealthRegen(EntityRegainHealthEvent e){
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onFoodLevelChange(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e){
        Togara.playerHandler.addPlayer(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLeave(PlayerQuitEvent e){
        Togara.playerHandler.removePlayer(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerHitEvent(PrePlayerAttackEntityEvent e){
        if(e.getAttacked() instanceof Mob){
            Togara.entityHandler.damageQueue.put(Togara.entityHandler.getTogaraEntityByEntity(e.getAttacked()), DamageType.PHYSICAL);
            Togara.entityHandler.attackEntity(e.getPlayer(), (Mob) e.getAttacked());
            e.setCancelled(true);
        }

    }

}
