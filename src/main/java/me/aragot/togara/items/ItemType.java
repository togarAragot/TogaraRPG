package me.aragot.togara.items;

import java.util.ArrayList;

public enum ItemType {

    SWORD(-1),
    BOW(-1),
    TRIDENT(-1),
    PICKAXE(-1),
    SHOVEL(-1),
    HOE(-1),
    AXE(-1),
    BOOTS(-1),
    LEGGINGS(-1),
    CHESTPLATE(-1),
    HELMET(-1),
    ALL(0),
    POTION(1),
    ARROW(2),
    BLOCK(3),
    CROP(4),
    CONSUMABLE(5),
    MISCELLANEOUS(6);

    public static final int size = ItemType.values().length;
    private final int i;

    ItemType(int i){
        this.i = i;
    }

    public int getIndex(){
        return this.i;
    }

    public static int getStorageSize(){
        int i = 0;
        for(ItemType type : ItemType.values()) if (type.getIndex() != -1) i++;
        return i;
    }

    public static ItemType[] getStorageValues(){
        ArrayList<ItemType> usableList = new ArrayList<>();
        for (ItemType type : ItemType.values()) if(type.getIndex() != -1) usableList.add(type);
        return usableList.toArray(new ItemType[0]);
    }

    public static boolean isTool(ItemType type){
        switch(type){
            case AXE:
            case PICKAXE:
            case SHOVEL:
            case HOE:
            case TRIDENT:
            case SWORD:
                return true;

            default:
                return false;
        }
    }

    public static boolean isArmor(ItemType type){
        switch(type){
            case HELMET:
            case CHESTPLATE:
            case LEGGINGS:
            case BOOTS:
                return true;

            default:
                return false;
        }
    }
}
