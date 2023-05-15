package me.aragot.togara.entities.enemies;

import me.aragot.togara.Togara;
import me.aragot.togara.entities.TogaraEntity;
import me.aragot.togara.items.Rarity;
import me.aragot.togara.stats.EntityStats;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataType;

//This is an example class of an Entity, Entities are registered in initCustomEntities of EntityHandler

public class TestMob extends TogaraEntity {

    private Location spawnLocation;

    public TestMob(){
        super("TEST_MOB", "Test Mob", true);
    }

    public TestMob(Location location){
        super("TEST_MOB", "Test Mob", false);
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
            zombie.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "entityId"), PersistentDataType.STRING, "TEST_MOB");
        }));
    }

    @Override
    public void spawn(Location location){
        new TestMob(location);
    }
}
