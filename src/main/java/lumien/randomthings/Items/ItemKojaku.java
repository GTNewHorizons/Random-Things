package lumien.randomthings.Items;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Entity.EntityPfeil;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class ItemKojaku extends Item
{
	public static final String[] kojakuPullIconNameArray = new String[] { "pulling_0", "pulling_1", "pulling_2" };
	
	private IIcon[] iconArray;

	public ItemKojaku()
	{
		this.setUnlocalizedName("kojaku");
		this.setCreativeTab(RandomThings.creativeTab);

		GameRegistry.registerItem(this, "kojaku");
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		int j = this.getMaxItemUseDuration(par1ItemStack) - par4;

		ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return;
		}
		j = event.charge;
		float f = j / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f < 0.1D)
		{
			return;
		}

		if (f > 1.0F)
		{
			f = 1.0F;
		}

		EntityPfeil entityarrow = new EntityPfeil(par2World, par3EntityPlayer, f * 2.0F);

		if (f == 1.0F)
		{
			entityarrow.setIsCritical(true);
		}

		int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

		if (k > 0)
		{
			entityarrow.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);
		}

		int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

		if (l > 0)
		{
			entityarrow.setKnockbackStrength(l);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
		{
			entityarrow.setFire(100);
		}

		par1ItemStack.damageItem(1, par3EntityPlayer);
		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

		if (!par2World.isRemote)
		{
			par2World.spawnEntityInWorld(entityarrow);
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}

	@Override
	
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon("RandomThings:kojaku/kojaku" + "_standby");
		this.iconArray = new IIcon[kojakuPullIconNameArray.length];

		for (int i = 0; i < this.iconArray.length; ++i)
		{
			this.iconArray[i] = par1IconRegister.registerIcon("RandomThings:kojaku/kojaku" + "_" + kojakuPullIconNameArray[i]);
		}
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		if (player.getItemInUse() == null)
			return this.itemIcon;
		int pulling = stack.getMaxItemUseDuration() - useRemaining;
		if (pulling >= 18)
		{
			return iconArray[2];
		}
		else if (pulling > 13)
		{
			return iconArray[1];
		}
		else if (pulling > 0)
		{
			return iconArray[0];
		}
		return itemIcon;
	}
}
