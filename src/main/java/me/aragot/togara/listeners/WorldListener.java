package me.aragot.togara.listeners;

import me.aragot.togara.Togara;

import me.aragot.togara.items.TogaraItem;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
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

}
