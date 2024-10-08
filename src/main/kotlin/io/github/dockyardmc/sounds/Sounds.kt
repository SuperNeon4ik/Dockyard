package io.github.dockyardmc.sounds

import io.github.dockyardmc.DockyardServer
import io.github.dockyardmc.extentions.*
import io.github.dockyardmc.location.Location
import io.github.dockyardmc.player.Player
import io.github.dockyardmc.player.PlayerManager
import io.github.dockyardmc.protocol.packets.play.clientbound.ClientboundPlaySoundPacket
import io.github.dockyardmc.protocol.packets.play.clientbound.SoundCategory
import io.github.dockyardmc.world.World
import io.netty.buffer.ByteBuf

class Sound(var identifier: String, var location: Location, var volume: Float = 0.5f, var pitch: Float = 1.0f, var category: SoundCategory = SoundCategory.MASTER) {

    init {
        if(!identifier.contains(":")) identifier = "minecraft:$identifier"
    }
}

fun Player.playSound(sound: Sound) {
    val packet = ClientboundPlaySoundPacket(sound)
    this.sendPacket(packet)
}

fun Player.playSound(identifier: String, location: Location = this.location, volume: Float = 0.5f, pitch: Float = 1.0f, category: SoundCategory = SoundCategory.MASTER) {
    val sound = Sound(identifier, location, volume, pitch, category)
    this.playSound(sound)
}

fun Collection<Player>.playSound(sound: Sound) {
    this.forEach { it.playSound(sound) }
}

fun Collection<Player>.playSound(identifier: String, location: Location, volume: Float = 0.5f, pitch: Float = 1.0f, category: SoundCategory = SoundCategory.MASTER) {
    val sound = Sound(identifier, location, volume, pitch, category)
    this.playSound(sound)
}

fun World.playSound(sound: Sound) {
    this.players.values.playSound(sound)
}

fun World.playSound(identifier: String, location: Location, volume: Float = 0.5f, pitch: Float = 1.0f, category: SoundCategory = SoundCategory.MASTER) {
    val sound = Sound(identifier, location, volume, pitch, category)
    this.playSound(sound)
}

fun DockyardServer.playSound(sound: Sound) {
    PlayerManager.players.playSound(sound)
}

fun DockyardServer.playSound(identifier: String, location: Location, volume: Float = 0.5f, pitch: Float = 1.0f, category: SoundCategory = SoundCategory.MASTER) {
    val sound = Sound(identifier, location, volume, pitch, category)
    this.playSound(sound)
}

fun ByteBuf.writeSoundEvent(sound: String) {
    this.writeVarInt(0)
    this.writeUtf(sound)
    this.writeBoolean(false)
}

fun ByteBuf.readSoundEvent(): String {
    val type = this.readVarInt()
    val identifier = this.readUtf()
    val hasFixedRange = this.readBoolean()
    if(hasFixedRange) {
        val fixedRange = this.readFloat()
    }
    return identifier
}