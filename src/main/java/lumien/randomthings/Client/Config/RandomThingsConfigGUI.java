package lumien.randomthings.Client.Config;

import java.util.ArrayList;
import java.util.List;

import lumien.randomthings.Configuration.RTConfiguration;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;

public class RandomThingsConfigGUI extends GuiConfig
{
	public RandomThingsConfigGUI(GuiScreen parent)
	{
		super(parent, getConfigElements(), "RandomThings", false, false, GuiConfig.getAbridgedConfigPath(RTConfiguration.config.toString()));
	}

	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("settings", "Settings", new ConfigElement(RTConfiguration.config.getCategory("settings")).getChildElements()));
		list.add(new DummyCategoryElement("vanillachanges", "Vanilla Changes", new ConfigElement(RTConfiguration.config.getCategory("vanillachanges")).getChildElements()));
		return list;
	}
}
