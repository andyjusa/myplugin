package serverplugin.psy

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.inventivetalent.glow.GlowAPI

class IreliaInfo{
    val attackCool=60L
    val coolTime=80L
    val heal=3
    val damage = 3.0
    val name = "irelia"
}
class Irelia {
    fun interactEntity(e:PlayerInteractAtEntityEvent, config:FileConfiguration,plugin:Plugin)
    {
        val info = IreliaInfo()
        val player:Player=e.player
        val target:Entity=e.rightClicked
        if(config.getString("${player.name}.psy")==info.name && !config.getBoolean("${player.name}.dash")&&!config.getBoolean("${player.name}.coolTime"))
        {
            if(target is LivingEntity)
            {
                val entityLocation = target.location
                val location=player.location
                val loc = entityLocation.subtract(player.location).multiply(1.0/3.0)
                val task: BukkitRunnable = object : BukkitRunnable() {
                    override fun run() {
                        location.add(loc)
                        player.teleport(location)
                    }
                }
                val task1: BukkitRunnable = object : BukkitRunnable() {
                    override fun run() {
                        location.add(loc)
                        player.teleport(location)
                    }
                }
                val taskEnd: BukkitRunnable = object : BukkitRunnable() {
                    override fun run() {
                        location.add(loc)
                        player.teleport(location)
                        target.damage(info.damage,player)
                        player.health = if((player.health+info.heal)>=player.maxHealth) player.maxHealth else player.health+info.heal
                        config.set("${player.name}.dash",false)
                        val taskCool: BukkitRunnable = object : BukkitRunnable() {
                            override fun run() {
                                config.set("${player.name}.coolTime",false)
                            }
                        }
                        val taskAttack: BukkitRunnable = object : BukkitRunnable() {
                            override fun run() {
                                config.set("${player.name}.${target.uniqueId}.Attacked",false)
                            }
                        }
                        if(config.getBoolean("${player.name}.${target.uniqueId}.Attacked")&&!target.isDead)
                        {
                            config.set("${player.name}.coolTime",true)
                            taskCool.runTaskLater(plugin,info.coolTime)
                        }else if(!config.getBoolean("${player.name}.${target.uniqueId}.Attacked"))
                        {
                            config.set("${player.name}.${target.uniqueId}.Attacked",true)
                            taskAttack.runTaskLater(plugin,info.attackCool)
                        }
                    }
                }
                task.runTaskLater(plugin,1L)
                task1.runTaskLater(plugin,2L)
                taskEnd.runTaskLater(plugin,3L)
                config.set("${player.name}.dash",true)
            }
        }
    }
}