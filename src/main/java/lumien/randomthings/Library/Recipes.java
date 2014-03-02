package lumien.randomthings.Library;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Configuration.ConfigBlocks;

public class Recipes
{
	public static void init()
	{
		ItemStack obsidian = new ItemStack(Block.getBlockFromName("obsidian"));
		ItemStack enderchest = new ItemStack(net.minecraft.init.Blocks.ender_chest);
		ItemStack netherstar = new ItemStack(Items.nether_star);
		ItemStack emerald = new ItemStack(Items.emerald);
		ItemStack glassPane = new ItemStack(Blocks.glass_pane);
		ItemStack glassBottle = new ItemStack(Items.glass_bottle);
		
		if (ConfigBlocks.playerInterface)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.playerInterface),"oeo","omo","ono",'o',obsidian,'e',enderchest,'m',emerald,'n',netherstar));
		if (ConfigBlocks.fluidDisplay)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fluidDisplay,8),"ggg","gbg","ggg",'g',glassPane,'b',glassBottle));
			
	
	}
}
