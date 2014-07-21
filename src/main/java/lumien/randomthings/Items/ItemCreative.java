package lumien.randomthings.Items;

import java.util.List;

import lumien.randomthings.Client.ClientTickHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemCreative extends ItemBase
{
	
    public ItemCreative(String name)
	{
		super(name);
	}


	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("\u00A7dCreative only");
	}
	
	
	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return ClientTickHandler.getCurrentCreativeColor();
	}
}
