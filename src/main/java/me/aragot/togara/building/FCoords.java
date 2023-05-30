package me.aragot.togara.building;

import me.aragot.togara.Togara;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.UUID;

public class FCoords {

    private int x;
    private int y;
    private int z;
    private int f;
    private UUID worldId;

    public FCoords(int x, int y, int z, int f, UUID worldId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.f = f;
        this.worldId = worldId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getF() {
        return f;
    }

    public UUID getWorldId(){
        return this.worldId;
    }

    public FCoords toGround(){
        int minY = -65;
        World world = Togara.instance.getServer().getWorld(this.getWorldId());
        if(world == null) return null;

        for(int i = y; y > minY; i--){
            Block block = world.getBlockAt(this.x, i, this.z);
            if(i == -64){
                this.y = -64;
                return this;
            }
            if(block.getType() == Material.AIR) continue;
            this.y = i;
            return this;
        }
        return null;
    }
}
