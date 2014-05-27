package lumien.randomthings.Library;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class WorldUtils
{
	public static void dropItemStack(World world, double x, double y, double z, ItemStack stack)
	{
		if (!world.isRemote)
		{
			float f = 0.7F;
			double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, stack);
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}
	}

	public static void notifyStrong(World worldObj, int posX, int posY, int posZ, Block block)
	{
		worldObj.notifyBlocksOfNeighborChange(posX, posY - 1, posZ, block);
		worldObj.notifyBlocksOfNeighborChange(posX, posY + 1, posZ, block);
		worldObj.notifyBlocksOfNeighborChange(posX - 1, posY, posZ, block);
		worldObj.notifyBlocksOfNeighborChange(posX + 1, posY, posZ, block);
		worldObj.notifyBlocksOfNeighborChange(posX, posY, posZ - 1, block);
		worldObj.notifyBlocksOfNeighborChange(posX, posY, posZ + 1, block);
	}

	public static void generateCube(World worldObj, int posX1, int posY1, int posZ1, int posX2, int posY2, int posZ2, Block b)
	{
		int minX = Math.min(posX1, posX2);
		int minY = Math.min(posY1, posY2);
		int minZ = Math.min(posZ1, posZ2);

		int maxX = Math.max(posX1, posX2);
		int maxY = Math.max(posY1, posY2);
		int maxZ = Math.max(posZ1, posZ2);
		
		for (int x=minX;x<=maxX;x++)
		{
			for (int y=minY;y<=maxY;y++)
			{
				for (int z=minZ;z<=maxZ;z++)
				{
					if (x==minX || y==minY || z==minZ || x==maxX || y==maxY || z==maxZ)
					{
						worldObj.setBlock(x, y, z, b);
					}
				}
			}
		}
	}

	public static boolean isPlayerOnline(String username)
	{
		if (FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			return MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username) != null;
		}
		else
		{
			NetHandlerPlayClient netclienthandler = Minecraft.getMinecraft().thePlayer.sendQueue;
			List list = netclienthandler.playerInfoList;

			for (int i = 0; i < list.size(); i++)
			{
				GuiPlayerInfo guiplayerinfo = (GuiPlayerInfo) list.get(i);
				if (guiplayerinfo.name.toLowerCase().equals(username.toLowerCase()))
				{
					return true;
				}
			}
			return false;
		}
	}
}
