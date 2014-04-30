package lumien.randomthings.Items;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Entity.EntityWhitestone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemWhiteStone extends Item
{
	public ItemWhiteStone()
	{
		this.setUnlocalizedName("whitestone");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setTextureName("RandomThings:whitestone");
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);

		GameRegistry.registerItem(this, "whitestone");
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass)
	{
		return par1ItemStack.getItemDamage() == 1;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return stack.getItemDamage() == 0 && stack.stackTagCompound != null;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldObj, Entity entity, int par4, boolean par5)
	{
		if (stack.getItemDamage() == 0 && (entity instanceof EntityPlayer) && worldObj.getWorldTime() >= 18000 && worldObj.getWorldTime() <= 22000 && worldObj.canBlockSeeTheSky((int)Math.floor(entity.posX), (int)Math.floor(entity.posY), (int)Math.floor(entity.posZ)))
		{
			EntityPlayer player = (EntityPlayer) entity;

			if (stack.stackTagCompound == null)
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setInteger("charge", 0);
			}

			int charges = stack.stackTagCompound.getInteger("charge");

			stack.stackTagCompound.setInteger("charge", charges += 1);
			if (charges == 101)
			{
				stack.setItemDamage(1);
			}
		}
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		if (stack.getItemDamage() == 1)
		{
			return 0;
		}
		else
		{
			if (stack.stackTagCompound == null)
			{
				return 0;
			}
			else
			{
				return 1 - stack.stackTagCompound.getInteger("charge") / 100F;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
	{
		p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
		p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		switch (par1ItemStack.getItemDamage())
		{
			case 0:
				return "item.whitestoneUncharged";
			case 1:
				return "item.whitestoneCharged";
		}
		return "item.whitestoneUncharged";
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack)
	{
		return stack.getItemDamage() == 0;
	}
}
