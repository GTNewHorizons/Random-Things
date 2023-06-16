package lumien.randomthings.Library;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.Configuration.ConfigItems;
import lumien.randomthings.Handler.ImbuingStation.ImbuingRecipeHandler;
import lumien.randomthings.Items.ItemFilter;
import lumien.randomthings.Items.ModItems;

public class Recipes {

    static ItemStack iObsidian = new ItemStack(Block.getBlockFromName("obsidian"));
    static ItemStack iEnderChest = new ItemStack(net.minecraft.init.Blocks.ender_chest);
    static ItemStack iNetherstar = new ItemStack(Items.nether_star);
    static ItemStack iEmerald = new ItemStack(Items.emerald);
    static ItemStack iGlassBottle = new ItemStack(Items.glass_bottle);
    static ItemStack iDirt = new ItemStack(Blocks.dirt);
    static ItemStack iBonemeal = new ItemStack(Items.dye, 1, 15);
    static ItemStack iRottenflesh = new ItemStack(Items.rotten_flesh);
    static ItemStack iHopper = new ItemStack(Blocks.hopper);
    static ItemStack iEnderPearl = new ItemStack(Items.ender_pearl);
    static ItemStack iDiamond = new ItemStack(Items.diamond);
    static ItemStack iPaper = new ItemStack(Items.paper);
    static ItemStack iRedstoneTorch = new ItemStack(Blocks.redstone_torch);
    static ItemStack iRedstoneRepeater = new ItemStack(Items.repeater);
    static ItemStack iLeather = new ItemStack(Items.leather);
    static ItemStack iString = new ItemStack(Items.string);
    static ItemStack iFlint = new ItemStack(Items.flint);
    static ItemStack iSnowball = new ItemStack(Items.snowball);
    static ItemStack iQuartz = new ItemStack(Items.quartz);
    static ItemStack iSoulSand = new ItemStack(Blocks.soul_sand);
    static ItemStack iLever = new ItemStack(Blocks.lever);
    static ItemStack iSeed = new ItemStack(Items.wheat_seeds);
    static ItemStack iWaterBottle = new ItemStack(Items.potionitem, 1, 0);

    static ItemStack iRedMushroom = new ItemStack(Blocks.red_mushroom, 1, 0);
    static ItemStack iBlazePowder = new ItemStack(Items.blaze_powder, 1, 0);
    static ItemStack iNetherBrick = new ItemStack(Blocks.nether_brick, 1, 0);
    static ItemStack iGoldIngot = new ItemStack(Items.gold_ingot, 1, 0);
    static ItemStack iBlazeRod = new ItemStack(Items.blaze_rod);
    static ItemStack iWitherSkull = new ItemStack(Items.skull, 1, 1);
    static ItemStack iBone = new ItemStack(Items.bone);
    static ItemStack iCoal = new ItemStack(Items.coal);
    static ItemStack iQuartzBlock = new ItemStack(Blocks.quartz_block);
    static ItemStack iGhastTear = new ItemStack(Items.ghast_tear);
    static ItemStack iDaisy = new ItemStack(Blocks.red_flower, 1, 8);
    static ItemStack iLapisBlock = new ItemStack(Blocks.lapis_block, 1, 0);
    static ItemStack iVine = new ItemStack(Blocks.vine);
    static ItemStack iCauldron = new ItemStack(Items.cauldron);
    static ItemStack iTallGrass = new ItemStack(Blocks.tallgrass, 1, 1);
    static ItemStack iLilypad = new ItemStack(Blocks.waterlily);

    static ItemStack imbuePoison = new ItemStack(ModItems.imbue, 1, 0);
    static ItemStack imbueExperience = new ItemStack(ModItems.imbue, 1, 1);
    static ItemStack imbueFire = new ItemStack(ModItems.imbue, 1, 2);
    static ItemStack imbueWither = new ItemStack(ModItems.imbue, 1, 3);
    static ItemStack imbueWeakness = new ItemStack(ModItems.imbue, 1, 4);
    static ItemStack imbueSpectre = new ItemStack(ModItems.imbue, 1, 5);

