package lumien.randomthings.Client.Config;

import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import java.util.ArrayList;
import java.util.List;
import lumien.randomthings.Configuration.RTConfiguration;
import lumien.randomthings.Library.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class RandomThingsConfigGUI extends GuiConfig {
    public RandomThingsConfigGUI(GuiScreen parent) {
        super(
                parent,
                getConfigElements(),
                Reference.MOD_ID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(RTConfiguration.config.toString()));
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.add(new DummyCategoryElement(
                "settings",
                "Settings",
                new ConfigElement(RTConfiguration.config.getCategory("settings")).getChildElements()));
        list.add(new DummyCategoryElement(
                "vanillachanges",
                "Vanilla Changes",
                new ConfigElement(RTConfiguration.config.getCategory("vanillachanges")).getChildElements()));
        return list;
    }
}
