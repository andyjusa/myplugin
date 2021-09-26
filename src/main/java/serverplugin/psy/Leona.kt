package serverplugin.psy

import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import serverplugin.stun
class LeonaInfo
{
    val name = "leona"
    val stunTime = 10L//tick
    val coolTime = 60L//tick
}
class Leona {
    fun attack(e:EntityDamageByEntityEvent, config:FileConfiguration, plugin: Plugin){
        val info=LeonaInfo()
        if(e.damager is Player&&config.getString("${e.damager.name}.psy")==info.name&&(e.entity as Player).inventory.itemInMainHand.type!= Material.BOW&&(e.entity as Player).inventory.itemInMainHand.type!= Material.CROSSBOW)
        {
            val task: BukkitRunnable = object : BukkitRunnable() {
                override fun run() {
                    config.set("${e.damager.name}.coolTime",false)
                }
            }
            if(!config.getBoolean("${e.damager.name}.coolTime")&&e.entity is Player)
            {
                stun().add(e.entity as Player,info.stunTime,config,plugin,(e.damager as Player).name)
                config.set("${e.damager.name}.coolTime",true)
                task.runTaskLater(plugin,info.coolTime)
            }
        }
    }
}