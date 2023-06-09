package me.aragot.togara.items;

import me.aragot.togara.Togara;
import me.aragot.togara.items.armor.boots.HermesBoots;
import me.aragot.togara.items.crops.Crop;
import me.aragot.togara.items.melee.SwordOfTogara;
import me.aragot.togara.stats.ItemStats;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class ItemHandler {

    public static ArrayList<TogaraItem> itemList = new ArrayList<>();

    public ItemHandler(){

        Crop.register();
        for(Material mat : Material.values()){
            ItemType type = getItemType(mat);
            Rarity rarity = getRarity(mat);
            TogaraItem item;
            if(ItemType.isTool(type)) {
                ItemStats stats = new ItemStats();
                double damage = 0;
                for (AttributeModifier modifier : mat.getDefaultAttributeModifiers(EquipmentSlot.HAND).get(Attribute.GENERIC_ATTACK_DAMAGE))
                    damage = modifier.getAmount() + damage;
                stats.setDamage((long) damage * 5);
                item = new TogaraWeapon(mat, mat.name().toUpperCase().replaceAll(" ", "_"), type, rarity, stats);
            } else if(ItemType.isArmor(type)){
                ItemStats stats = new ItemStats();
                double armor = 0;
                for (AttributeModifier modifier : mat.getDefaultAttributeModifiers(EquipmentSlot.HEAD).get(Attribute.GENERIC_ARMOR))
                    armor = modifier.getAmount() + armor;
                for (AttributeModifier modifier : mat.getDefaultAttributeModifiers(EquipmentSlot.CHEST).get(Attribute.GENERIC_ARMOR))
                    armor = modifier.getAmount() + armor;
                for (AttributeModifier modifier : mat.getDefaultAttributeModifiers(EquipmentSlot.LEGS).get(Attribute.GENERIC_ARMOR))
                    armor = modifier.getAmount() + armor;
                for (AttributeModifier modifier : mat.getDefaultAttributeModifiers(EquipmentSlot.FEET).get(Attribute.GENERIC_ARMOR))
                    armor = modifier.getAmount() + armor;
                stats.setDefense((int) armor * 5);
                item = new TogaraArmor(mat, mat.name().toUpperCase().replaceAll(" ", "_"), type, rarity, stats);
            } else {
                item = new TogaraItem(mat, mat.name().toUpperCase().replaceAll(" ", "_"), type,rarity);
            }

            itemList.add(item);
        }
        registerCustomItems();
    }

    public void registerCustomItems(){
        //Weapons
        SwordOfTogara.register();
        //Armor
        HermesBoots.register();
    }

    public TogaraItem getTogaraItemById(String itemId){
        for(TogaraItem item : itemList){
            if(item.getItemId().equalsIgnoreCase(itemId)) return item;
        }
        return null;
    }

    public TogaraItem getTogaraItemByMaterial(Material material){
        for(TogaraItem item : itemList){
            if(item.getMaterial().equals(material)){
                return item;
            }
        }
        return null;
    }

    private ItemType getItemType(Material material){
        ItemType type = ItemType.MISCELLANEOUS;
        String name = material.name().toUpperCase();


        if(material.isBlock()) type = ItemType.BLOCK;
        else if(material.isEdible()) type = ItemType.CONSUMABLE;
        else if(name.contains("SWORD")) type = ItemType.SWORD;
        else if(name.contains("BOW")) type = ItemType.BOW;
        else if(name.contains("TRIDENT")) type = ItemType.TRIDENT;
        else if(name.contains("PICKAXE")) type = ItemType.PICKAXE;
        else if(name.contains("SHOVEL")) type = ItemType.SHOVEL;
        else if(name.contains("HOE")) type = ItemType.HOE;
        else if(name.contains("AXE")) type = ItemType.AXE;
        else if(name.contains("BOOTS")) type = ItemType.BOOTS;
        else if(name.contains("LEGGINGS")) type = ItemType.LEGGINGS;
        else if(name.contains("CHESTPLATE")) type = ItemType.CHESTPLATE;
        else if(name.contains("HELMET")) type = ItemType.HELMET;
        else if(name.contains("POTION")) type = ItemType.POTION;
        else if(name.contains("ARROW")) type = ItemType.ARROW;
        else if(Crop.isCrop(material)) type = ItemType.CROP;

        return type;
    }

    public ItemType getItemType(String itemId){
        ItemType itemType = ItemType.MISCELLANEOUS;
        for(TogaraItem item : itemList){
            if(item.getItemId().equalsIgnoreCase(itemId)) return item.getType();
        }
        return itemType;
    }

    private Rarity getRarity(Material material){
        Rarity rarity = Rarity.COMMON;
        String name = material.name().toUpperCase();
        if(name.contains("DIAMOND")) rarity = Rarity.UNCOMMON;
        else if(name.contains("NETHERITE")) rarity = Rarity.RARE;

        return rarity;
    }

    public Rarity getRarity(String itemId){
        Rarity rarity = Rarity.COMMON;
        for(TogaraItem item : itemList){
            if(item.getItemId().equalsIgnoreCase(itemId)) return item.getRarity();
        }
        return rarity;
    }

    public String getItemIdFromStack(ItemStack stack){
        ItemMeta meta = stack.getItemMeta();
        String itemId = meta.getPersistentDataContainer().get(new NamespacedKey(Togara.instance, "itemId"), PersistentDataType.STRING);
        if(itemId != null) return itemId;
        return "";
    }

    public TogaraItem getTogaraItemFromStack(ItemStack stack){
        if(stack == null) return null;
        ItemMeta meta = stack.getItemMeta();
        if(meta == null) return null;
        String itemId = meta.getPersistentDataContainer().get(new NamespacedKey(Togara.instance, "itemId"), PersistentDataType.STRING);
        if(itemId == null) return null;
        return getTogaraItemById(itemId);
    }

}
