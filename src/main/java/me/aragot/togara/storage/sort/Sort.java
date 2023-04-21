package me.aragot.togara.storage.sort;

public enum Sort {

    NONE(0),
    INCREASING_AMOUNT(1),
    DECREASING_AMOUNT(2);

    public static final int size = Sort.values().length;
    private int i;
    Sort(int i) {
        this.i = i;
    }

    public int getIndex(){
        return this.i;
    }
}
