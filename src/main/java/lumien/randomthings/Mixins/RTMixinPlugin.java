package lumien.randomthings.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.Configuration.VanillaChanges;
import lumien.randomthings.Library.Reference;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.libraries.org.objectweb.asm.tree.ClassNode;

public class RTMixinPlugin implements IMixinConfigPlugin {

    private static final Logger LOG = LogManager.getLogger(Reference.MOD_NAME + " Mixins");

    static {
        final File file = new File("config/RandomThings.cfg");
        final Configuration config = new Configuration(file);
        config.load();
        VanillaChanges.FASTER_LEAVEDECAY = config.get(
                "VanillaChanges",
                "FasterLeaveDecay",
                true,
                "Leaves will decay much faster when no longer connected to a log").getBoolean(true);
        VanillaChanges.LOCKED_GAMMA = config.get("VanillaChanges", "LockedGamma", false, "Locks the Gamma to 0")
                .setRequiresMcRestart(true).getBoolean(false);
        ConfigBlocks.wirelessLever = config.get("Blocks", "WirelessLever", true).getBoolean(true);
        LOG.info("Loaded early config");
        if (config.hasChanged()) config.save();
    }

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    // This method return a List<String> of mixins. Every mixins in this list will be loaded.
    @Override
    public List<String> getMixins() {
        List<String> mixins = new ArrayList<>();
        for (Mixin mixin : Mixin.values()) {
            if (mixin.shouldLoad()) {
                mixins.add(mixin.mixinClass);
                LOG.debug("Loading mixin: " + mixin.mixinClass);
            }
        }
        return mixins;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
