package lumien.randomthings.Library;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.Configuration.ConfigItems;
import lumien.randomthings.Items.ModItems;

public class Recipes
{
	public static void init()
	{
		ItemStack iObsidian = new ItemStack(Block.getBlockFromName("obsidian"));
		ItemStack iEnderChest = new ItemStack(net.minecraft.init.Blocks.ender_chest);
		ItemStack iNetherstar = new ItemStack(Items.nether_star);
		ItemStack iEmerald = new ItemStack(Items.emerald);
		ItemStack iGlassPane = new ItemStack(Blocks.glass_pane);
		ItemStack iGlassBottle = new ItemStack(Items.glass_bottle);
		ItemStack iLapislazuli = new ItemStack(Items.dye,1,4);
		ItemStack iDirt = new ItemStack(Blocks.dirt);
		ItemStack iBonemeal = new ItemStack(Items.dye,1,15);
		ItemStack iRottenflesh = new ItemStack(Items.rotten_flesh);
		ItemStack iHopper = new ItemStack(Blocks.hopper);
		ItemStack iRedstone = new ItemStack(Items.redstone);
		ItemStack iEnderPearl = new ItemStack(Items.ender_pearl);
		ItemStack iGlowstone = new ItemStack(Items.glowstone_dust);
		ItemStack iDiamond = new ItemStack(Items.diamond);
		ItemStack iPaper = new ItemStack(Items.paper);
		ItemStack iStone = new ItemStack(Blocks.stone);
		ItemStack iRedstoneTorch = new ItemStack(Blocks.redstone_torch);
		ItemStack iRedstoneRepeater = new ItemStack(Items.repeater);
		
		ItemStack iPlayerCore = new ItemStack(ModItems.ingredients,1,0);
		ItemStack iObsidianStick = new ItemStack(ModItems.ingredients,1,1);
		
		// Crafting Items
		GameRegistry.addRecipe(new ShapedOreRecipe(iPlayerCore,"xlx","lel","xlx",'l',iLapislazuli,'e',iEmerald));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ingredients,3,1), "o","o",'o',iObsidian));
		
		if (ConfigBlocks.playerInterface)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.playerInterface),"oeo","omo","ono",'o',iObsidian,'e',iEnderChest,'m',iPlayerCore,'n',iNetherstar));
		if (ConfigBlocks.fluidDisplay)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fluidDisplay,8),"ggg","gbg","ggg",'g',iGlassPane,'b',iGlassBottle));
		if (ConfigBlocks.fertilizedDirt)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fertilizedDirt,1),"brb","rdr","brb",'b',iBonemeal,'r',iRottenflesh,'d',iDirt));
		if (ConfigBlocks.itemCollector)
		{
			ItemStack iItemCollector = new ItemStack(ModBlocks.itemCollector);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.itemCollector,1),"xhx","rer","xox",'h',iHopper,'r',iRedstone,'e',iEnderPearl,'o',"stickObsidian"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.advancedItemCollector,1),"xdx","gig",'d',iDiamond,'g',iGlowstone,'i',iItemCollector));
		}
		if (ConfigBlocks.onlineDetector)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.onlineDetector,1),"sts","rer","sts",'s',iStone,'t',iRedstoneTorch,'r',iRedstone,'e',iLapislazuli));
		if (ConfigItems.voidStone)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.voidStone,1),"xox","oeo","xox",'o',iObsidian,'e',iEnderPearl));
		if (ConfigItems.filter)
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.filter,1,0),"xrx","rpr","xrx",'r',"dyeRed",'p',iPaper));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.filter,1,1),"xrx","rpr","xrx",'r',"dyeYellow",'p',iPaper));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.filter,1,2),"xrx","rpr","xrx",'r',"dyeBlue",'p',iPaper));
		}
	}
}
