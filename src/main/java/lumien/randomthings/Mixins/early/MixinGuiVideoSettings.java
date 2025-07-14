package lumien.randomthings.Mixins.early;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.settings.GameSettings;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiVideoSettings.class)
public class MixinGuiVideoSettings {

    @Mutable
    @Final
    @Shadow
    private static GameSettings.Options[] videoOptions;

    @Inject(method = "<clinit>", at = @At(value = "RETURN"))
    private static void rt$lockGamma(CallbackInfo ci) {
        ArrayList<GameSettings.Options> options = new ArrayList<>(Arrays.asList(videoOptions));
        options.removeIf(option -> option == GameSettings.Options.GAMMA);
        videoOptions = options.toArray(videoOptions);
    }
}
