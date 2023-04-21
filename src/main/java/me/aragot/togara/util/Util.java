package me.aragot.togara.util;

import me.aragot.togara.Togara;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Map;


public class Util {

    public static ItemStack getHead(Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.displayName(Component.text(player.getName()));
        skull.setOwningPlayer(player);
        item.setItemMeta(skull);
        return item;
    }

    public static int getEmptySlots(Player p) {
        PlayerInventory inventory = p.getInventory();
        ItemStack[] cont = inventory.getContents();
        int i = 0;
        for (ItemStack item : cont){
            if (item != null && item.getType() != Material.AIR) {
                i++;
            }
        }
        return 36 - i;
    }
    public static int getAmountOfItems(String itemId, Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack[] cont = inventory.getContents();
        int i = 0;
        for (ItemStack item : cont){
            if(item == null) continue;
            String currentId = Togara.itemHandler.getItemIdFromStack(item);
            if(currentId.equalsIgnoreCase(itemId)) i = i + item.getAmount();
        }
        return i;
    }

    public static void removeItems(Player player, String itemId, int amount){
        HashMap<Integer, Integer> toRemove = new HashMap<>();
        PlayerInventory inventory = player.getInventory();
        ItemStack[] cont = inventory.getContents();

        int i = 0;
        for (ItemStack item : cont){
            if(item == null){
                i++;
                continue;
            }
            String currentId = Togara.itemHandler.getItemIdFromStack(item);
            if(amount == 0) break;
            if(itemId.equalsIgnoreCase(currentId)){
                if(amount >= 64 && item.getAmount() == 64){
                    toRemove.put(i, 64);
                    amount = amount - 64;
                } else if(amount >= 64 && item.getAmount() != 64){
                    toRemove.put(i, item.getAmount());
                    amount = amount - item.getAmount();
                } else if(amount < 64  && item.getAmount() >= amount){
                    toRemove.put(i, amount);
                    amount = 0;
                } else if(amount < 64 && item.getAmount() < amount){
                    toRemove.put(i, item.getAmount());
                    amount = amount - item.getAmount();
                }
            }
            i++;
        }

        for(Map.Entry<Integer, Integer> remove : toRemove.entrySet()){
            if(remove.getValue() == 64) inventory.clear(remove.getKey());
            else {
                inventory.getItem(remove.getKey()).setAmount(inventory.getItem(remove.getKey()).getAmount() - remove.getValue());
            }
        }
    }

}
