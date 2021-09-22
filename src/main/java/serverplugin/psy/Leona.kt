package serverplugin.psy

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import serverplugin.stun

class Leona {
    fun attack(e:EntityDamageByEntityEvent, config:FileConfiguration, plugin: Plugin){
        if(e.damager is Player&&config.getString("${e.damager.name}.psy")=="leona")
        {
            val task: BukkitRunnable = object : BukkitRunnable() {
                override fun run() {
                    config.set("${e.damager.name}.cooltime",false)
                }
            }
            if(!config.getBoolean("${e.damager.name}.cooltime")&&e.entity is Player)
            {
                stun().add(e.entity as Player,10,config,plugin,(e.damager as Player).name)
                config.set("${e.damager.name}.cooltime",true)
                task.runTaskLater(plugin,60)
            }
        }
    }
}