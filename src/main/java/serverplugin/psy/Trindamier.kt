package serverplugin.psy

import serverplugin.Main
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
class TrindamierInfo
{
    val name="trindamier"
    val damageIncrease=0.1
    val speedTime = 100
    val absorptionTime = 1000
    val coolTime = 600L//tick
    val speedEffort=2
    val absorptionEffort=2
}
class Trindamier {
    fun entityDamageByEntityEvent(e: EntityDamageByEntityEvent, config:FileConfiguration)
    {
        val player = e.damager
        val info = TrindamierInfo()
        if(player is Player &&config.getString("${player.name}.psy")==info.name)
        {
            val dam=player.maxHealth-player.health
            e.damage =(dam*e.damage*info.damageIncrease)+e.damage
        }
        if(e.entity is Player &&config.getString("${e.entity.name}.psy")==info.name)
        {
            val task: BukkitRunnable = object : BukkitRunnable() {
                override fun run() {
                    config.set("${e.entity.name}.coolTime",false)
                }
            }
            if((e.entity as Player).health<5&&!config.getBoolean("${e.entity.name}.coolTime"))
            {
                config.set("${e.entity.name}.coolTime",true)
                (e.entity as Player).addPotionEffect(PotionEffect(PotionEffectType.SPEED ,info.speedTime,info.speedEffort))
                (e.entity as Player).addPotionEffect(PotionEffect(PotionEffectType.ABSORPTION ,info.absorptionTime,info.absorptionEffort))
                task.runTaskLater(Main(), info.coolTime)
            }
        }
    }
}