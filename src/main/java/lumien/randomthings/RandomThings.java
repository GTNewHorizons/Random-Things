package lumien.randomthings;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Client.GuiHandler;
import lumien.randomthings.Configuration.RTConfiguration;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Core.RTCreativeTab;
import lumien.randomthings.Entity.EntityDyeSlime;
import lumien.randomthings.Entity.ModEntitys;
import lumien.randomthings.Handler.BackgroundHandler;
import lumien.randomthings.Handler.LetterHandler;
import lumien.randomthings.Handler.PeripheralProvider;
import lumien.randomthings.Handler.RTEventHandler;
import lumien.randomthings.Handler.RTTickHandler;
import lumien.randomthings.Handler.SoundRecorderHandler;
import lumien.randomthings.Handler.Notifications.NotificationHandler;
import lumien.randomthings.Items.ItemBiomeSolution;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Library.Recipes;
import lumien.randomthings.Network.PacketPipeline;
import lumien.randomthings.Proxy.CommonProxy;
import lumien.randomthings.TileEntities.ModTileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.Mod.EventHandler;

@Mod(modid = RandomThings.MOD_ID, name = RandomThings.MOD_NAME, version = RandomThings.MOD_VERSION)
public class RandomThings
{
	@Instance(RandomThings.MOD_ID)
	public static RandomThings instance;

	public static final String MOD_ID = "RandomThings";
	public static final String MOD_NAME = "Random Things";
	public static final String MOD_VERSION = "@VERSION@";

	@SidedProxy(clientSide = "lumien.randomthings.Proxy.ClientProxy", serverSide = "lumien.randomthings.Proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final PacketPipeline packetPipeline = new PacketPipeline();

	public static final RTCreativeTab creativeTab = new RTCreativeTab();

	public Logger logger;

	File nbtFile;
	public NBTTagCompound modNBT;

	public LetterHandler letterHandler;
	public NotificationHandler notificationHandler;
	public SoundRecorderHandler soundRecorderHandler;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		RTConfiguration.init(event);

		ModItems.init();
		ModBlocks.init();
		ModTileEntities.init();
		ModEntitys.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		FMLCommonHandler.instance().bus().register(new RTTickHandler());

		MinecraftForge.EVENT_BUS.register(new RTEventHandler());
		proxy.registerTickHandler();
		
		if (event.getSide().isClient())
		{
			notificationHandler = new NotificationHandler();
			BackgroundHandler.setRandomBackground();
			soundRecorderHandler = new SoundRecorderHandler();
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		packetPipeline.initalise();
		RandomThings.proxy.registerRenderers();

		Recipes.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		packetPipeline.postInitialise();
		
		try
		{
			PeripheralProvider.register();
		}
		catch (Exception e)
		{
			logger.log(Level.WARN, "Couldn't reflect on cc, no peripheral support for CreativePlayerInterface and OnlineDetector");
			e.printStackTrace();
		}
	}

	public void saveNBT()
	{
		try
		{
			CompressedStreamTools.write(modNBT, nbtFile);
		}
		catch (IOException e)
		{
			logger.log(Level.WARN, "Couldn't use NBT Mod File, things like letters won't persist.");
			e.printStackTrace();
		}
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		initializeModNBT();

		letterHandler = new LetterHandler();
		letterHandler.readFromNBT();
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

					ItemBiomeSolution.biomeColors.put(biomeID, color);
					if (Settings.DEBUG)
					{
						logger.info(m.getSender() + " registered a custom solution color (" + color + ") for the biome " + biomeID);
					}
				}
			}
		}
	}

	private void initializeModNBT()
	{
		nbtFile = new File(DimensionManager.getCurrentSaveRootDirectory(), "RandomThings.dat");
		if (!nbtFile.exists())
		{
			logger.log(Level.INFO, "Creating NBT File");

			try
			{
				nbtFile.createNewFile();
				CompressedStreamTools.write(new NBTTagCompound(), nbtFile);
				modNBT = CompressedStreamTools.read(nbtFile);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				modNBT = CompressedStreamTools.read(nbtFile);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		if (modNBT == null)
		{
			logger.log(Level.WARN, "Couldn't use NBT Mod File, things like ender letters won't persist.");
			modNBT = new NBTTagCompound();
		}
	}
}
