package lumien.randomthings.Mixins.early;

import net.minecraft.potion.Potion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Potion.class)
public interface PotionAccessor {

    @Accessor
    static void setPotionTypes(Potion[] potionTypes) {
        throw new UnsupportedOperationException("Mixin failed to inject!");
    }
}
