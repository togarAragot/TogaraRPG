package me.aragot.togara.admin.items;

import me.aragot.togara.Togara;
import me.aragot.togara.items.*;
import me.aragot.togara.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ItemDealerGui implements Listener {

    public static ArrayList<UUID> inSearch = new ArrayList<>();

    private static ArrayList<TogaraItem> allItems = new ArrayList<>();
    private static ArrayList<Info> infos = new ArrayList<>();

    private static ItemStack filler;
    private static ItemStack nothing;
    private static ItemStack close;
    private static ItemStack search;
    private static ItemStack filter;
    private static ItemStack scroll;
    private static ItemStack lastPage;
    private static ItemStack nextPage;

    //Categories
    private static ArrayList<ItemStack> categories = new ArrayList<>();
    private static ItemStack all;
    private static ItemStack weapons;
    private static ItemStack armor;
    private static ItemStack arrows;
    private static ItemStack potions;
    private static ItemStack spawnAreas;

    public static void init(){
        ItemMeta meta;
        filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        meta = filler.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<b>").decoration(TextDecoration.ITALIC, false));
        filler.setItemMeta(meta);

        close = new ItemStack(Material.BARRIER);
        meta = close.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<red>Close</red>").decoration(TextDecoration.ITALIC, false));
        close.setItemMeta(meta);

        search = new ItemStack(Material.OAK_SIGN);
        meta = search.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Search for an Item</yellow>").decoration(TextDecoration.ITALIC, false));
        search.setItemMeta(meta);

        filter = new ItemStack(Material.CLOCK);
        meta = filter.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Filter by</yellow>").decoration(TextDecoration.ITALIC, false));
        filter.setItemMeta(meta);

        nothing = new ItemStack(Material.BARRIER);
        meta = nothing.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<red>Nothing found</red>").decoration(TextDecoration.ITALIC, false));
        nothing.setItemMeta(meta);

        lastPage = new ItemStack(Material.ARROW);
        meta = lastPage.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Previous Page</yellow>").decoration(TextDecoration.ITALIC, false));
        lastPage.setItemMeta(meta);

        nextPage = new ItemStack(Material.ARROW);
        meta = nextPage.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Next Page</yellow>").decoration(TextDecoration.ITALIC, false));
        nextPage.setItemMeta(meta);

        scroll = new ItemStack(Material.ARROW);
        meta = scroll.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Scroll</yellow>").decoration(TextDecoration.ITALIC, false));
        scroll.setItemMeta(meta);

        //Categories

        all = new ItemStack(Material.END_CRYSTAL);
        meta = all.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<gold>All</gold>").decoration(TextDecoration.ITALIC, false));
        all.setItemMeta(meta);
        categories.add(all);

        weapons = new ItemStack(Material.GOLDEN_SWORD);
        meta = weapons.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<red>Weapons</red>").decoration(TextDecoration.ITALIC, false));
        weapons.setItemMeta(meta);
        categories.add(weapons);

        armor = new ItemStack(Material.NETHERITE_CHESTPLATE);
        meta = armor.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Armor</yellow>").decoration(TextDecoration.ITALIC, false));
        armor.setItemMeta(meta);
        categories.add(armor);

        arrows = new ItemStack(Material.SPECTRAL_ARROW);
        meta = arrows.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<blue>Arrows</blue>").decoration(TextDecoration.ITALIC, false));
        arrows.setItemMeta(meta);
        categories.add(arrows);

        potions = new ItemStack(Material.POTION);
        meta = potions.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<blue>Potions</blue>").decoration(TextDecoration.ITALIC, false));
        potions.setItemMeta(meta);
        categories.add(potions);

        spawnAreas = new ItemStack(Material.MAGENTA_CARPET);
        meta = spawnAreas.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<dark_purple>Spawn Areas</dark_purple>").decoration(TextDecoration.ITALIC, false));
        spawnAreas.setItemMeta(meta);
        categories.add(spawnAreas);

        allItems.addAll(TogaraWeapon.weaponItems);
        allItems.addAll(TogaraArmor.armorItems);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e){
        if(!(e.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof ItemDealerHolder)) return;
        if(e.getClickedInventory() == null) return;
        Info info = getInfoByPlayer((Player) e.getWhoClicked());
        if(info == null) info = new Info((Player) e.getWhoClicked());
        if(e.getClickedInventory().getHolder() instanceof ItemDealerHolder){
            switch (e.getSlot()){
                case 0:
                    int page = ((ItemDealerHolder)e.getInventory().getHolder()).getPage();
                    info.setCatIndex(info.getCatIndex() - 1);
                    openItemDealer((Player) e.getWhoClicked(), page);
                    break;
                case 8:
                    page = ((ItemDealerHolder)e.getInventory().getHolder()).getPage();
                    info.setCatIndex(info.getCatIndex() + 1);
                    openItemDealer((Player) e.getWhoClicked(), page);
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    page = ((ItemDealerHolder)e.getInventory().getHolder()).getPage();
                    ItemStack stack = e.getCurrentItem();
                    Material mat = stack.getType();
                    if(mat == Material.END_CRYSTAL){
                        info.setType(ItemType.ALL);
                    } else if(mat == Material.GOLDEN_SWORD){
                        info.setType(ItemType.WEAPON);
                    } else if(mat == Material.NETHERITE_CHESTPLATE){
                        info.setType(ItemType.ARMOR);
                    } else if(mat == Material.SPECTRAL_ARROW){
                       info.setType(ItemType.ARROW);
                    } else if(mat == Material.POTION){
                        info.setType(ItemType.POTION);
                    } else if(mat == Material.MAGENTA_CARPET){
                        info.setType(ItemType.SPAWNAREA);
                    }
                    openItemDealer((Player) e.getWhoClicked(), page);
                    break;
                case 36:
                    page = ((ItemDealerHolder)e.getInventory().getHolder()).getPage();
                    if(page - 1 == 0) break;
                    openItemDealer((Player) e.getWhoClicked(), (page - 1));
                    break;
                case 44:
                    page = ((ItemDealerHolder)e.getInventory().getHolder()).getPage();
                    if(e.getCurrentItem().equals(filler)) break;
                    openItemDealer((Player) e.getWhoClicked(), (page + 1));
                    break;
                case 38:
                    if(e.getClick().isLeftClick()){
                        info.nextFilter();
                    } else if(e.getClick().isRightClick()){
                        info.lastFilter();
                    }
                    openItemDealer((Player) e.getWhoClicked(), 1);
                    break;
                case 42: // Search
                    if(e.getClick().isRightClick()){
                        info.setSearch("");
                        openItemDealer((Player) e.getWhoClicked(), 1);
                        break;
                    } else if(e.getClick().isLeftClick()){
                        Player player = (Player) e.getWhoClicked();
                        Location loc = e.getWhoClicked().getLocation();
                        loc.setX(loc.getBlockX());
                        loc.setY(-64);
                        loc.setZ(loc.getBlockZ());
                        player.closeInventory(InventoryCloseEvent.Reason.OPEN_NEW);
                        player.sendBlockChange(loc, Material.OAK_SIGN.createBlockData());
                        inSearch.add(e.getWhoClicked().getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                ItemDealerPacketHandler.openSign(player, loc);
                            }
                        }.runTaskLater(Togara.instance, 1);
                    }
                case 49:
                    e.getWhoClicked().closeInventory();
                    break;
                default:
                    if(e.getCurrentItem() == null || e.getCurrentItem().equals(filler)  || e.getWhoClicked().getInventory().firstEmpty() == -1) break;
                    TogaraItem item = Togara.itemHandler.getTogaraItemFromStack(e.getCurrentItem());
                    int amount = 0;

                    if(e.getClick().isLeftClick() && !e.getClick().isShiftClick()) amount = 1;
                    else if(e.getClick().isLeftClick() && e.getClick().isShiftClick()) amount = 64;
                    else if(e.getClick().isRightClick() && !e.getClick().isShiftClick()) amount = 32;
                    else if(e.getClick().isRightClick() && e.getClick().isShiftClick()){
                        int emptySlots = Util.getEmptySlots((Player) e.getWhoClicked());
                        amount = emptySlots * 64;
                    }
                    item.give(((Player) e.getWhoClicked()).getPlayer(), amount);
                    openItemDealer((Player) e.getWhoClicked(), ((ItemDealerHolder) e.getWhoClicked().getOpenInventory().getTopInventory().getHolder()).getPage());
                    break;
            }
            //else own inventory
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){
        if(e.getReason() == InventoryCloseEvent.Reason.OPEN_NEW) return;
        if(inSearch.contains(e.getPlayer().getUniqueId())) return;
        infos.remove(getInfoByPlayer((Player) e.getPlayer()));
    }

    @EventHandler
    public void onSignEditEvent(SignChangeEvent e){
        Player player = e.getPlayer();
        if(!(player.getOpenInventory().getTopInventory().getHolder() instanceof ItemDealerHolder)) return;
        Info info = getInfoByPlayer(e.getPlayer());
        String searchTerm = Togara.mm.serialize(e.line(0));
        info.setSearch(searchTerm);
        openItemDealer(player, 1);
    }

    //Maybe critical code
    public static boolean openItemDealer(Player player, int page){

        Info info = getInfoByPlayer(player);

        List<String> itemIds = getDisplayItemsForInfo(info);

        int size = itemIds.size();
        int totalPages = (int) Math.ceil((double) size / 27);

        if(page > totalPages) page = totalPages;
        else if(page < 1) page = 1;

        Inventory inventory = Bukkit.createInventory(new ItemDealerHolder(null, page), 54, Component.text("Item Dealer"));

        for(int i = 0; i < 9; i++) inventory.setItem(i, filler);

        int catIndex = info.getCatIndex();
        for(int i = 2; i < 7; i++){
            if(info.getCatIndex() == 6) info.setCatIndex(0);
            if(info.getCatIndex() + categories.size() - 1 == 0) info.setCatIndex(0);
            if(catIndex + categories.size() - 1 == 0 || catIndex > categories.size() - 1){
                catIndex = 0;
            }
            else if(catIndex < 0) catIndex = categories.size() + catIndex;

            inventory.setItem(i, categories.get(catIndex));
            catIndex++;
        }

        inventory.setItem(0, scroll);
        inventory.setItem(8, scroll);




        int index = 0;
        if(page != 1) index = 27 * (page - 1);
        if(index > itemIds.size()) return false;

        for(int i = 9; i < 36; i++){
            if(index == itemIds.size()) break;
            if(itemIds.get(index).equalsIgnoreCase("NOTHING")){
                inventory.setItem(i, nothing);
                break;
            }
            ItemStack stack = Togara.itemHandler.getTogaraItemById(itemIds.get(index)).getTogaraItemStack();

            ItemMeta stackMeta = stack.getItemMeta();
            stack.setItemMeta(stackMeta);
            inventory.setItem(i, stack);
            index++;
        }

        for(int i = 36; i < 54; i++) inventory.setItem(i, filler);

        ItemMeta filterMeta = filter.getItemMeta();
        filterMeta.lore(getFilterLore(info.getFilter()));
        ItemStack newFilter = filter.clone();
        newFilter.setItemMeta(filterMeta);
        inventory.setItem(38, newFilter);

        ItemMeta searchMeta = search.getItemMeta();
        searchMeta.lore(getSearchLore(info.getSearch()));
        ItemStack newSearch = search.clone();
        newSearch.setItemMeta(searchMeta);
        inventory.setItem(42, newSearch);


        inventory.setItem(36, lastPage);
        inventory.setItem(44, nextPage);

        if(page == 1) inventory.setItem(36, filler);
        if(page == totalPages) inventory.setItem(44, filler);

        inventory.setItem(49, close);

        player.openInventory(inventory);
        return true;
    }

    public static int getSize(){
        return TogaraWeapon.getWeapons().size() + TogaraArmor.getArmorList().size();
    }

    public static List<String> getDisplayItemsForInfo(Info info){
        ArrayList<String> itemIds = new ArrayList<>();
        Rarity filter = info.getFilter();
        ItemType itemType = info.getType();
        String search = info.getSearch();

        for(TogaraItem item : allItems){
            String itemId = item.getItemId();
            if(filter == Rarity.ALL && itemType == ItemType.ALL ||
                    Togara.itemHandler.getRarity(itemId) == filter && itemType == ItemType.ALL ||
                    filter == Rarity.ALL && ItemType.getHeadType(item.getType()) == itemType ||
                    Togara.itemHandler.getRarity(itemId) == filter && ItemType.getHeadType(item.getType()) == itemType){
                if(itemId.replaceAll("_", " ").contains(search.toUpperCase())) itemIds.add(itemId);
            }
        }

        if(!itemIds.isEmpty()) return itemIds;

        return Collections.singletonList("NOTHING");
    }

    public static Info getInfoByPlayer(Player player){
        for(Info info : infos){
            if(info.getPlayer().equals(player.getUniqueId())) return info;
        }

        return null;
    }

    public static void addInfo(Info info){
        infos.add(info);
    }

    private static List<Component> getFilterLore(Rarity rarity){
        ArrayList<Component> filterList = new ArrayList<>();

        filterList.add(Component.text(" "));
        for(Rarity rar : Rarity.values()){
            String color = "white";

            if(rar == Rarity.UNCOMMON){
                color = "green";
            } else if(rar == Rarity.RARE){
                color = "blue";
            } else if(rar == Rarity.EPIC){
                color = "dark_purple";
            } else if(rar == Rarity.LEGENDARY){
                color = "gold";
            }

            if(rar == rarity) filterList.add(Togara.mm.deserialize("<" + color + ">" + rar.name() + " ←</" + color + ">" ).decoration(TextDecoration.ITALIC, false));
            else filterList.add(Togara.mm.deserialize("<" + color + ">" + rar.name() + "</" + color + ">" ).decoration(TextDecoration.ITALIC, false));
            if(rar == Rarity.ALL) filterList.add(Component.text(" "));
        }

        filterList.add(Component.text(" "));
        filterList.add(Togara.mm.deserialize("<blue>Left-Click to move down").decoration(TextDecoration.ITALIC, false));
        filterList.add(Togara.mm.deserialize("<blue>Right-Click to move up").decoration(TextDecoration.ITALIC, false));
        return filterList;
    }

    private static List<Component> getSearchLore(String search){
        ArrayList<Component> searchLore = new ArrayList<>();

        searchLore.add(Component.text(" "));
        if(!search.isEmpty()){
            searchLore.add(Togara.mm.deserialize("<gray>Showing results for <yellow>'" + search + "'</yellow><gray>").decoration(TextDecoration.ITALIC, false));
            searchLore.add(Component.text(" "));
        }
        searchLore.add(Togara.mm.deserialize("<blue>Left-Click to start a new Search<blue>").decoration(TextDecoration.ITALIC, false));
        searchLore.add(Togara.mm.deserialize("<blue>Right-Click to clear the Search<blue>").decoration(TextDecoration.ITALIC, false));
        return searchLore;
    }


    public static class Info{

        private UUID player;
        private String search;
        private Rarity filter;
        private ItemType type;
        private int catIndex;

        public Info(Player player){
            this.player = player.getUniqueId();
            this.search = "";
            this.filter = Rarity.ALL;
            this.catIndex = 0;
            this.type = ItemType.ALL;
        }

        public void lastFilter(){
            if(this.filter.getIndex() > 0){
                this.filter = Rarity.values()[filter.getIndex() - 1];
            } else this.filter = Rarity.values()[Rarity.values().length - 1];
        }

        public void nextFilter(){
            if(this.filter.getIndex() < Rarity.size - 1){
                this.filter = Rarity.values()[filter.getIndex() + 1];
            } else this.filter = Rarity.ALL;
        }

        public UUID getPlayer() {
            return player;
        }

        public void setPlayer(UUID player) {
            this.player = player;
        }

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }

        public Rarity getFilter() {
            return filter;
        }

        public void setFilter(Rarity filter) {
            this.filter = filter;
        }

        public int getCatIndex() {
            return catIndex;
        }

        public void setCatIndex(int catIndex) {
            this.catIndex = catIndex;
        }

        public ItemType getType() {
            return type;
        }

        public void setType(ItemType type) {
            this.type = type;
        }
    }
}
