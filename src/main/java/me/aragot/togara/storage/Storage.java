package me.aragot.togara.storage;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Storage {

    private UUID owner;
    private HashMap<String, Integer> items;
    private int maxAmount = 999;

    public Storage(UUID owner) {
        this.owner = owner;
        this.items = new HashMap<>();
    }
    public Storage(UUID owner, HashMap<String, Integer> items) {
        this.owner = owner;
        this.items = items;
    }


    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public int addItems(String itemId, int amount){
        int current = items.get(itemId) == null ? 0 : items.get(itemId);
        if(current >= maxAmount) return 0;
        else if(current + amount >= maxAmount){
            items.put(itemId, maxAmount);
            return maxAmount - current;
        }
        items.put(itemId, current + amount);
        return amount;
    }

    public int removeItems(String itemId, int amount){
        int current = items.get(itemId) == null ? 0 : items.get(itemId);
        if(current <= 0) return 0;
        else if (current < amount){
            items.put(itemId, 0);
            return current;
        }
        items.put(itemId, current - amount);
        return amount;
    }
}
