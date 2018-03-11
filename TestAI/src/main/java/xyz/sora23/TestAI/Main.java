package xyz.sora23.TestAI;


import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.mob.EntityZombie;
import xyz.sora23.TestAI.Zombie.CustomZombie;
import xyz.sora23.TestAI.Zombie.Task.ZombieTask;
public class Main extends PluginBase implements Listener {

    public int ids[];
    @Override
    public void onEnable () {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent ev){
        Entity entity = ev.getEntity();
        if(entity instanceof EntityZombie && ev instanceof EntityDamageByEntityEvent){
            CustomZombie zombie = new CustomZombie(entity, ((EntityDamageByEntityEvent) ev).getDamager());
            this.getServer().getScheduler().scheduleRepeatingTask(new ZombieTask(this, zombie),1);
        }
    }
}