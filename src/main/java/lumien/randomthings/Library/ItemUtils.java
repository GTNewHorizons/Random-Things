package lumien.randomthings.Library;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtils {
    public static boolean areOreDictionaried(ItemStack item1, ItemStack item2) {
        ArrayList<Integer> ids1 = getOreIDs(item1);
        ArrayList<Integer> ids2 = getOreIDs(item2);

        if (ids1 == null || ids2 == null) {
            return false;
        }

        for (Integer id1 : ids1) {
            for (Integer id2 : ids2) {
                if (id1.equals(id2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ArrayList<Integer> getOreIDs(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        ArrayList<Integer> oreIDs = new ArrayList<Integer>();

        for (int id : OreDictionary.getOreIDs(itemStack)) {
            oreIDs.add(id);
        }
        return oreIDs;
    }
}
