package lumien.randomthings.Items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemBloodstone extends ItemBase
{
	public static final float MAX_CHARGES = 400;

	public ItemBloodstone()
	{
		super("bloodStone");

		this.setMaxDamage((int) MAX_CHARGES);
		this.setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List itemList)
	{
		super.getSubItems(item, creativeTab, itemList);
		
		ItemStack full = new ItemStack(this,1,0);
		full.stackTagCompound = new NBTTagCompound();
		full.stackTagCompound.setInteger("charges", (int) MAX_CHARGES);
		itemList.add(full);
	}

	@Override
	public void onUpdate(ItemStack is, World worldObj, Entity entity, int p_77663_4_, boolean p_77663_5_)
	{
		super.onUpdate(is, worldObj, entity, p_77663_4_, p_77663_5_);

		int charges;
		if (is.stackTagCompound == null)
		{
			charges = 0;
		}
		else
		{
			charges = is.stackTagCompound.getInteger("charges");
		}
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			int rate = -1;
			if (charges <= 200)
			{
				rate = (int) (200 - 180F / 200 * charges);
			}
			else
			{
				rate = (int) (20F - ((charges - 200) * 10F / 200F));
			}

			if (worldObj.getTotalWorldTime() % rate == 0)
			{
				player.heal(1);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List informationList, boolean extendedInformation)
	{
		int charges;
		if (is.stackTagCompound == null)
		{
			charges = 0;
		}
		else
		{
			charges = is.stackTagCompound.getInteger("charges");
		}

		informationList.add(I18n.format("text.miscellaneous.charges", charges));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack, int pass)
	{
		return par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.getInteger("charges") == MAX_CHARGES;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		if (stack.stackTagCompound == null)
		{
			return 1;
		}
		else
		{
			return 1 - stack.stackTagCompound.getInteger("charges") / MAX_CHARGES;
		}
	}

	@Override
	public boolean showDamage(ItemStack item)
	{
		if (item.stackTagCompound == null)
		{
			return true;
		}
		else
		{
			return item.stackTagCompound.getInteger("charges") < MAX_CHARGES;
		}
	}

	@Override
	public int getDurabilityColor(ItemStack item, double health)
	{
		return 13107200;
	}
}
