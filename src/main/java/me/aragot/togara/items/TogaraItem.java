package me.aragot.togara.items;

import me.aragot.togara.Togara;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class TogaraItem {

    private Material material;
    private String itemId;
    private ItemType type;
    private Rarity rarity;

    public TogaraItem(Material material, String itemId, ItemType type, Rarity rarity) {
        this.material = material;
        this.itemId = itemId;
        this.type = type;
        this.rarity = rarity;
    }

    public ItemMeta getDefaultItemMeta(){
        MiniMessage mm = Togara.mm;
        ItemStack stack = new ItemStack(material);

        ItemMeta meta = stack.getItemMeta();

        //Common rarity color = white
        String color = "white";

        if(this.rarity == Rarity.UNCOMMON){
            color = "green";
        } else if(this.rarity == Rarity.RARE){
            color = "blue";
        } else if(this.rarity == Rarity.EPIC){
            color = "dark_purple";
        } else if(this.rarity == Rarity.LEGENDARY){
            color = "gold";
        }

        PlainTextComponentSerializer serializer = PlainTextComponentSerializer.builder().build();
        meta.displayName(mm.deserialize("<" + color + ">" + serializer.serialize(stack.displayName()).replace("[", "").replace("]", "") + "</" + color + ">").decoration(TextDecoration.ITALIC, false));
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "itemId"), PersistentDataType.STRING, itemId);
        ArrayList<Component> lore = new ArrayList<>();
        if(this instanceof TogaraWeapon) lore.addAll(getItemStats(stack));
        lore.addAll(getDescription());
        lore.add(mm.deserialize("<" + color + "><b>" + rarity.name() + " " + type.name() + "</b></" + color + ">" ).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        return meta;
    }

    public static ItemMeta getItemMeta(ItemStack stack){
        MiniMessage mm = Togara.mm;

        ItemMeta meta = stack.getItemMeta();
        TogaraItem item = Togara.itemHandler.getTogaraItemFromStack(stack);

        //Common rarity color = white
        String color = "white";

        if(this.rarity == Rarity.UNCOMMON){
            color = "green";
        } else if(this.rarity == Rarity.RARE){
            color = "blue";
        } else if(this.rarity == Rarity.EPIC){
            color = "dark_purple";
        } else if(this.rarity == Rarity.LEGENDARY){
            color = "gold";
        }

        PlainTextComponentSerializer serializer = PlainTextComponentSerializer.builder().build();
        meta.displayName(mm.deserialize("<" + color + ">" + serializer.serialize(stack.displayName()).replace("[", "").replace("]", "") + "</" + color + ">").decoration(TextDecoration.ITALIC, false));
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "itemId"), PersistentDataType.STRING, itemId);
        ArrayList<Component> lore = new ArrayList<>();
        if(this instanceof TogaraWeapon) lore.addAll(getItemStats(stack));
        lore.addAll(getDescription());
        lore.add(mm.deserialize("<" + color + "><b>" + rarity.name() + " " + type.name() + "</b></" + color + ">" ).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        return meta;
    }

    public ArrayList<Component> getDescription(){
        ArrayList<Component> toReturn = new ArrayList<>();
        toReturn.add(Component.text(" "));
        return toReturn;
    }

    public ArrayList<Component> getItemStats(ItemStack stack){

    }


    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ItemStack getItemStack(){
        return new ItemStack(this.material);
    }

    public ItemStack getTogaraItemStack(){
        ItemStack stack = new ItemStack(this.material);
        stack.setItemMeta(this.getDefaultItemMeta());
        return stack;
    }

    public boolean isStackable(){
        if(material.getMaxStackSize() == 1) return false;
        return true;
    }

}
