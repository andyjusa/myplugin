package serverplugin.psy

import org.bukkit.FluidCollisionMode
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Arrow
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityShootBowEvent
import java.util.function.Predicate
class KalistarInfo{
    val name = "kalistar"
    val arrowSize = 1.5
    val range = 10.0
    val alpha=0.7
    val damage = 5
    val fireTicks = 100
}
class Kalistar{
    fun kalistar(e: EntityShootBowEvent,config:FileConfiguration){
        val info = KalistarInfo()
        val player =e.entity
        val arrow = e.projectile
        if(arrow is Arrow &&player is Player &&config.getString("${player.name}.psy")==info.name&&player.inventory.itemInMainHand.type== Material.BOW)
        {
            val start = player.eyeLocation
            val direction = start.direction
            val world = start.world
            e.isCancelled=true
            val pre: Predicate<Entity> = Predicate{ num: Entity -> num !=player && num is LivingEntity }
            val hitResult = world?.rayTrace(
                start,
                direction,
                info.range,
                FluidCollisionMode.NEVER,
                true,
                info.arrowSize,
                pre
            )
            var isfire = false
            e.bow?.enchantments?.get(Enchantment.ARROW_FIRE).let()
            {
                if (it != null) {
                    isfire= it>0
                }
            }
            hitResult?.hitEntity.let { target ->
                target?.location?.let {
                    target.world.spawnParticle(
                        Particle.FIREWORKS_SPARK,
                        it,10,0.0,0.0,0.0,0.1,null,true)
                }
                var alpha=0.0
                e.bow?.enchantments?.get(Enchantment.ARROW_DAMAGE).let {
                    if (it != null) {
                        alpha+=it*info.alpha
                    }
                }
                target?.let {
                    it as LivingEntity
                    it.damage(info.damage+alpha,player)
                    it.fireTicks=if(isfire)info.fireTicks else it.fireTicks
                    player.location.y+=0.5
                    player.velocity.y=0.0
                    val velocity = player.velocity.normalize().multiply(1.6)
                    velocity.y=0.3
                    player.velocity=velocity
                }
            }
        }
    }
}