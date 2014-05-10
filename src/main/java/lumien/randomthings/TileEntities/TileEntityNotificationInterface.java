package lumien.randomthings.TileEntities;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.registry.GameData;
import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.SimpleComponent;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.WorldUtils;
import lumien.randomthings.Network.Packets.PacketNotification;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.block.Block;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

@Optional.InterfaceList(value = { @Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers"), @Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft") })
public class TileEntityNotificationInterface extends TileEntity implements IPeripheral, SimpleComponent
{

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName()
	{
		return "notificationInterface";
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public String getType()
	{
		return "notificationInterface";
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public String[] getMethodNames()
	{
		return new String[] { "sendNotification" };
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] sendNotification(Context context, Arguments args) throws Exception
	{
		String receiver = args.checkString(0);
		String title = args.checkString(1);
		String description = args.checkString(2);
		String iconString = args.checkString(3);
		
		if (!WorldUtils.isPlayerOnline(receiver))
		{
			throw new Exception("Selected Receiver is not Online");
		}

		int metadata = 0;

		if (args.count() > 4)
		{
			metadata = args.checkInteger(4);
		}
		Item i = GameData.getItemRegistry().getObject(iconString);
		Block b = GameData.getBlockRegistry().getObject(iconString);

		ItemStack is = null;

		if (i != null)
		{
			is = new ItemStack(i, 1, metadata);
		}
		else if (b != null)
		{
			is = new ItemStack(b, 1, metadata);
		}
		else
		{
			throw new Exception("Invalid IconString");
		}

		EntityPlayerMP receiverEntity = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(receiver);
		if (receiverEntity == null)
		{
			return new Object[] {};
		}

		PacketNotification packet = new PacketNotification(title, description, is);

		RandomThings.packetPipeline.sendTo(packet, receiverEntity);

		return new Object[] {};
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception
	{
		switch (method)
		{
			case 0:
				if (arguments.length < 4)
				{
					throw new Exception("Usage: sendNotification(Receiver,Title,Description,IconString)");
				}
				else
				{
					String receiver = arguments[0] + "";
					String title = arguments[1] + "";
					String description = arguments[2] + "";
					String iconString = arguments[3] + "";

					int metadata = 0;

					if (arguments.length > 4)
					{
						metadata = (int) Double.parseDouble(arguments[4] + "");
					}

					if (!WorldUtils.isPlayerOnline(receiver))
					{
						throw new Exception("Selected Receiver is not Online");
					}
					else
					{
						Item i = GameData.getItemRegistry().getObject(iconString);
						Block b = GameData.getBlockRegistry().getObject(iconString);

						ItemStack is = null;

						if (i != null)
						{
							is = new ItemStack(i, 1, metadata);
						}
						else if (b != null)
						{
							is = new ItemStack(b, 1, metadata);
						}
						else
						{
							throw new Exception("Invalid IconString");
						}

						EntityPlayerMP receiverEntity = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(receiver);
						if (receiverEntity == null)
						{
							throw new Exception("Player entity not found");
						}

						PacketNotification packet = new PacketNotification(title, description, is);

						RandomThings.packetPipeline.sendTo(packet, receiverEntity);
						return null;
					}
				}
		}
		throw new Exception("Usage: sendNotification(Receiver,Title,Description,IconString)");
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public void attach(IComputerAccess computer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public void detach(IComputerAccess computer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public boolean equals(IPeripheral other)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
