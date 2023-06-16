package lumien.randomthings.Mixins.Minecraft;

import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import lumien.randomthings.Handler.CoreHandler;

@Mixin(World.class)
public class MixinWorld {

    @Inject(method = "isBlockIndirectlyGettingPowered(III)Z", at = @At("HEAD"), cancellable = true)
    public void isBlockIndirectlyGettingRTPowered(int p_72864_1_, int p_72864_2_, int p_72864_3_,
            CallbackInfoReturnable<Boolean> cir) {
        World world = (World) (Object) this;
        if (CoreHandler.isBlockIndirectlyGettingPowered(world, p_72864_1_, p_72864_2_, p_72864_3_)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
