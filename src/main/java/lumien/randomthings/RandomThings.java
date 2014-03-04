package lumien.randomthings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Client.GuiHandler;
import lumien.randomthings.Configuration.ConfigItems;
import lumien.randomthings.Configuration.RTConfiguration;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Core.RTCreativeTab;
import lumien.randomthings.Handler.BackgroundHandler;
import lumien.randomthings.Handler.RTEventHandler;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Library.Recipes;
import lumien.randomthings.Network.PacketPipeline;
import lumien.randomthings.TileEntities.ModTileEntities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.Mod.EventHandler;

@Mod(modid = RandomThings.MOD_ID, name = RandomThings.MOD_NAME, version = RandomThings.MOD_VERSION)
public class RandomThings
{
	@Instance(RandomThings.MOD_ID)
	public static RandomThings instance;

	public static final String MOD_ID = "RandomThings";
	public static final String MOD_NAME = "Random Things";
	public static final String MOD_VERSION = "@VERSION@";

	public static final PacketPipeline packetPipeline = new PacketPipeline();

	public static final RTCreativeTab creativeTab = new RTCreativeTab();

	public Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		logger.info("Test");

		RTConfiguration.init(event);

		ModItems.init();
		ModBlocks.init();
		ModTileEntities.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		MinecraftForge.EVENT_BUS.register(new RTEventHandler());

		if (event.getSide().isClient())
		{
			BackgroundHandler.setRandomBackground();
		}
	}
	


	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		packetPipeline.initalise();

		Recipes.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		packetPipeline.postInitialise();
	}

	@EventHandler
	public void imcMessages(IMCEvent event)
	{
		ImmutableList<IMCMessage> messages = event.getMessages();
		if (messages.size() > 0)
		{
			for (IMCMessage m : messages)
			{
				String action = m.key;

				if (action.equals("setSolutionColor"))
				{
					NBTTagCompound nbt = m.getNBTValue();
					int biomeID = nbt.getInteger("biomeID");
					int color = nbt.getInteger("color");

					ModItems.biomeSolution.biomeColors.put(biomeID, color);
					if (Settings.DEBUG)
					{
						logger.info(m.getSender() + " registered a custom solution color (" + color + ") for the biome " + biomeID);
					}
				}
			}
		}
	}
}
