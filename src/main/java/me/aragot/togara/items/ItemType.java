package me.aragot.togara.items;

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
    private int i;

    ItemType(int i){
        this.i = i;
    }

    public int getIndex(){
        return this.i;
    }
}
