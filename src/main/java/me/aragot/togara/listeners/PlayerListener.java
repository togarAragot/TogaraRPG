package me.aragot.togara.listeners;

import io.papermc.paper.event.player.PlayerArmSwingEvent;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import me.aragot.togara.Togara;
import me.aragot.togara.entities.DamageType;
import me.aragot.togara.entities.TogaraEntity;
import me.aragot.togara.entities.player.TogaraPlayer;
import me.aragot.togara.items.TogaraItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

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

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemDrop(PlayerDropItemEvent e){
        hasRecentlyDropped.add(e.getPlayer());
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
        hasRecentlyAttacked.add(e.getPlayer());
        if(e.getAttacked() instanceof Mob){
            Togara.entityHandler.damageQueue.put(Togara.entityHandler.getTogaraEntityByEntity(e.getAttacked()), DamageType.PHYSICAL);
            Togara.entityHandler.attackEntity(e.getPlayer(), (Mob) e.getAttacked());
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent e){
        hasRecentlyLeftClicked.add(e.getPlayer());
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR)) return;
        TogaraPlayer p = Togara.playerHandler.getTogaraPlayer(e.getPlayer());
        if(p.getTotalStats().getSwingRange() == 0) return;

        Entity entity = getEntityInSight(e.getPlayer(), 3 + p.getTotalStats().getSwingRange());
        if(entity == null) return;
        TogaraEntity togaraEntity = Togara.entityHandler.getTogaraEntityByEntity(entity);
        if(togaraEntity == null) return;

        Togara.entityHandler.damageQueue.put(togaraEntity, DamageType.PHYSICAL);
        togaraEntity.damage(p);
    }

    private Entity getEntityInSight(Player p, double range) {

        Vector playerLookDir = p.getEyeLocation().getDirection();

        Vector playerEyeLocation = p.getEyeLocation().toVector();

        Entity bestEntity = null;

        float bestAngle = 0.25f;


        for (Entity e : p.getNearbyEntities(range, range, range)) {

            if (!p.hasLineOfSight(e)) continue;
            if(!(e instanceof Mob)) continue;

            Vector entityLoc = e.getLocation().toVector();
            Vector entityMid = entityLoc.setY(entityLoc.getY() + (e.getHeight() / 2));

            Vector playerToEntity = entityLoc.subtract(playerEyeLocation);
            float currentAngle = playerLookDir.angle(entityMid);
            if (currentAngle < bestAngle) {

                bestAngle = playerLookDir.angle(playerToEntity);

                bestEntity = e;

            }

        }
        return bestEntity;
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerAnimation(PlayerAnimationEvent e){
        if (e.getAnimationType() == PlayerAnimationType.ARM_SWING){
            // Means the client actually has send the interaction with AIR packet to the server, so the server already
            // triggered the PlayerInteractEvent on its own and this fix is not needed. This also triggers when block are placed
            if(hasRecentlyLeftClicked.contains(e.getPlayer())) return;
            // Server already handled an attack packet, no need to apply this fix.
            if(hasRecentlyAttacked.contains(e.getPlayer())) return;
            // The arm animation was triggered by an item drop event, skip.
            if(hasRecentlyDropped.contains(e.getPlayer())) return;

            Bukkit.getServer().getPluginManager().callEvent(new PlayerInteractEvent(
                    e.getPlayer(),
                    Action.LEFT_CLICK_AIR,
                    e.getPlayer().getActiveItem(),
                    null,
                    BlockFace.SELF
            ));
        }
    }

    private static ArrayList<Player> hasRecentlyLeftClicked = new ArrayList<>();
    private static ArrayList<Player> hasRecentlyAttacked = new ArrayList<>();
    private static ArrayList<Player> hasRecentlyDropped = new ArrayList<>();

    public static void clearLists(){
        hasRecentlyLeftClicked.clear();
        hasRecentlyAttacked.clear();
        hasRecentlyDropped.clear();
    }
}
