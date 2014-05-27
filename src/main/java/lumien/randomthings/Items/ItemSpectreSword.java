package lumien.randomthings.Items;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.ChatColors;
import lumien.randomthings.Library.PotionEffects;
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

	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		super.hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
		par2EntityLivingBase.addPotionEffect(new PotionEffect(PotionEffects.SLOWNESS,40,2,false));
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add(ChatColors.AQUA + "Soul Solid");
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		return 16777215;
	}
}
