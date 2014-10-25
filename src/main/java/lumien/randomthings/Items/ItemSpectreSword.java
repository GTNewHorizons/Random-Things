package lumien.randomthings.Items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Library.Colors;
import lumien.randomthings.Library.PotionEffects;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;

public class ItemSpectreSword extends ItemSword
{
	public final static Item.ToolMaterial spectreToolMaterial = EnumHelper.addToolMaterial("Spectre", 3, 1700, 8.2f, 4f, 16);

	public ItemSpectreSword()
	{
		super(spectreToolMaterial);

		this.setUnlocalizedName("spectreSword");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setTextureName("RandomThings:spectreSword");

		GameRegistry.registerItem(this, "spectreSword");
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase hitEntity, EntityLivingBase hittingEntity)
	{
		super.hitEntity(par1ItemStack, hitEntity, hittingEntity);
		hitEntity.addPotionEffect(new PotionEffect(PotionEffects.SLOWNESS, 40, 2, false));
		return true;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add(Colors.AQUA + I18n.format("text.spectre.soulSolid"));
	}

	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		RenderUtils.enableDefaultBlending();
		return 16777215;
	}
}
