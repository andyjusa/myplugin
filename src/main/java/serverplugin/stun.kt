package serverplugin

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable


class stun {
    fun add(player: Player,time:Long,config:FileConfiguration,plugin:Plugin,attacker:String){
        config.set("${player.name}.stun",true)
        config.set("${player.name}.stunned",attacker)
        val task: BukkitRunnable = object : BukkitRunnable() {
            override fun run() {
                if(config.getString("${player.name}.stunned")==attacker) {config.set("${player.name}.stun",false)}
            }
        }
        task.runTaskLater(plugin,time)
    }
}