package lumien.randomthings.Items;

import java.util.List;

import lumien.randomthings.Client.ClientTickHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCreative extends Item
{
	
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("\u00A7dCreative only");
	}
	
	
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return ClientTickHandler.getCurrentCreativeColor();
	}
}
