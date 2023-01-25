package lumien.randomthings.Handler;

import java.util.Random;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Configuration.VanillaChanges;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemDye;
import net.minecraft.util.ResourceLocation;

public class BackgroundHandler {

    static String[] validBlocks = new String[] {
        "netherrack",
        "wool_colored",
        "stonebrick",
        "stonebrick_mossy",
        "sponge",
        "stone",
        "red_sand",
        "sand",
        "bedrock",
        "brick",
        "clay",
        "cobblestone",
        "cobblestone_mossy",
        "dirt",
        "end_stone",
        "glowstone",
        "gravel",
        "hardened_clay",
        "hay_block_top",
        "ice",
        "log",
        "melon_side",
        "mycelium_top",
        "nether_brick",
        "portal"
    };

    static Random rng = new Random();

    static String[] logTypes = new String[] {"acacia", "big_oak", "birch", "jungle", "oak", "spruce"};

    public static void setBackgroundBlock(String block) {
        setBackground(new ResourceLocation("textures/blocks/" + block + ".png"));
    }

    public static void setBackground(ResourceLocation rl) {
        if (Gui.optionsBackground.getResourcePath().equals("textures/gui/options_background.png")) {
            Gui.optionsBackground = rl;
        }
    }

    public static void setRandomBackground() {
        if (VanillaChanges.MODIFIED_BACKGROUND) {
            if (!Settings.FIXED_BACKGROUND.equals("")) {
                setBackground(new ResourceLocation(Settings.FIXED_BACKGROUND));
            } else {
                String randomBlock = validBlocks[rng.nextInt(validBlocks.length)];

                switch (randomBlock) {
                    case "hardened_clay":
                        randomBlock = randomBlock + "_stained_"
                                + ItemDye.field_150921_b[rng.nextInt(ItemDye.field_150921_b.length)];
                        break;
                    case "wool_colored":
                        randomBlock =
                                randomBlock + "_" + ItemDye.field_150921_b[rng.nextInt(ItemDye.field_150921_b.length)];
                        break;
                    case "log":
                    case "planks":
                        randomBlock = randomBlock + "_" + logTypes[rng.nextInt(logTypes.length)];
                        break;
                }

                setBackgroundBlock(randomBlock);
            }
        }
    }
}
