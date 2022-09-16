package lumien.randomthings.Handler;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModCompHandler {
    public static void postInit() {
        // Obsidian Rods
        ArrayList<ItemStack> rods = OreDictionary.getOres("obsidianRod");

        for (ItemStack is : rods) {
            OreDictionary.registerOre("stickObsidian", is);
        }
    }
}
