package lumien.randomthings.Mixins.Minecraft;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Gui.class)
public interface GuiAccessor {

    @Accessor
    static void setOptionsBackground(ResourceLocation ressource) {
        throw new IllegalStateException("Mixin stub invoked");
    }
}
