package lumien.randomthings.Configuration;

import lumien.randomthings.Handler.BackgroundHandler;
import lumien.randomthings.Handler.MagneticForceHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class RTConfiguration
{
	private static Configuration c;

	public static void init(FMLPreInitializationEvent event)
	{
		c = new Configuration(event.getSuggestedConfigurationFile());
		c.load();

		ConfigBlocks.playerInterface = isBlockEnabled("PlayerInterface");
		ConfigBlocks.fluidDisplay = isBlockEnabled("FluidDisplay");
		ConfigBlocks.fertilizedDirt = ConfigBlocks.fertilizedDirtTilled = isBlockEnabled("FertilizedDirt");
		ConfigBlocks.itemCollector = isBlockEnabled("ItemCollector");
		ConfigBlocks.onlineDetector = isBlockEnabled("OnlineDetector");
		ConfigBlocks.moonSensor = isBlockEnabled("MoonSensor");
		ConfigBlocks.notificationInterface = isBlockEnabled("NotificationInterface");
		ConfigBlocks.lapisLamp = isBlockEnabled("LapisLamp");
		ConfigBlocks.carpentryBench = isBlockEnabled("CarpentryBench");
		ConfigBlocks.dyeingMachine = isBlockEnabled("DyeingMachine");

		ConfigItems.biomeCapsule = isItemEnabled("BiomeSolution");
		ConfigItems.biomePainter = isItemEnabled("BiomePainter");
		ConfigItems.whitestone = isItemEnabled("Whitestone");
		ConfigItems.magneticForce = isItemEnabled("MagneticForce");
		ConfigItems.voidStone = isItemEnabled("VoidStone");
		ConfigItems.dropFilter = isItemEnabled("DropFilter");
		ConfigItems.enderLetter = isItemEnabled("EnderLetter");
		ConfigItems.spectreKey = isItemEnabled("SpectreKey");
		ConfigItems.soundRecorder = isItemEnabled("SoundRecorder");
		ConfigItems.spectreArmor = isItemEnabled("SpectreArmor");
		ConfigItems.spectreSword = isItemEnabled("SpectreSword");
		ConfigItems.spiritBinder = isItemEnabled("SpiritBinder");

		ConfigItems.creativeSword = isItemEnabled("CreativeSword");
		ConfigItems.creativeGrower = isItemEnabled("CreativeGrower");
		ConfigItems.creativeChestGenerator = isItemEnabled("CreativeChestGenerator");

		ConfigDungeonLoot.WHITESTONE_CHANCE = c.get("DungeonLoot", "WhiteStone", 3).getInt();

		VanillaChanges.MODIFIED_BACKGROUND = c.get("VanillaChanges", "ModifiedBackgrounds", true, "The normal dirt background will be replaced with a different block each start").getBoolean(true);
		VanillaChanges.LOCKED_GAMMA = c.get("VanillaChanges", "LockedGamma", false, "Locks the Gamma to 0").getBoolean(false);

		BackgroundHandler.fixedBackground = c.get("VanillaChanges", "fixedBackground", "", "If this is not empty the options background will not be random but the one specified here. This has to be the name of a block texture without the .png").getString();

		VanillaChanges.THROWABLES_MOTION = c.get("VanillaChanges", "ThrowableMotion", false, "When you throw something or shoot an arrow the motion of the player will be added to the motion of the projectile").getBoolean(false);

		Settings.ANIMATED_TEXTURES = c.get("Settings", "AnimatedTextures",true).getBoolean(true);
		Settings.FERTILIZEDDIRT_GROWTHINDICATOR = c.get("Settings", "FertilizedDirtGrowthIndicator", false, "Bonemeal particles will appear whenever fertilized dirt boosts the plant").getBoolean(false);
		Settings.SPIRIT_CHANCE = c.get("Settings", "SpiritChance", 0.1, "The chance of a spirit spawning when you have a spirit binder in your inventory. (0-1)").getDouble(0.1);
		Settings.SPIRIT_CHANCE_SWORD = c.get("Settings", "SpiritChanceSword", 0.2, "The chance of a spirit spawning when you have a spirit binder in your inventory and kill the entity with a spectre sword. (0-1)").getDouble(0.1);
		Settings.WIRELESSLEVER_RANGE = c.get("Settings", "WirelessLeverRange", 10, "The range of a wireless lever in blocks").getDouble(10);
		Settings.SPECTRE_DIMENSON_ID = c.get("Settings", "SpectreDimensionID",DimensionManager.getNextFreeDimId(),"The Dimension ID of the spectre World. On first run and when you remove this setting from the config file RandomThings will try to find a dimensionID itself").getInt();
		
		MagneticForceHandler.TELEPORT_LENGTH = c.get("Settings", "MagneticForceTeleportLength", 200, "In ticks (20=1 Second)").getInt();

		c.save();
	}

	private static boolean isBlockEnabled(String block)
	{
		return c.get("Blocks", block,true).getBoolean(true);
	}

	private static boolean isItemEnabled(String item)
	{
		return c.get("Items", item, true).getBoolean(true);
	}
}
