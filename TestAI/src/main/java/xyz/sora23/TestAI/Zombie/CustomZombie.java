package xyz.sora23.TestAI.Zombie;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.mob.EntityZombie;
import cn.nukkit.entity.EntityHuman;

import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;


import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;

public class CustomZombie {

    public EntityZombie zombie;
    public EntityHuman target;

    public CustomZombie(Entity zombie, Entity target){
        this.zombie = zombie;
        if(target == null) this.target = this.setTarget();
    }

    public double getX(){
        return this.zombie.x;
    }

    public double getY(){
        return this.zombie.y;
    }

    public double getZ(){
        return this.zombie.z;
    }

    public void setYaw(double deg){
        this.zombie.yaw = deg;
    }

    public void setPitch(double deg){
        this.zombie.pitch = deg;
    }

    public Level getLevel(){
        return this.zombie.getLevel();
    }

    public EntityHuman setTarget() {
        Level level = this.zombie.getLevel();
        Entity entities[] = level.getNearbyEntities(this.zombie.boundingBox.grow(10, 10, 10), this.zombie);
        int i;
        for(i=0;i<entities.length;i++){
            if(entities[i] instanceof EntityHuman){
                this.target = (EntityHuman) entities[i];
                return this.getTarget();
            }
        }
        return null;
    }

    public EntityHuman getTarget(){
        return this.target;
    }

    public BlockFace getDirection(){
        return this.zombie.getDirection();
    }

    public int getFrontBlock(int y){
        Level level = this.getLevel();
        int id = null
        switch (this.getDirection()){
            case NORTH:
                id = level.getBlockIdAt((int) Math.floor(this.getX()) - 1, (int) this.getY() + y,(int) Math.floor(this.getZ()));
                break;
            case SOUTH:
                id = level.getBlockIdAt((int) Math.floor(this.getX()) + 1, (int) this.getY() + y,(int) Math.floor(this.getZ()));
                break;
            case EAST:
                id = level.getBlockIdAt( (int) Math.floor(this.getX()), (int) this.getY() + y,(int) Math.floor(this.getZ()) - 1);
                break;
            case WEST:
                id = level.getBlockIdAt( (int) Math.floor(this.getX()), (int) this.getY() + y, (int) Math.floor(this.getZ()) + 1);

        }
        return id;
    }


    public void attack2target(int val){
        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(this.zombie, this.target, EntityDamageEvent.DamageCause.ENTITY_ATTACK, val);
        this.target.attack(event);
    }
}
