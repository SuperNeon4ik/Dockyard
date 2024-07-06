package io.github.dockyardmc.inventory

import io.github.dockyardmc.bindables.BindableMap
import io.github.dockyardmc.entities.Entity
import io.github.dockyardmc.item.ItemStack
import io.github.dockyardmc.player.Player
import io.github.dockyardmc.protocol.packets.play.clientbound.ClientboundSetInventorySlotPacket
import io.github.dockyardmc.utils.MathUtils

class Inventory(var entity: Entity) {
    val name: String = "Inventory"
    val size = entity.inventorySize
    val slots: BindableMap<Int, ItemStack> = BindableMap()

    init {
        slots.itemSet { (entity as? Player)?.sendInventoryUpdate(it.key) }
        slots.itemRemoved { (entity as? Player)?.sendInventoryUpdate(it.key) }
    }

    operator fun set(slot: Int, item: ItemStack) {
        slots[slot] = item
    }

    operator fun get(slot: Int): ItemStack = slots[slot] ?: ItemStack.air

    fun clear() {
        slots.clear(false)
    }

    //TODO make work
    fun Player.give(itemStack: ItemStack) {

    }

    fun Player.clearInventory() {
        this@Inventory.clear()
    }

    fun Player.sendInventoryUpdate(slot: Int) {
        val clientSlot =  MathUtils.toOriginalSlotIndex(slot)
        val packet = ClientboundSetInventorySlotPacket(0, 0, clientSlot, slots[slot] ?: ItemStack.air)
        this.sendPacket(packet)
    }
}