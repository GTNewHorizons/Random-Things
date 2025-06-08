package lumien.randomthings.Mixins.Minecraft;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {

    @Accessor(remap = false)
    NBTTagCompound getCustomEntityData();

}