    static ItemStack iPlayerCore = new ItemStack(ModItems.ingredients, 1, 0);
    static ItemStack iObsidianStick = new ItemStack(ModItems.ingredients, 1, 1);
    static ItemStack iEnderFragment = new ItemStack(ModItems.ingredients, 1, 2);
    static ItemStack iEctoplasm = new ItemStack(ModItems.ingredients, 1, 3);
    static ItemStack iSpectreIron = new ItemStack(ModItems.ingredients, 1, 4);
    static ItemStack iEnderDiamond = new ItemStack(ModItems.ingredients, 1, 6);
    static ItemStack iEnergizedWater = new ItemStack(ModItems.ingredients, 1, 7);

    static ItemStack iTransformationCore = new ItemStack(ModItems.ingredients, 1, 5);
    static ItemStack iSpiderEye = new ItemStack(Items.spider_eye, 1, 0);
    static ItemStack iAllium = new ItemStack(Blocks.red_flower, 1, 2);

    public static void init() {
        // Always there
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.imbuingStation),
                        "vcv",
                        "tgt",
                        "lel",
                        'v',
                        iVine,
                        'c',
                        iCauldron,
                        't',
                        iTallGrass,
                        'g',
                        new ItemStack(Blocks.stained_hardened_clay, 1, 13),
                        'l',
                        iLilypad,
                        'e',
                        "blockEmerald"));
        // Crafting Items
        GameRegistry
                .addRecipe(new ShapedOreRecipe(iPlayerCore, "xlx", "lel", "xlx", 'l', "gemLapis", 'e', "gemEmerald"));
        GameRegistry
                .addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ingredients, 3, 1), "o", "o", 'o', iObsidian));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(new ItemStack(ModItems.ingredients, 4, 2), "fe", 'e', iEnderPearl, 'f', iFlint));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.ingredients, 1, 4),
                        "ei",
                        'e',
                        iEctoplasm,
                        'i',
                        "ingotIron"));
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModItems.ingredients, 1, 5),
                        "dyeRed",
                        "dyeOrange",
                        "dyeYellow",
                        "dyeGreen",
                        "dyeCyan",
                        "dyePurple",
                        "dyeGray",
                        "dyeLime",
                        "dyeMagenta"));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.ingredients, 1, 6),
                        "xex",
                        "ede",
                        "xex",
                        'e',
                        iEnderPearl,
                        'd',
                        "gemDiamond"));

        if (ConfigBlocks.playerInterface) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.playerInterface),
                        "oeo",
                        "omo",
                        "ono",
                        'o',
                        iObsidian,
                        'e',
                        iEnderChest,
                        'm',
                        iPlayerCore,
                        'n',
                        iNetherstar));
        if (ConfigBlocks.fluidDisplay) {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(ModBlocks.fluidDisplay, 8),
                            "ggg",
                            "gbg",
                            "ggg",
                            'g',
                            "paneGlass",
                            'b',
                            iGlassBottle));
            GameRegistry.addRecipe(
                    new ShapelessOreRecipe(ModBlocks.advancedFluidDisplay, ModBlocks.fluidDisplay, "gemLapis"));
        }
        if (ConfigBlocks.fertilizedDirt) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.fertilizedDirt, 1),
                        "brb",
                        "rdr",
                        "brb",
                        'b',
                        iBonemeal,
                        'r',
                        iRottenflesh,
                        'd',
                        iDirt));
        if (ConfigBlocks.itemCollector) {
            ItemStack iItemCollector = new ItemStack(ModBlocks.itemCollector);
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(ModBlocks.itemCollector, 1),
                            "xhx",
                            "rer",
                            "xox",
                            'h',
                            iHopper,
                            'r',
                            "dustRedstone",
                            'e',
                            iEnderPearl,
                            'o',
                            "stickObsidian"));
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(ModBlocks.advancedItemCollector, 1),
                            "xdx",
                            "gig",
                            'd',
                            "gemDiamond",
                            'g',
                            "dustGlowstone",
                            'i',
                            iItemCollector));
        }
        if (ConfigBlocks.onlineDetector) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.onlineDetector, 1),
                        "sts",
                        "rer",
                        "sts",
                        's',
                        "stone",
                        't',
                        iRedstoneTorch,
                        'r',
                        "dustRedstone",
                        'e',
                        "gemLapis"));
        if (ConfigBlocks.moonSensor) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.moonSensor, 1),
                        "ggg",
                        "lql",
                        "sss",
                        'g',
                        "blockGlass",
                        'l',
                        "gemLapis",
                        's',
                        "slabWood",
                        'q',
                        "gemQuartz"));
        if (ConfigBlocks.lapisLamp) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.spectreLamp, 4, 0),
                        "isi",
                        "sls",
                        "isi",
                        's',
                        "blockGlass",
                        'i',
                        "ingotIron",
                        'l',
                        "gemLapis"));
        if (ConfigBlocks.wirelessLever) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.wirelessLever),
                        "r",
                        "l",
                        'r',
                        "blockRedstone",
                        'l',
                        iLever));
        if (ConfigBlocks.dyeingMachine) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.dyeingMachine),
                        "wpw",
                        "ptp",
                        "wpw",
                        'w',
                        new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE),
                        'p',
                        "plankWood",
                        't',
                        iTransformationCore));
        if (ConfigBlocks.spectreGlass) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.spectreGlass, 16, 0),
                        "ggg",
                        "geg",
                        "ggg",
                        'g',
                        "blockGlass",
                        'e',
                        iEctoplasm));
        if (ConfigBlocks.energyDistributor) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.energyDistributor),
                        "iri",
                        "beb",
                        "iri",
                        'i',
                        "blockIron",
                        'b',
                        "blockRedstone",
                        'r',
                        iRedstoneRepeater,
                        'e',
                        iEnergizedWater));
        if (ConfigBlocks.enderEnergyDistributor && ConfigBlocks.energyDistributor) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.enderEnergyDistributor),
                        "ede",
                        "dsd",
                        "ede",
                        'd',
                        iEnderDiamond,
                        's',
                        new ItemStack(ModBlocks.energyDistributor)));
        if (ConfigBlocks.bloodMoonSensor) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.bloodMoonSensor),
                        "ggg",
                        "rqr",
                        "sss",
                        'g',
                        "blockGlass",
                        'r',
                        "dustRedstone",
                        's',
                        "slabWood",
                        'q',
                        "gemQuartz"));

        if (ConfigItems.voidStone) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.voidStone, 1),
                        "xox",
                        "oeo",
                        "xox",
                        'o',
                        "stone",
                        'e',
                        iEnderPearl));
        if (ConfigItems.dropFilter) {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(ModItems.dropFilter, 1, 0),
                            "lsl",
                            "sfs",
                            "lsl",
                            'l',
                            iLeather,
                            's',
                            iString,
                            'f',
                            iFlint));
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(ModItems.dropFilter, 1, 1),
                            "fe",
                            'f',
                            new ItemStack(ModItems.dropFilter, 1, 0),
                            'e',
                            ConfigItems.voidStone ? ModItems.voidStone : iEnderPearl));
        }
        if (ConfigItems.enderLetter) {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(ModItems.enderLetter, 1, 0),
                            "fpl",
                            'f',
                            iEnderFragment,
                            'p',
                            iPaper,
                            'l',
                            iLeather));
        }
        if (ConfigItems.spectreArmor) addArmorRecipes(
                iSpectreIron,
                new ItemStack(ModItems.spectreHelmet),
                new ItemStack(ModItems.spectreChestplate),
                new ItemStack(ModItems.spectreLeggings),
                new ItemStack(ModItems.spectreBoots));
        if (ConfigItems.spectreSword) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.spectreSword),
                        "xsx",
                        "xsx",
                        "xox",
                        's',
                        iSpectreIron,
                        'o',
                        "stickObsidian"));
        if (ConfigItems.spectreKey) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.spectreKey),
                        "xsx",
                        "xtx",
                        's',
                        iSpectreIron,
                        't',
                        "stickWood"));
        if (ConfigItems.magneticForce) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.magneticForce),
                        "xex",
                        "xmx",
                        "xpx",
                        'e',
                        iEnderPearl,
                        'm',
                        "gemEmerald",
                        'p',
                        iPaper));
        if (ConfigItems.spiritBinder) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.spiritBinder),
                        "ses",
                        "ene",
                        "ses",
                        's',
                        iEctoplasm,
                        'e',
                        iEnderPearl,
                        'n',
                        iNetherstar));
        if (ConfigItems.soundRecorder) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.soundRecorder),
                        "xrx",
                        "wiw",
                        "wiw",
                        'r',
                        iRedstoneTorch,
                        'w',
                        "plankWood",
                        'i',
                        "ingotIron"));
        if (ConfigItems.biomeCapsule) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.biomeCapsule),
                        "dte",
                        "qgq",
                        "ooo",
                        'd',
                        "blockDiamond",
                        'e',
                        "gemEmerald",
                        't',
                        iTransformationCore,
                        'q',
                        "gemQuartz",
                        'g',
                        "blockGlass",
                        'o',
                        iObsidian));
        if (ConfigItems.biomePainter) GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.biomePainter),
                        "xtx",
                        "xwx",
                        "xox",
                        't',
                        iTransformationCore,
                        'w',
                        new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE),
                        'o',
                        "stickObsidian"));
        if (ConfigItems.ginto) {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(ModItems.ginto, 1, 0),
                            "ixi",
                            "xsx",
                            'i',
                            "ingotIron",
                            's',
                            iSpectreIron,
                            'e',
                            iEctoplasm));
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(ModItems.ginto, 1, 1),
                            "xex",
                            "xgx",
                            'e',
                            iEctoplasm,
                            'g',
                            new ItemStack(ModItems.ginto, 1, 0)));
        }

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.filter, 1, 0),
                        "xrx",
                        "rpr",
                        "xrx",
                        'r',
                        "dyeRed",
                        'p',
                        iPaper));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(ItemFilter.standardItemFilter, "xrx", "rpr", "xrx", 'r', "dyeYellow", 'p', iPaper));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.filter, 1, 2),
                        "xrx",
                        "rpr",
                        "xrx",
                        'r',
                        "dyeBlue",
                        'p',
                        iPaper));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.filter, 1, 3),
                        "xrx",
                        "rpr",
                        "xrx",
                        'r',
                        "dyePurple",
                        'p',
                        iPaper));

        addImbuingRecipes();
    }

    private static void addImbuingRecipes() {
        if (ConfigItems.imbue) {
            ImbuingRecipeHandler.addRecipe(iSpiderEye, iAllium, iRedMushroom, iWaterBottle, imbuePoison);
            ImbuingRecipeHandler.addRecipe(iDiamond, iEmerald, iGoldIngot, iWaterBottle, imbueExperience);
            ImbuingRecipeHandler.addRecipe(iFlint, iBlazeRod, iNetherBrick, iWaterBottle, imbueFire);
            ImbuingRecipeHandler.addRecipe(iWitherSkull, iBone, iCoal, iWaterBottle, imbueWither);
            ImbuingRecipeHandler.addRecipe(iRottenflesh, iQuartzBlock, iGhastTear, iWaterBottle, imbueWeakness);
            ImbuingRecipeHandler.addRecipe(iEctoplasm, iLapisBlock, iDaisy, iWaterBottle, imbueSpectre);
        }

        ImbuingRecipeHandler.addRecipe(
                new ItemStack(Items.gunpowder),
                new ItemStack(Items.glowstone_dust),
                new ItemStack(Items.redstone),
                iWaterBottle,
                new ItemStack(ModItems.ingredients, 1, 7));
    }

    private static void addArmorRecipes(ItemStack material, ItemStack helmet, ItemStack chestPlate, ItemStack leggings,
            ItemStack boots) {
        GameRegistry.addRecipe(helmet, "mmm", "mxm", 'm', material);
        GameRegistry.addRecipe(chestPlate, "mxm", "mmm", "mmm", 'm', material);
        GameRegistry.addRecipe(leggings, "mmm", "mxm", "mxm", 'm', material);
        GameRegistry.addRecipe(boots, "mxm", "mxm", 'm', material);
    }
}
