package xyz.sora23.TestAI;


import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.mob.EntityZombie;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDeathEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.TaskHandler;
import xyz.sora23.TestAI.Zombie.CustomZombie;
import xyz.sora23.TestAI.Zombie.Task.ZombieTask;

import java.util.*;

public class Main extends PluginBase implements Listener {

    public Main(){

    }

    public Map<Long, TaskHandler> ids = new HashMap<Long, TaskHandler>();
    @Override
    public void onEnable () {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent ev){
        Entity entity = ev.getEntity();
        if(entity instanceof EntityZombie && ev instanceof EntityDamageByEntityEvent){
            Entity damager = ((EntityDamageByEntityEvent) ev).getDamager();
            Long id = damager.getId();
            if(!this.ids.containsKey(id)){
                CustomZombie zombie = new CustomZombie((EntityZombie) entity, (EntityHuman) damager);
                TaskHandler taskHandler = this.getServer().getScheduler().scheduleRepeatingTask(new ZombieTask(this, zombie), 1);
                this.ids.put(id, taskHandler);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent ev){
        Entity entity = ev.getEntity();
        Long id = entity.getId();
        if(this.ids.containsKey(id)){
            TaskHandler handler = this.ids.get(id);
            handler.cancel();
        }
    }
}