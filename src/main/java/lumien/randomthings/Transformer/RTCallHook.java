package lumien.randomthings.Transformer;

import cpw.mods.fml.relauncher.IFMLCallHook;
import java.io.File;
import java.util.Map;
import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.Configuration.VanillaChanges;
import net.minecraftforge.common.config.Configuration;

public class RTCallHook implements IFMLCallHook {

    @Override
    public Void call() {
        final File file = new File("config/RandomThings.cfg");
        final Configuration config = new Configuration(file);
        config.load();
        VanillaChanges.FASTER_LEAVEDECAY = config.get(
                        "VanillaChanges",
                        "FasterLeaveDecay",
                        true,
                        "Leaves will decay much faster when no longer connected to a log")
                .getBoolean(true);
        VanillaChanges.HARDCORE_DARKNESS = config.get(
                        "VanillaChanges",
                        "Hardcore Darkness",
                        false,
                        "The minimum light will be removed so if there's no light source it's actually going to be completely black")
                .getBoolean(false);
        ConfigBlocks.wirelessLever = config.get("Blocks", "WirelessLever", true).getBoolean(true);
        RTClassTransformer.coreLogger.info("Loaded early config from RTCallHook");

        if (config.hasChanged()) config.save();

        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}
}
