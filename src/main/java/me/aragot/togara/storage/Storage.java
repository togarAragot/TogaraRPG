package me.aragot.togara.storage;

import me.aragot.togara.items.Rarity;
import me.aragot.togara.items.ItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Storage {

    private UUID owner;
    private HashMap<String, Integer> items;
    private Rarity filter;
    private ItemType itemType;
    private String search = "";
    private int maxAmount = 999;

    public Storage(UUID owner) {
        this.owner = owner;
        this.items = new HashMap<>();
        this.filter = Rarity.ALL;
        this.itemType = ItemType.ALL;
    }
    public Storage(UUID owner, HashMap<String, Integer> items) {
        this.owner = owner;
        this.items = items;
        this.filter = Rarity.ALL;
        this.itemType = ItemType.ALL;
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

    public Rarity getFilter() {
        return filter;
    }

    public void setFilter(Rarity filter) {
        this.filter = filter;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public int getSize(){
        int size = 0;
        Iterator<Map.Entry<String,Integer>> iter = items.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,Integer> entry = iter.next();
            if(entry.getValue() == 0){
                iter.remove();
                continue;
            }
            size++;
        }
        return size;
    }

    public int getAmount(String itemId){
        if(items.get(itemId) != null) return items.get(itemId);
        else return 0;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search){
        this.search = search;
    }

    public void nextFilter(){
        if(this.filter.getIndex() < Rarity.size - 1){
            this.filter = Rarity.values()[filter.getIndex() + 1];
        } else this.filter = Rarity.ALL;
    }

    public void nextItemType(){
        if(this.itemType.getIndex() < ItemType.size - 1){
            this.itemType = ItemType.values()[itemType.getIndex() + 1];
        } else this.itemType = ItemType.ALL;
    }

    public void lastFilter(){
        if(this.filter.getIndex() > 0){
            this.filter = Rarity.values()[filter.getIndex() - 1];
        } else this.filter = Rarity.values()[Rarity.values().length - 1];
    }
    public void lastItemType(){
        if(this.itemType.getIndex() > 0){
            this.itemType = ItemType.values()[itemType.getIndex() - 1];
        } else this.itemType = ItemType.values()[ItemType.values().length - 1];
    }

}
