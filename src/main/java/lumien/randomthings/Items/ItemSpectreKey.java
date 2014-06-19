package lumien.randomthings.Items;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Entity.EntitySpirit;
import lumien.randomthings.Handler.Spectre.TeleporterSpectre;
import lumien.randomthings.Library.WorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemSpectreKey extends Item
{
	IIcon empty;

	public ItemSpectreKey()
	{
		this.setUnlocalizedName("spectreKey");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setFull3D();
		this.setMaxStackSize(1);
		
		GameRegistry.registerItem(this, "spectreKey");
		this.setTextureName("RandomThings:spectreKey");
	}

	
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		return 16777215;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		if (player.worldObj.isRemote && count < 60)
		{	
			EntityFX particle;
			if (Minecraft.getMinecraft().thePlayer == player)
			{
				float t = 1F / 255F;
				for (int i = 0; i < (60 - count); i++)
				{
					particle = new EntitySmokeFX(player.worldObj, player.posX + Math.random() * 2 - 1, player.posY - 1 + Math.random(), player.posZ + Math.random() * 2 - 1, 0, 0, 0);

					particle.setRBGColorF(t * 152F, t * 245F, t * 255F);
					Minecraft.getMinecraft().effectRenderer.addEffect(particle);
				}
			}
			else
			{
				for (int i = 0; i < (60 - count); i++)
				{
					particle = new EntitySmokeFX(player.worldObj, player.posX + Math.random() * 2 - 1, player.posY + 0.5 + Math.random(), player.posZ + Math.random() * 2 - 1, 0, 0, 0);
					particle.setRBGColorF(1, 1, 1);
					Minecraft.getMinecraft().effectRenderer.addEffect(particle);
				}
			}
		}
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par2World.isRemote)
		{
			if (par2World.provider.dimensionId != Settings.SPECTRE_DIMENSON_ID)
			{
				RandomThings.instance.spectreHandler.teleportPlayerToSpectreWorld((EntityPlayerMP) par3EntityPlayer);
			}
			else
			{
				RandomThings.instance.spectreHandler.teleportPlayerOutOfSpectreWorld((EntityPlayerMP) par3EntityPlayer);
			}
		}
		return par1ItemStack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack, int pass)
	{
		return Minecraft.getMinecraft().thePlayer.worldObj.provider.dimensionId == Settings.SPECTRE_DIMENSON_ID;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 100;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	
	public void registerIcons(IIconRegister ir)
	{
		super.registerIcons(ir);
		this.empty = ir.registerIcon("RandomThings:empty");
	}
}
