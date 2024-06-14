package io.github.dockyardmc.protocol.packets.play.clientbound

import io.github.dockyardmc.annotations.WikiVGEntry
import io.github.dockyardmc.extentions.writeUtf
import io.github.dockyardmc.extentions.writeVarInt
import io.github.dockyardmc.protocol.packets.ClientboundPacket
import io.github.dockyardmc.protocol.packets.ProtocolState

@WikiVGEntry("Sound Effect")
class ClientboundRespawnPacket : ClientboundPacket(0x68, ProtocolState.PLAY) { //nice
    init {
        data.writeUtf("minecraft:overworld")
        data.writeUtf("world")
        data.writeLong(0)
        data.writeByte(1)
        data.writeByte(-1)
        data.writeBoolean(false)
        data.writeBoolean(true)
        data.writeBoolean(false)
        data.writeVarInt(0)
        data.writeByte(1)
    }
}

