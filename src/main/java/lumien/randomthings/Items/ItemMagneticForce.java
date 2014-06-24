package lumien.randomthings.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMagneticForce extends Item
{
	double r=0;
	public ItemMagneticForce()
	{
		this.setUnlocalizedName("magneticForce");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setTextureName("RandomThings:magneticForce");

		GameRegistry.registerItem(this, "magneticForce");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par2World.isRemote)
		{
			par3EntityPlayer.openGui(RandomThings.instance, GuiIds.MAGNETIC_FORCE, par2World, (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ);
		}
		return par1ItemStack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		EntityPlayer player = (EntityPlayer) par3Entity;
	}
}
