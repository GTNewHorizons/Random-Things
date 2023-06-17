package lumien.randomthings.Mixins.Minecraft;

import net.minecraft.client.renderer.EntityRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import lumien.randomthings.Handler.LightmapHandler;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @ModifyVariable(method = "updateLightmap", at = @At(value = "STORE"), index = 20, name = "k", ordinal = 2)
    private int rtManipulateGreen(int val) {
        return LightmapHandler.manipulateGreen(val);
    }

    @ModifyVariable(method = "updateLightmap", at = @At(value = "STORE"), index = 21, name = "l", ordinal = 3)
    private int rtManipulateBlue(int val) {
        return LightmapHandler.manipulateBlue(val);
    }
}
