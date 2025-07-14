package lumien.randomthings.Mixins;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;

import com.gtnewhorizon.gtnhmixins.IEarlyMixinLoader;
import com.gtnewhorizon.gtnhmixins.builders.IMixins;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.Configuration.VanillaChanges;

@IFMLLoadingPlugin.MCVersion("1.7.10")
public class RandomThingsLoadingPluing implements IFMLLoadingPlugin, IEarlyMixinLoader {

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
        if (config.hasChanged()) config.save();
    }

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String getMixinConfig() {
        return "mixins.RandomThings.early.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedCoreMods) {
        return IMixins.getEarlyMixins(Mixins.class, loadedCoreMods);
    }
}
