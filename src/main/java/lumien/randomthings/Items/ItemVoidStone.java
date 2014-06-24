package lumien.randomthings.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemVoidStone extends Item
{
	public ItemVoidStone()
	{
		this.setUnlocalizedName("voidStone");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setMaxStackSize(1);
		this.setTextureName("RandomThings:voidstone");

		GameRegistry.registerItem(this, "voidStone");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par2World.isRemote)
		{
			par3EntityPlayer.openGui(RandomThings.instance, GuiIds.VOID_STONE, par2World, (int) par3EntityPlayer.posX, (int) par3EntityPlayer.posY, (int) par3EntityPlayer.posZ);
		}

		return par1ItemStack;
	}
}
