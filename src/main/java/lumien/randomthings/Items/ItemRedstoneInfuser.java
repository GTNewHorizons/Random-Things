package lumien.randomthings.Items;

import java.util.ArrayList;

import javax.vecmath.Vector3d;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Handler.RedstoneHandler;
import lumien.randomthings.Library.WorldUtils;
import lumien.randomthings.Network.Packets.PacketInfusedBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class ItemRedstoneInfuser extends Item
{
	public ItemRedstoneInfuser()
	{
		this.setCreativeTab(RandomThings.creativeTab);
		this.setUnlocalizedName("redstoneInfuser");

		GameRegistry.registerItem(this, "redstoneInfuser");
	}

	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int posX, int posY, int posZ, int par7, float par8, float par9, float par10)
	{
		if (!par3World.isRemote)
		{
			if (par2EntityPlayer.isSneaking())
			{
				if (RedstoneHandler.isPowered(par3World.provider.dimensionId, posX, posY, posZ))
				{
					WorldUtils.dropItemStack(par3World, posX, posY, posZ, new ItemStack(Items.redstone));
					RedstoneHandler.removePoweredBlock(par3World.provider.dimensionId, posX, posY, posZ);
					par3World.notifyBlockOfNeighborChange(posX, posY, posZ, Blocks.diamond_block);
					return true;
				}
			}
			else
			{
				if (!par2EntityPlayer.capabilities.isCreativeMode)
				{
					if (!par2EntityPlayer.inventory.hasItem(Items.redstone))
					{
						return false;
					}
				}
				if (!RedstoneHandler.isPowered(par3World.provider.dimensionId, posX, posY, posZ))
				{
					RedstoneHandler.addPoweredBlock(par3World.provider.dimensionId, posX, posY, posZ);
					par3World.notifyBlockOfNeighborChange(posX, posY, posZ, Blocks.diamond_block);
					if (!par2EntityPlayer.capabilities.isCreativeMode)
					{
						par2EntityPlayer.inventory.consumeInventoryItem(Items.redstone);
						par2EntityPlayer.inventory.markDirty();
						par2EntityPlayer.inventoryContainer.detectAndSendChanges();
					}
					
					ArrayList<Vector3d> toAdd = new ArrayList<Vector3d>();
					toAdd.add(new Vector3d(posX,posY,posZ));
					
					RandomThings.packetPipeline.sendToDimension(new PacketInfusedBlocks(par3World.provider.dimensionId,false,toAdd),par3World.provider.dimensionId);
					
					return true;
				}
			}
		}
		return false;
	}
}
