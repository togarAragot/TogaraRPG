package me.aragot.togara.items;

public enum Rarity {

    ALL(0),
    COMMON(1),
    UNCOMMON(2),
    RARE(3),
    EPIC(4),
    LEGENDARY(5);

    public static final int size = Rarity.values().length;
    private int i;
    Rarity(int i) {
        this.i = i;
    }

    public int getIndex(){
        return this.i;
    }
}
