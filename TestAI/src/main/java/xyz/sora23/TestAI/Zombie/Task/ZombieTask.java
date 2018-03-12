package xyz.sora23.TestAI.Zombie.Task;

import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import xyz.sora23.TestAI.Main;
import xyz.sora23.TestAI.Zombie.CustomZombie;

import cn.nukkit.entity.EntityHuman;
import cn.nukkit.scheduler.PluginTask;

public class ZombieTask extends PluginTask{
    public CustomZombie zombie;
    public ZombieTask(Main main, CustomZombie zombie) {
        super(main);
        this.zombie = zombie;
    }

    @Override
    public void onRun(int currentTick){
        EntityHuman target = this.zombie.setTarget();
        if(target == null) return;

        double tx = target.x;
        double tz = target.z;

        double cx = this.zombie.getX();
        double cz = this.zombie.getZ();

        double x = 0, y = 0, z = 0;

        if((0 <= cx && 0 <= tx) || (cx < 0 && tx < 0)){
            if(tx < cx){
                x = -(cx - tx);
            }else{
                x = tx - cx;
            }
        }else if(0 <= cx && tx < 0){
            x = -(Math.abs(tx) + cx);
        }else if(cx < 0 && 0 <= tx){
            x = Math.abs(cx) + tx;
        }

        if((0 <= cz && 0 <= tz) || (cz < 0 && tz < 0)){
            if(tz < cz){
                z = -(cz - tz);
            }else{
                z = tz - cz;
            }
        }else if(0 <= cz && tz < 0){
            z = -(Math.abs(tz) + cz);
        }else if(cz < 0 && 0 <= tz){
            z = Math.abs(cz) + tz;
        }

        double rad = Math.atan2(x, z);

        x = CustomZombie.SPEED * Math.sin(rad);
        z = CustomZombie.SPEED * Math.cos(rad);

        if(this.zombie.getFrontBlock(0) != Block.AIR && this.zombie.getFrontBlock(1) == Block.AIR && this.zombie.getFrontBlock(2) == Block.AIR){
            y = 1;
        }
        if(this.zombie.getUnderBlock() == Block.AIR){
            y = -1;
        }
        this.zombie.setYaw(-Math.toDegrees(rad));
        if(this.zombie.getDistance() <= 1){
            this.zombie.attack2target(3);
        }else{
            this.zombie.move(x, y, z);
        }

    }

}
