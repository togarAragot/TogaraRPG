package me.aragot.togara.listeners;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import me.aragot.togara.Togara;
import me.aragot.togara.entities.DamageType;
import me.aragot.togara.entities.TogaraEntity;
import me.aragot.togara.entities.player.TogaraPlayer;
import me.aragot.togara.items.TogaraArmor;
import me.aragot.togara.items.TogaraItem;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;
import java.util.function.Predicate;

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


    @EventHandler
    public void onEquipmentChange(InventoryClickEvent e){

        if(e.getSlotType() == InventoryType.SlotType.ARMOR){
            TogaraArmor equipped = (TogaraArmor) Togara.itemHandler.getTogaraItemFromStack(e.getCurrentItem());
            TogaraArmor unequipped = (TogaraArmor) Togara.itemHandler.getTogaraItemFromStack(e.getCursor());
            if(equipped != null) equipped.equip((Player) e.getWhoClicked());
            if(unequipped != null) unequipped.unequip((Player) e.getWhoClicked());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e){
        Togara.entityHandler.register(e.getPlayer().getWorld());
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
            //Red Enemies = no damage
            Togara.entityHandler.attackEntity(e.getPlayer(), (Mob) e.getAttacked());
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent e){
        hasRecentlyLeftClicked.add(e.getPlayer());
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR)) return;
        TogaraPlayer p = Togara.playerHandler.getTogaraPlayer(e.getPlayer());
        if(p.getTotalStats().getSwingRange() == 3) return;

        Entity entity = getEntityInSight(e.getPlayer(), p.getTotalStats().getSwingRange());
        if(entity == null) return;
        TogaraEntity togaraEntity = Togara.entityHandler.getTogaraEntityByEntity(entity);
        if(togaraEntity == null) return;

        Togara.entityHandler.damageQueue.put(togaraEntity, DamageType.PHYSICAL);
        Togara.entityHandler.attackEntity(e.getPlayer(), (Mob) entity);
    }

    private Entity getEntityInSight(Player p, double range) {

        Predicate<Entity> isMob = x -> (x instanceof Mob);
        World world = p.getWorld();
        RayTraceResult result = world.rayTraceEntities(p.getEyeLocation(), p.getEyeLocation().getDirection(), range, isMob);
        if(result == null || result.getHitEntity() == null) return null;

        return result.getHitEntity();
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

    private static final ArrayList<Player> hasRecentlyLeftClicked = new ArrayList<>();
    private static final ArrayList<Player> hasRecentlyAttacked = new ArrayList<>();
    private static final ArrayList<Player> hasRecentlyDropped = new ArrayList<>();

    public static void clearLists(){
        hasRecentlyLeftClicked.clear();
        hasRecentlyAttacked.clear();
        hasRecentlyDropped.clear();
    }
}
