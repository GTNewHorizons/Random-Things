package lumien.randomthings.Configuration;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Handler.BackgroundHandler;
import lumien.randomthings.Handler.MagneticForceHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class RTConfiguration
{
	public static Configuration config;

	public static void init(FMLPreInitializationEvent event)
	{
		RTConfiguration.config = new Configuration(event.getSuggestedConfigurationFile());
	}

	public static void onConfigChange(OnConfigChangedEvent event)
	{
		if (event.modID.equals(RandomThings.MOD_ID))
			syncConfig();
	}

	public static void syncConfig()
	{
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
		ConfigBlocks.spiritRod = isBlockEnabled("SpiritRod");

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
		ConfigItems.ginto = isItemEnabled("Ginto");

		ConfigDungeonLoot.WHITESTONE_CHANCE = RTConfiguration.config.get("DungeonLoot", "WhiteStone", 3).getInt();

		VanillaChanges.MODIFIED_BACKGROUND = RTConfiguration.config.get("VanillaChanges", "ModifiedBackgrounds", true, "The normal dirt background will be replaced with a different block each start").setRequiresMcRestart(true).getBoolean(true);
		VanillaChanges.LOCKED_GAMMA = RTConfiguration.config.get("VanillaChanges", "LockedGamma", false, "Locks the Gamma to 0").setRequiresMcRestart(true).getBoolean(false);
		VanillaChanges.FASTER_LEAVEDECAY = config.get("VanillaChanges", "FasterLeaveDecay", true ,"Leaves will decay much faster when no longer connected to a log").getBoolean(true);
		VanillaChanges.HARDCORE_DARKNESS = config.get("VanillaChanges", "Hardcore Darkness", false ,"The minimum light will be removed so if there's no light source it's actually going to be completely black").setRequiresMcRestart(true).getBoolean(false);
		
		BackgroundHandler.fixedBackground = RTConfiguration.config.get("VanillaChanges", "fixedBackground", "", "If this is not empty the options background will not be random but the one specified here. This has to be the name of a block texture without the .png").setRequiresMcRestart(true).getString();

		VanillaChanges.THROWABLES_MOTION = RTConfiguration.config.get("VanillaChanges", "ThrowableMotion", false, "When you throw something or shoot an arrow the motion of the player will be added to the motion of the projectile").getBoolean(false);

		Settings.ANIMATED_TEXTURES = RTConfiguration.config.get("Settings", "AnimatedTextures", true).setRequiresMcRestart(true).getBoolean(true);
		Settings.FERTILIZEDDIRT_GROWTHINDICATOR = RTConfiguration.config.get("Settings", "FertilizedDirtGrowthIndicator", false, "Bonemeal particles will appear whenever fertilized dirt boosts the plant").getBoolean(false);
		Settings.SPIRIT_CHANCE = RTConfiguration.config.get("Settings", "SpiritChance", 0.02, "The chance of a spirit spawning when you have a spirit binder in your inventory. (0-1)").getDouble(0.1);
		Settings.SPIRIT_CHANCE_SWORD = RTConfiguration.config.get("Settings", "SpiritChanceSword", 0.1, "The chance of a spirit spawning when you have a spirit binder in your inventory and kill the entity with a spectre sword. (0-1)").getDouble(0.1);
		Settings.WIRELESSLEVER_RANGE = RTConfiguration.config.get("Settings", "WirelessLeverRange", 10, "The range of a wireless lever in blocks").getDouble(10);
		Settings.SPECTRE_DIMENSON_ID = RTConfiguration.config.get("Settings", "SpectreDimensionID", -1, "The Dimension ID of the spectre World. On first run and when you remove this setting from the config file RandomThings will try to find a dimensionID itself").setRequiresMcRestart(true).getInt();

		MagneticForceHandler.TELEPORT_LENGTH = RTConfiguration.config.get("Settings", "MagneticForceTeleportLength", 200, "In ticks (20=1 Second)").getInt();

		if (RTConfiguration.config.hasChanged())
			RTConfiguration.config.save();
	}

	private static boolean isBlockEnabled(String block)
	{
		return RTConfiguration.config.get("Blocks", block, true).getBoolean(true);
	}

	private static boolean isItemEnabled(String item)
	{
		return RTConfiguration.config.get("Items", item, true).getBoolean(true);
	}
}
