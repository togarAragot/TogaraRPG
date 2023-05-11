package me.aragot.togara.stats;

public interface Stats {
    //Armor stats
    int getDefense();
    int getMagicDefense();
    int getSpeed();

    //General stats
    double getMana();
    int getHeal();
    int getRegen();

    //Combat stats
    int getStrength();
    int getCritChance();
    int getCritDamage();
    int getMagicPenetration();
    int getArmorPenetration();
    double getSwingRange();


}
