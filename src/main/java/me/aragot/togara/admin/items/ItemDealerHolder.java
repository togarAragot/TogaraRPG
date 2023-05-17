package me.aragot.togara.admin.items;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class ItemDealerHolder implements InventoryHolder {

    private Inventory inventory;
    private int page;

    public ItemDealerHolder(Inventory inventory, int page){
        this.inventory = inventory;
        this.page = page;
    }
    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public int getPage(){
        return this.page;
    }
}
