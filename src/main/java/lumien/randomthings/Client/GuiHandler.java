package lumien.randomthings.Client;

import lumien.randomthings.Items.ItemDropFilter;
import lumien.randomthings.Items.ItemFilter;
import lumien.randomthings.Items.ItemEnderLetter;
import lumien.randomthings.Client.GUI.GuiCarpentryBench;
import lumien.randomthings.Client.GUI.GuiDropFilter;
import lumien.randomthings.Client.GUI.GuiItemCollector;
import lumien.randomthings.Client.GUI.GuiItemFilter;
import lumien.randomthings.Client.GUI.GuiEnderLetter;
import lumien.randomthings.Client.GUI.GuiMagneticForce;
import lumien.randomthings.Client.GUI.GuiOnlineDetector;
import lumien.randomthings.Client.GUI.GuiPlayerInterface;
import lumien.randomthings.Client.GUI.GuiSoundRecorder;
import lumien.randomthings.Client.GUI.GuiVoidStone;
import lumien.randomthings.Container.ContainerCarpentryBench;
import lumien.randomthings.Container.ContainerDropFilter;
import lumien.randomthings.Container.ContainerItemCollector;
import lumien.randomthings.Container.ContainerItemFilter;
import lumien.randomthings.Container.ContainerEnderLetter;
import lumien.randomthings.Container.ContainerMagneticForce;
import lumien.randomthings.Container.ContainerOnlineDetector;
import lumien.randomthings.Container.ContainerPlayerInterface;
import lumien.randomthings.Container.ContainerSoundRecorder;
import lumien.randomthings.Container.ContainerVoidStone;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
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
			case VOID_STONE:
				return new ContainerVoidStone(player.inventory);
			case DROP_FILTER:
				IInventory inventoryDropFilter = ItemDropFilter.getDropFilterInv(player);
				if (inventoryDropFilter == null)
				{
					inventoryDropFilter = new InventoryBasic("placeholder", false, 1);
				}
				return new ContainerDropFilter(player.inventory, inventoryDropFilter);
			case ENDER_LETTER:
				IInventory letterInventory = ItemEnderLetter.getLetterInventory(player);
				if (letterInventory == null)
				{
					letterInventory = new InventoryBasic("placeholder", false, 9);
				}
				return new ContainerEnderLetter(player.getCurrentEquippedItem(),player.inventory, letterInventory);
			case SOUND_RECORDER:
				return new ContainerSoundRecorder();
			case MAGNETIC_FORCE:
				return new ContainerMagneticForce();
			case CARPENTRY_BENCH:
				return new ContainerCarpentryBench(player.inventory,world,x,y,z);
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
				return new GuiItemFilter(player, player.inventory, inventoryFilter);
			case ADVANCED_ITEMCOLLECTOR:
				return new GuiItemCollector(player.inventory, (TileEntityAdvancedItemCollector) tileEntity);
			case ONLINE_DETECTOR:
				return new GuiOnlineDetector((TileEntityOnlineDetector) tileEntity);
			case VOID_STONE:
				return new GuiVoidStone(player.inventory);
			case DROP_FILTER:
				IInventory inventoryDropFilter = ItemDropFilter.getDropFilterInv(player);
				if (inventoryDropFilter == null)
				{
					inventoryDropFilter = new InventoryBasic("placeholder", false, 1);
				}
				return new GuiDropFilter(player.inventory, inventoryDropFilter);
			case ENDER_LETTER:
				IInventory letterInventory = ItemFilter.getItemFilterInv(player);
				if (letterInventory == null)
				{
					letterInventory = new InventoryBasic("placeholder", false, 9);
				}
				return new GuiEnderLetter(player.inventory, letterInventory,player.getCurrentEquippedItem());
			case SOUND_RECORDER:
				return new GuiSoundRecorder();
			case MAGNETIC_FORCE:
				return new GuiMagneticForce();
			case CARPENTRY_BENCH:
				return new GuiCarpentryBench(player.inventory,world,x,y,z);
		}
		return null;
	}
}
