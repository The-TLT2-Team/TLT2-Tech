package com.fg.tlt_tech.network.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Objects;
import java.util.function.Supplier;

public class PSyncToolPersistentDataC2S {
    public final CompoundTag nbt;
    public final EquipmentSlot slot;
    public PSyncToolPersistentDataC2S(CompoundTag nbt, EquipmentSlot slot){
        this.nbt = nbt;
        this.slot = slot;
    }
    public PSyncToolPersistentDataC2S(FriendlyByteBuf byteBuf){
        this.nbt = byteBuf.readNbt();
        this.slot = byteBuf.readEnum(EquipmentSlot.class);
    }
    public void toByte(FriendlyByteBuf byteBuf){
        byteBuf.writeNbt(nbt);
        byteBuf.writeEnum(slot);
    }
    public void handle(Supplier<NetworkEvent.Context> supplier){
        if (supplier.get().getSender()!=null){
            ToolStack.from(supplier.get().getSender().getItemBySlot(slot)).getPersistentData().copyFrom(nbt);
        }
    }
}
