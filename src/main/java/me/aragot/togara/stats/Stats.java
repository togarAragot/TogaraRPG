package me.aragot.togara.stats;

public interface Stats {
    //Armor stats
    int getDefense();
    int getMagicDefense();
    long getHealth();
    int getSpeed();

    //General stats
    double getMana();

    //Combat stats
    int getStrength();
    int getCritChance();
    int getCritDamage();
    int getMagicPenetration();
    int getArmorPenetration();
    double getSwingRange();


}
