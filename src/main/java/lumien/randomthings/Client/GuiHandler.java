package lumien.randomthings.Client;

import lumien.randomthings.Items.ItemFilter;
import lumien.randomthings.Client.GUI.GuiItemCollector;
import lumien.randomthings.Client.GUI.GuiItemFilter;
import lumien.randomthings.Client.GUI.GuiPlayerInterface;
import lumien.randomthings.Container.ContainerItemCollector;
import lumien.randomthings.Container.ContainerItemFilter;
import lumien.randomthings.Container.ContainerPlayerInterface;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
import lumien.randomthings.TileEntities.TileEntityPlayerInterface;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
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
				return new ContainerPlayerInterface((TileEntityPlayerInterface)tileEntity);
			case ITEM_FILTER:
				IInventory inventoryFilter = ItemFilter.getItemFilterInv(player);
				if (inventoryFilter==null)
				{
					inventoryFilter = new InventoryBasic("placeholder",false,9);
				}
				return new ContainerItemFilter(player.inventory,inventoryFilter);
			case ADVANCED_ITEMCOLLECTOR:
				return new ContainerItemCollector(player.inventory,(TileEntityAdvancedItemCollector)tileEntity);
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
				return new GuiPlayerInterface((TileEntityPlayerInterface)tileEntity);
			case ITEM_FILTER:
				IInventory inventoryFilter = ItemFilter.getItemFilterInv(player);
				if (inventoryFilter == null)
				{
					inventoryFilter = new InventoryBasic("placeholder",false,27);
				}
				return new GuiItemFilter(player.inventory,inventoryFilter);
			case ADVANCED_ITEMCOLLECTOR:
				return new GuiItemCollector(player.inventory,(TileEntityAdvancedItemCollector)tileEntity);
		}
		return null;
	}
}
