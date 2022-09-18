package lumien.randomthings.Mixins.Minecraft;

import lumien.randomthings.Handler.LightmapHandler;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
    @ModifyVariable(method = "updateLightmap", at = @At(value = "STORE"), index = 19, name = "j", ordinal = 1)
    private int rtManipulateRed(int val) {
        return LightmapHandler.manipulateRed(val);
    }

    @ModifyVariable(method = "updateLightmap", at = @At(value = "STORE"), index = 20, name = "k", ordinal = 2)
    private int rtManipulateGreen(int val) {
        return LightmapHandler.manipulateGreen(val);
    }

    @ModifyVariable(method = "updateLightmap", at = @At(value = "STORE"), index = 21, name = "l", ordinal = 3)
    private int rtManipulateBlue(int val) {
        return LightmapHandler.manipulateBlue(val);
    }
}
