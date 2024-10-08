package io.github.dockyardmc

import io.github.dockyardmc.commands.Commands
import io.github.dockyardmc.commands.IntArgument
import io.github.dockyardmc.commands.StringArgument
import io.github.dockyardmc.datagen.EventsDocumentationGenerator
import io.github.dockyardmc.datagen.VerifyPacketIds
import io.github.dockyardmc.entities.*
import io.github.dockyardmc.events.Events
import io.github.dockyardmc.events.PlayerJoinEvent
import io.github.dockyardmc.events.PlayerPreSpawnWorldSelectionEvent
import io.github.dockyardmc.location.Location
import io.github.dockyardmc.player.*
import io.github.dockyardmc.registry.*
import io.github.dockyardmc.utils.DebugScoreboard
import io.github.dockyardmc.world.WorldManager
import io.github.dockyardmc.world.generators.FlatWorldGenerator

// This is just maya testing env.. do not actually run this
fun main(args: Array<String>) {

    if(args.contains("validate-packets")) {
        VerifyPacketIds()
        return
    }

    if(args.contains("event-documentation")) {
        EventsDocumentationGenerator()
        return
    }

    val testWorld = WorldManager.create("test", FlatWorldGenerator(), DimensionTypes.OVERWORLD)
    testWorld.defaultSpawnLocation = Location(0, 201, 0, testWorld)

    Events.on<PlayerPreSpawnWorldSelectionEvent> {
        it.world = testWorld
    }

    Events.on<PlayerJoinEvent> {
        val player = it.player
        player.gameMode.value = GameMode.CREATIVE
        player.inventory[0] = Items.CHERRY_TRAPDOOR.toItemStack()
        DebugScoreboard.sidebar.viewers.add(player)
        player.addPotionEffect(PotionEffects.NIGHT_VISION, 99999, 0, false)
        player.addPotionEffect(PotionEffects.SPEED, 99999, 3, false)
    }

    Commands.add("/world") { cmd ->
        cmd.addArgument("world", StringArgument())
        cmd.execute { executor ->
            val player = executor.player!!
            val world = WorldManager.getOrThrow(cmd.get<String>("world"))
            world.join(player)
        }
    }

    Commands.add("/potion") {
        it.addArgument("effect", StringArgument())
        it.addArgument("duration", IntArgument())
        it.execute { ctx ->
            val player = ctx.player!!
            val effect = it.get<String>("effect")
            val duration = it.get<Int>("duration")
            val potionEffect = PotionEffects.getPotionEffect(effect)
            player.addPotionEffect(potionEffect, duration)
        }
    }

    val player = PlayerManager.playerToEntityIdMap[0]!!

    player.metadata[EntityMetadataType.STATE] = getEntityMetadataState(player) {
        isGlowing = true
        isInvisible = false
        isFlying = true
    }

    val server = DockyardServer()
    server.start()
}

fun MutableList<EntityMetadata>.removeByType(type: EntityMetadataType) {
    val meta = this.firstOrNull { it.type == type } ?: return
    this.remove(meta)
}