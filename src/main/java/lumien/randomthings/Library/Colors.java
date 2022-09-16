package lumien.randomthings.Library;

import java.awt.Color;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Colors {
    public static final String BLACK = "\247" + "0";
    public static final String DARK_BLUE = "\247" + "1";
    public static final String DARK_GREEN = "\247" + "2";
    public static final String DARK_AQUA = "\247" + "3";
    public static final String DARK_RED = "\247" + "4";
    public static final String DARK_PURPLE = "\247" + "5";
    public static final String GOLD = "\247" + "6";
    public static final String GRAY = "\247" + "7";
    public static final String DARK_GRAY = "\247" + "8";
    public static final String BLUE = "\247" + "9";

    public static final String GREEN = "\247" + "a";
    public static final String AQUA = "\247" + "b";
    public static final String RED = "\247" + "c";
    public static final String LIGHT_PURPLE = "\247" + "d";
    public static final String YELLOW = "\247" + "e";
    public static final String WHITE = "\247" + "f";

    public static final Color[] dyeColors = new Color[ItemDye.field_150922_c.length];

    {
    }

    public static final String[] oreDictDyes = new String[] {
        "dyeBlack",
        "dyeRed",
        "dyeGreen",
        "dyeBrown",
        "dyeBlue",
        "dyePurple",
        "dyeCyan",
        "dyeLightGray",
        "dyeGray",
        "dyePink",
        "dyeLime",
        "dyeYellow",
        "dyeLightBlue",
        "dyeMagenta",
        "dyeOrange",
        "dyeWhite"
    };

    public static int getDyeColor(ItemStack is) {
        int[] oreDictIds = OreDictionary.getOreIDs(is);
        for (int i : oreDictIds) {
            for (int dyeO = 0; dyeO < Colors.oreDictDyes.length; dyeO++) {
                String s = Colors.oreDictDyes[dyeO];
                int dyeID = OreDictionary.getOreID(s);
                if (dyeID == i) {
                    return ItemDye.field_150922_c[dyeO];
                }
            }
        }

        return 0;
    }

    public static int getDye(ItemStack is) {
        int[] oreDictIds = OreDictionary.getOreIDs(is);
        for (int i : oreDictIds) {
            for (int dyeO = 0; dyeO < Colors.oreDictDyes.length; dyeO++) {
                String s = Colors.oreDictDyes[dyeO];
                int dyeID = OreDictionary.getOreID(s);
                if (dyeID == i) {
                    return dyeO;
                }
            }
        }
        return -1;
    }
}
