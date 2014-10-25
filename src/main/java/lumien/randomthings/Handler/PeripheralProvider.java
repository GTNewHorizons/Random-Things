package lumien.randomthings.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lumien.randomthings.TileEntities.TileEntityCreativePlayerInterface;
import lumien.randomthings.TileEntities.TileEntityNotificationInterface;
import lumien.randomthings.TileEntities.TileEntityOnlineDetector;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

@Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheralProvider", modid = "ComputerCraft")
public class PeripheralProvider implements IPeripheralProvider
{
	public static void register() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		if (Loader.isModLoaded("ComputerCraft"))
		{
			Class clazz = Class.forName("dan200.computercraft.api.ComputerCraftAPI");

			if (clazz != null)
			{
				Method m = clazz.getDeclaredMethod("registerPeripheralProvider", Class.forName("dan200.computercraft.api.peripheral.IPeripheralProvider"));
				m.invoke(null, new PeripheralProvider());
			}
		}
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public IPeripheral getPeripheral(World world, int x, int y, int z, int side)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityCreativePlayerInterface || te instanceof TileEntityOnlineDetector || te instanceof TileEntityNotificationInterface)
		{
			return (IPeripheral) te;
		}
		return null;
	}

}
