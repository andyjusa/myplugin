package serverplugin.psy

import serverplugin.Main
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class Trindamier {
    fun entitydamagebyentityevent(e: EntityDamageByEntityEvent,config:FileConfiguration)
    {
        val player = e.damager
        if(player is Player &&config.getString("${player.name}.psy")=="trindamier")
        {
            val dam=(player.maxHealth-player.health)*3
            e.damage+=dam*e.damage*0.1
        }
        if(e.entity is Player &&config.getString("${e.entity.name}.psy")=="trindamier")
        {
            val task: BukkitRunnable = object : BukkitRunnable() {
                override fun run() {
                    config.set("${e.entity.name}.cooltime",false)
                }
            }
            if((e.entity as Player).health<5&&!config.getBoolean("${e.entity.name}.cooltime"))
            {
                config.set("${e.entity.name}.cooltime",true)
                (e.entity as Player).addPotionEffect(PotionEffect(PotionEffectType.SPEED ,100,2))
                (e.entity as Player).addPotionEffect(PotionEffect(PotionEffectType.ABSORPTION ,1000,2))
                task.runTaskLater(Main(), 600)
            }
        }
    }
}