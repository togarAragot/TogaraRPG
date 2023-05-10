package me.aragot.togara.entities.enemies;

import me.aragot.togara.entities.TogaraEntity;
import me.aragot.togara.items.Rarity;
import me.aragot.togara.stats.EntityStats;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;

public class TestMob extends TogaraEntity {

    private Location spawnLocation;

    public TestMob(Location location){
        super("Test MOB");
        this.spawnLocation = location;
        spawnHealthTag();
    }

    @Override
    public void spawn(){
        EntityStats stats = new EntityStats();
        stats.setMaxHealth(1000000);
        stats.setHealth(1000000);
        stats.setEntityGrade(Rarity.LEGENDARY);
        stats.setLevel(99);
        this.stats = stats;

        spawnLocation.getWorld().spawn(spawnLocation, Zombie.class, (zombie -> {
            this.setEntity(zombie);
            zombie.setAI(false);
            zombie.setGravity(false);
        }));
    }
}
