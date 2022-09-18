package lumien.randomthings.Mixins.Minecraft;

import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityLiving.class)
public interface EntityLivingAccessor {
    @Accessor
    int getExperienceValue();

    @Accessor
    void setExperienceValue(int experienceValue);
}
