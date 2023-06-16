package lumien.randomthings;

import java.io.File;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Client.GuiHandler;
import lumien.randomthings.Configuration.RTConfiguration;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Core.Commands.ExitSpectreCommand;
import lumien.randomthings.Core.Commands.RTCommand;
import lumien.randomthings.Core.RTCreativeTab;
import lumien.randomthings.Entity.ModEntitys;
import lumien.randomthings.Handler.BackgroundHandler;
import lumien.randomthings.Handler.LetterHandler;
import lumien.randomthings.Handler.MagneticForceHandler;
import lumien.randomthings.Handler.ModCompHandler;
import lumien.randomthings.Handler.Notifications.NotificationHandler;
import lumien.randomthings.Handler.PeripheralProvider;
import lumien.randomthings.Handler.RTEventHandler;
import lumien.randomthings.Handler.RTTickHandler;
import lumien.randomthings.Handler.SoundRecorderHandler;
import lumien.randomthings.Handler.Spectre.SpectreHandler;
import lumien.randomthings.Handler.Spectre.WorldProviderSpectre;
import lumien.randomthings.Items.ItemBiomeCapsule;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Library.Recipes;
import lumien.randomthings.Library.Reference;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.Potions.ModPotions;
import lumien.randomthings.Proxy.CommonProxy;
import lumien.randomthings.TileEntities.ModTileEntities;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.MOD_VERSION,
        guiFactory = "lumien.randomthings.Client.Config.RandomThingsGuiFactory")
public class RandomThings {

    @Instance(Reference.MOD_ID)
    public static RandomThings instance;

    public static final String AUTHOR_USERNAME = "XxsumsumxX";

    @SidedProxy(
            clientSide = "lumien.randomthings.Proxy.ClientProxy",
            serverSide = "lumien.randomthings.Proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final RTCreativeTab creativeTab = new RTCreativeTab();

    public Logger logger;

    public SpectreHandler spectreHandler;

    File nbtFile;
    public NBTTagCompound modNBT;

    public LetterHandler letterHandler;
    public NotificationHandler notificationHandler;
    public SoundRecorderHandler soundRecorderHandler;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        RTConfiguration.init(event);
        RTConfiguration.syncConfig();

        ModItems.init();
        ModBlocks.init();
        ModTileEntities.init();

        ModEntitys.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        FMLCommonHandler.instance().bus().register(new RTTickHandler());

        RTEventHandler rte = new RTEventHandler();
        MinecraftForge.EVENT_BUS.register(rte);
        FMLCommonHandler.instance().bus().register(rte);

        proxy.registerTickHandler();

        if (event.getSide().isClient()) {
            notificationHandler = new NotificationHandler();
            BackgroundHandler.setRandomBackground();
            soundRecorderHandler = new SoundRecorderHandler();
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PacketHandler.init();
        RandomThings.proxy.registerRenderers();

        if (Settings.SPECTRE_DIMENSON_ID == -1) {
            int dimensionID = DimensionManager.getNextFreeDimId();
            logger.log(Level.INFO, "Auto Resolved Spectre Dimension ID to " + dimensionID);
            RTConfiguration.spectreDimensionID.set(dimensionID);

            RTConfiguration.syncConfig();
        }
        DimensionManager.registerProviderType(Settings.SPECTRE_DIMENSON_ID, WorldProviderSpectre.class, true);
        DimensionManager.registerDimension(Settings.SPECTRE_DIMENSON_ID, Settings.SPECTRE_DIMENSON_ID);

        Recipes.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        try {
            PeripheralProvider.register();
        } catch (Exception e) {
            logger.log(
                    Level.WARN,
                    "Couldn't reflect on cc, no cc peripheral support for CreativePlayerInterface and OnlineDetector and Notification Interface");
            e.printStackTrace();
        }

        ModPotions.init();
        ModCompHandler.postInit();
    }

    public boolean canBeDeactivated() {
        return false;
    }

    public void saveNBT() {
        try {
            CompressedStreamTools.write(modNBT, nbtFile);
        } catch (IOException e) {
            ChatComponentTranslation cct = new ChatComponentTranslation("text.error.nbt");
            logger.log(Level.WARN, cct.getUnformattedText());
            e.printStackTrace();
        }
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        initializeModNBT();

        letterHandler = new LetterHandler();
        letterHandler.readFromNBT();

        MagneticForceHandler.INSTANCE.readFromNBT(modNBT);

        event.registerServerCommand(new RTCommand());
        event.registerServerCommand(new ExitSpectreCommand());
    }

    @EventHandler
    public void imcMessages(IMCEvent event) {
        ImmutableList<IMCMessage> messages = event.getMessages();
        if (messages.size() > 0) {
            for (IMCMessage m : messages) {
                String action = m.key;

                if (action.equals("setSolutionColor")) {
                    NBTTagCompound nbt = m.getNBTValue();
                    int biomeID = nbt.getInteger("biomeID");
                    int color = nbt.getInteger("color");

                    ItemBiomeCapsule.biomeColors.put(biomeID, color);
                }
            }
        }
    }

    private void initializeModNBT() {
        nbtFile = new File(DimensionManager.getCurrentSaveRootDirectory(), "RandomThings.dat");
        if (!nbtFile.exists()) {
            logger.log(Level.INFO, "Creating NBT File");

            try {
                nbtFile.createNewFile();
                CompressedStreamTools.write(new NBTTagCompound(), nbtFile);
                modNBT = CompressedStreamTools.read(nbtFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                modNBT = CompressedStreamTools.read(nbtFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (modNBT == null) {
            ChatComponentTranslation cct = new ChatComponentTranslation("text.error.nbt");
            logger.log(Level.WARN, cct.getUnformattedText());
            modNBT = new NBTTagCompound();
        }
    }
}
