package lumien.randomthings.Mixins.Minecraft;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lumien.randomthings.Handler.Bloodmoon.ClientBloodmoonHandler;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {

    @Redirect(
            method = "renderSky",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/WorldClient;getSkyColor(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/util/Vec3;"))
    private Vec3 rt$SkyColorHook(WorldClient theWorld, Entity renderViewEntity, float f) {
        Vec3 vec3 = theWorld.getSkyColor(renderViewEntity, f);
        ClientBloodmoonHandler.skyColorHook(vec3);
        return vec3;
    }

    @Inject(
            method = "renderSky",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/WorldClient;getMoonPhase()I",
                    shift = At.Shift.BEFORE))
    private void rt$MoonColorHook(float tessellator, CallbackInfo ci) {
        ClientBloodmoonHandler.moonColorHook();
    }
}
