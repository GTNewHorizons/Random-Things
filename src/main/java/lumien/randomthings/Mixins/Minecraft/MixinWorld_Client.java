package lumien.randomthings.Mixins.Minecraft;

import lumien.randomthings.Configuration.VanillaChanges;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class MixinWorld_Client {
    @Inject(method = "getSunBrightnessBody", at = @At("RETURN"), cancellable = true, remap = false)
    private void rtHardcoreDarkness(float var, CallbackInfoReturnable<Float> cir) {
        if (VanillaChanges.HARDCORE_DARKNESS) {
            cir.setReturnValue((cir.getReturnValueF() - 0.2f) / 0.8f);
            cir.cancel();
        }
    }
}
