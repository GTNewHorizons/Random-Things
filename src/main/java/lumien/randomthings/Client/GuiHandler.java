package lumien.randomthings.Client;

import lumien.randomthings.Items.ItemDropFilter;
import lumien.randomthings.Items.ItemFilter;
import lumien.randomthings.Client.GUI.GuiDropFilter;
import lumien.randomthings.Client.GUI.GuiItemCollector;
import lumien.randomthings.Client.GUI.GuiItemFilter;
import lumien.randomthings.Client.GUI.GuiFluidRouter;
import lumien.randomthings.Client.GUI.GuiOnlineDetector;
import lumien.randomthings.Client.GUI.GuiPlayerInterface;
import lumien.randomthings.Client.GUI.GuiVoidStone;
import lumien.randomthings.Container.ContainerDropFilter;
import lumien.randomthings.Container.ContainerItemCollector;
import lumien.randomthings.Container.ContainerItemFilter;
import lumien.randomthings.Container.ContainerLiquidRouter;
import lumien.randomthings.Container.ContainerOnlineDetector;
import lumien.randomthings.Container.ContainerPlayerInterface;
import lumien.randomthings.Container.ContainerVoidStone;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
import lumien.randomthings.TileEntities.TileEntityFluidRouter;
import lumien.randomthings.TileEntities.TileEntityOnlineDetector;
import lumien.randomthings.TileEntities.TileEntityPlayerInterface;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import static lumien.randomthings.Library.GuiIds.*;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		switch (ID)
		{
			case PLAYER_INTERFACE:
				return new ContainerPlayerInterface((TileEntityPlayerInterface) tileEntity);
			case ITEM_FILTER:
				IInventory inventoryFilter = ItemFilter.getItemFilterInv(player);
				if (inventoryFilter == null)
				{
					inventoryFilter = new InventoryBasic("placeholder", false, 9);
				}
				return new ContainerItemFilter(player.inventory, inventoryFilter);
			case ADVANCED_ITEMCOLLECTOR:
				return new ContainerItemCollector(player.inventory, (TileEntityAdvancedItemCollector) tileEntity);
			case ONLINE_DETECTOR:
				return new ContainerOnlineDetector();
			case FLUID_ROUTER:
				return new ContainerLiquidRouter(player.inventory, (TileEntityFluidRouter) tileEntity);
			case VOID_STONE:
				return new ContainerVoidStone(player.inventory);
			case DROP_FILTER:
				IInventory inventoryDropFilter = ItemDropFilter.getDropFilterInv(player);
				if (inventoryDropFilter == null)
				{
					inventoryDropFilter = new InventoryBasic("placeholder", false, 1);
				}
				return new ContainerDropFilter(player.inventory, inventoryDropFilter);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		switch (ID)
		{
			case PLAYER_INTERFACE:
				return new GuiPlayerInterface((TileEntityPlayerInterface) tileEntity);
			case ITEM_FILTER:
				IInventory inventoryFilter = ItemFilter.getItemFilterInv(player);
				if (inventoryFilter == null)
				{
					inventoryFilter = new InventoryBasic("placeholder", false, 27);
				}
				return new GuiItemFilter(player,player.inventory, inventoryFilter);
			case ADVANCED_ITEMCOLLECTOR:
				return new GuiItemCollector(player.inventory, (TileEntityAdvancedItemCollector) tileEntity);
			case ONLINE_DETECTOR:
				return new GuiOnlineDetector((TileEntityOnlineDetector) tileEntity);
			case FLUID_ROUTER:
				return new GuiFluidRouter(player.inventory, (TileEntityFluidRouter) tileEntity);
			case VOID_STONE:
				return new GuiVoidStone(player.inventory);
			case DROP_FILTER:
				IInventory inventoryDropFilter = ItemDropFilter.getDropFilterInv(player);
				if (inventoryDropFilter == null)
				{
					inventoryDropFilter = new InventoryBasic("placeholder", false, 1);
				}
				return new GuiDropFilter(player.inventory, inventoryDropFilter);
		}
		return null;
	}
}
