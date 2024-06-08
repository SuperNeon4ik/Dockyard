package io.github.dockyardmc.plugins.bundled

import io.github.dockyardmc.DockyardServer
import io.github.dockyardmc.ServerMetrics
import io.github.dockyardmc.commands.Commands
import io.github.dockyardmc.commands.PlayerArgument
import io.github.dockyardmc.events.Events
import io.github.dockyardmc.events.PlayerBlockBreakEvent
import io.github.dockyardmc.events.PlayerBlockPlaceEvent
import io.github.dockyardmc.events.PlayerMoveEvent
import io.github.dockyardmc.extentions.truncate
import io.github.dockyardmc.location.Location
import io.github.dockyardmc.particles.DustParticleData
import io.github.dockyardmc.particles.DustTransitionParticleData
import io.github.dockyardmc.particles.spawnParticle
import io.github.dockyardmc.periodic.Period
import io.github.dockyardmc.periodic.TickPeriod
import io.github.dockyardmc.player.Player
import io.github.dockyardmc.player.SkinManager
import io.github.dockyardmc.plugins.DockyardPlugin
import io.github.dockyardmc.registry.Particles
import io.github.dockyardmc.scroll.RGB
import io.github.dockyardmc.utils.MathUtils
import io.github.dockyardmc.utils.Vector3
import io.github.dockyardmc.utils.Vector3f
import io.github.dockyardmc.world.World
import io.github.dockyardmc.world.WorldManager
import kotlin.math.cos
import kotlin.math.sin

class MayaTestPlugin: DockyardPlugin {
    override var name: String = "MayaTestPlugin"
    override var author: String = "LukynkaCZE"
    override var version: String = DockyardServer.versionInfo.dockyardVersion

    var showCube = false

    override fun load(server: DockyardServer) {


        Period.on<TickPeriod> {
            val runtime = Runtime.getRuntime()
            val mspt = ServerMetrics.millisecondsPerTick
            val memoryUsage = runtime.totalMemory() - runtime.freeMemory()
            val memUsagePercent = MathUtils.percent(runtime.totalMemory().toDouble(), memoryUsage.toDouble()).truncate(0)

            val fMem = (memoryUsage.toDouble() / 1000000).truncate(1)
            val fMax = (runtime.totalMemory().toDouble() / 1000000).truncate(1)
            DockyardServer.broadcastActionBar("<white>MSPT: <lime>$mspt <dark_gray>| <white>Memory Usage: <#ff6830>$memUsagePercent% <gray>(${fMem}mb / ${fMax}mb)")
        }

        Commands.add("/skin") {
            it.execute { exec ->
                SkinManager.updateSkinOf(exec.player!!)
            }
        }


    }

    override fun unload(server: DockyardServer) {
        TODO("Not yet implemented")
    }
}