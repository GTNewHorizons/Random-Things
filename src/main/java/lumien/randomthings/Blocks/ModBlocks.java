package lumien.randomthings.Blocks;

import lumien.randomthings.Blocks.EnergyDistributors.BlockEnderEnergyDistributor;
import lumien.randomthings.Blocks.EnergyDistributors.BlockEnergyDistributor;
import lumien.randomthings.Blocks.Spectre.BlockSpectreBlock;
import lumien.randomthings.Blocks.Spectre.BlockSpectreGlass;
import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.RandomThings;

public class ModBlocks {

    public static BlockPlayerInterface playerInterface;
    public static BlockCreativePlayerInterface creativePlayerInterface;
    public static BlockFluidDisplay fluidDisplay;
    public static BlockFertilizedDirt fertilizedDirt, fertilizedDirtTilled;
    public static BlockItemCollector itemCollector;
    public static BlockAdvancedItemCollector advancedItemCollector;
    public static BlockOnlineDetector onlineDetector;
    public static BlockMoonSensor moonSensor;
    public static BlockBloodmoonSensor bloodMoonSensor;
    public static BlockNotificationInterface notificationInterface;
    public static BlockSpectreBlock spectreBlock;
    public static BlockLapisLamp spectreLamp;
    public static BlockWirelessLever wirelessLever;
    public static BlockDyeingMachine dyeingMachine;
    public static BlockImbuingStation imbuingStation;
    public static BlockAdvancedFluidDisplay advancedFluidDisplay;
    public static BlockSpectreGlass spectreGlass;

    public static BlockEnergyDistributor energyDistributor;
    public static BlockEnderEnergyDistributor enderEnergyDistributor;

    public static void init() {
        RandomThings.instance.logger.debug("Initializing Blocks");

        if (ConfigBlocks.playerInterface) {
            playerInterface = new BlockPlayerInterface();
            creativePlayerInterface = new BlockCreativePlayerInterface();
        }
        if (ConfigBlocks.fluidDisplay) {
            fluidDisplay = new BlockFluidDisplay();
            advancedFluidDisplay = new BlockAdvancedFluidDisplay();
        }
        if (ConfigBlocks.fertilizedDirt) fertilizedDirt = new BlockFertilizedDirt(false);
        if (ConfigBlocks.fertilizedDirtTilled) fertilizedDirtTilled = new BlockFertilizedDirt(true);
        if (ConfigBlocks.itemCollector) {
            itemCollector = new BlockItemCollector();
            advancedItemCollector = new BlockAdvancedItemCollector();
        }
        if (ConfigBlocks.onlineDetector) onlineDetector = new BlockOnlineDetector();
        if (ConfigBlocks.moonSensor) moonSensor = new BlockMoonSensor();
        if (ConfigBlocks.bloodMoonSensor) bloodMoonSensor = new BlockBloodmoonSensor();
        if (ConfigBlocks.notificationInterface) notificationInterface = new BlockNotificationInterface();
        if (ConfigBlocks.lapisLamp) spectreLamp = new BlockLapisLamp();
        if (ConfigBlocks.wirelessLever) wirelessLever = new BlockWirelessLever();
        if (ConfigBlocks.dyeingMachine) dyeingMachine = new BlockDyeingMachine();
        if (ConfigBlocks.spectreGlass) spectreGlass = new BlockSpectreGlass();
        if (ConfigBlocks.energyDistributor) energyDistributor = new BlockEnergyDistributor();
        if (ConfigBlocks.enderEnergyDistributor) enderEnergyDistributor = new BlockEnderEnergyDistributor();

        // Always there
        spectreBlock = new BlockSpectreBlock();
        imbuingStation = new BlockImbuingStation();
    }
}
