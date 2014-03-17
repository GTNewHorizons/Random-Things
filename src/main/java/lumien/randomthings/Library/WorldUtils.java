package lumien.randomthings.Library;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
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
