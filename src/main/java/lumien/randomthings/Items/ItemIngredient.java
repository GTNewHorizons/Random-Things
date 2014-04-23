package lumien.randomthings.Items;

import java.util.ArrayList;
import java.util.List;

import lumien.randomthings.RandomThings;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

public class ItemIngredient extends Item
{
	private class Ingredient
	{
		IIcon icon;
		String iconName;
		String unlocalizedName;
		int maxStackSize;

		private Ingredient(String iconName, String unlocalizedName,int maxStackSize)
		{
			this.iconName = "RandomThings:crafting/" + iconName;
			this.unlocalizedName = unlocalizedName;
			this.maxStackSize = maxStackSize;
		}
	}

	ArrayList<Ingredient> ingredients;

	public ItemIngredient()
	{
		ingredients = new ArrayList<Ingredient>();
		
		ingredients.add(new Ingredient("playerCore", "playerCore",64));
		ingredients.add(new Ingredient("obsidianStick", "obsidianStick",64));
		ingredients.add(new Ingredient("enderFragment","enderFragment",16));
		
		OreDictionary.registerOre("stickObsidian", new ItemStack(this,1,1));
		OreDictionary.registerOre("obsidianStick", new ItemStack(this,1,1));
		
		this.setHasSubtypes(true);
		this.setCreativeTab(RandomThings.creativeTab);
		
		GameRegistry.registerItem(this, "ingredient");
	}
	
	
	
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int i=0;i<ingredients.size();i++)
        {
        	list.add(new ItemStack(this,1,i));
        }
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		if (ingredients.size() - 1 < damage || damage < 0)
		{
			return ingredients.get(0).icon;
		}
		else
		{
			return ingredients.get(damage).icon;
		}
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        for (Ingredient i:ingredients)
        {
        	i.icon = par1IconRegister.registerIcon(i.iconName);
        }
    }
    
    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
    	int damage = par1ItemStack.getItemDamage();
    	if (damage<0 || damage > ingredients.size()-1)
    	{
    		return "item.error";
    	}
        return "item." + ingredients.get(damage).unlocalizedName;
    }
    
    @Override
    public int getItemStackLimit(ItemStack is)
    {
    	int damage = is.getItemDamage();
    	if (ingredients.size() - 1 < damage || damage < 0)
    	{
    		return 64;
    	}
    	else
    	{
    		return ingredients.get(damage).maxStackSize;
    	}
    }
}
