package serverplugin

import org.bukkit.Server
import org.bukkit.block.Block
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent

class Protect {
    fun interact(e: PlayerInteractEvent,config: FileConfiguration,server: Server)
    {
        if(e.clickedBlock!=null)
        {
            e.isCancelled=blocking(e.clickedBlock as Block,e.player,config,server)
        }

    }
    fun blocking(b: Block, p: Player,config: FileConfiguration,server: Server):Boolean
    {
        val pl =server.offlinePlayers
        val onpl =server.onlinePlayers
        var inBlock =false
        for (a in pl){
            val x0=config.getInt("${a.name}.x")
            val z0=config.getInt("${a.name}.z")
            val x1=config.getInt("${a.name}.x1")
            val z1=config.getInt("${a.name}.z1")
            val X = if(x0>x1) x0 else x1
            val x = if(x1>x0) x0 else x1
            val Z = if(z0>z1) z0 else z1
            val z = if(z1>z0) z0 else z1
            if((b.location.blockX in x..X)&&(b.location.blockY in z..Z)){
                inBlock = a.name != p.name

            }
        }
        for (a in onpl){
            val x0=config.getInt("${a.name}.x")
            val z0=config.getInt("${a.name}.z")
            val x1=config.getInt("${a.name}.x1")
            val z1=config.getInt("${a.name}.z1")
            val X = if(x0>x1) x0 else x1
            val x = if(x1>x0) x0 else x1
            val Z = if(z0>z1) z0 else z1
            val z = if(z1>z0) z0 else z1
            if((b.location.blockX in x..X)&&(b.location.blockY in z..Z)){
                inBlock = a.name != p.name
            }
        }
        return inBlock
    }
}