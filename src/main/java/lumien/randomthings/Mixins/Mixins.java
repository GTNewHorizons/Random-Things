package lumien.randomthings.Mixins;

import javax.annotation.Nonnull;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.Configuration.VanillaChanges;

/**
 * IMPORTANT: Do not make any references to any mod from this file. This file is loaded quite early on and if you refer
 * to other mods you load them as well. The consequence is: You can't inject any previously loaded classes! Exception:
 * Reference.java, as long as it is used for Strings only!
 */
public enum Mixins implements IMixins {

    // spotless:off
    MINECRAFT(new MixinBuilder()
            .addClientMixins(
                    "MixinEntityRenderer",
                    "GuiAccessor",
                    "MixinItem",
                    "MixinRenderGlobal")
            .addCommonMixins(
                    "EntityAccessor",
                    "EntityLivingAccessor",
                    "PotionAccessor",
                    "MixinSpawnerAnimals",
                    "WorldServerAccessor",
                    "WorldAccessor")
            .setPhase(Phase.EARLY)),
    BLOCK_LEAVES_BASE(new MixinBuilder()
            .addCommonMixins("MixinBlockLeavesBase")
            .setApplyIf(() -> VanillaChanges.FASTER_LEAVEDECAY)
            .setPhase(Phase.EARLY)),
    THAUMCRAFT_FAST_LEAVE_DECAY(new MixinBuilder()
            .addCommonMixins("MixinBlockMagicalLeaves")
            .addRequiredMod(TargetedMod.THAUMCRAFT)
            .setPhase(Phase.LATE)
            .setApplyIf(() -> VanillaChanges.FASTER_LEAVEDECAY)),
    GUIVIDEOSETTINGS(new MixinBuilder()
            .addClientMixins("MixinGuiVideoSettings")
            .setApplyIf(() -> VanillaChanges.LOCKED_GAMMA)
            .setPhase(Phase.EARLY)),
    WORLD(new MixinBuilder()
            .addCommonMixins("MixinWorld")
            .setApplyIf(() -> ConfigBlocks.wirelessLever)
            .setPhase(Phase.EARLY));
    // spotless:on

    private final MixinBuilder builder;

    Mixins(MixinBuilder builder) {
        this.builder = builder;
    }

    @Nonnull
    @Override
    public MixinBuilder getBuilder() {
        return this.builder;
    }
}
