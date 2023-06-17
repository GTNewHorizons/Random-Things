package lumien.randomthings.Client;

import static lumien.randomthings.Library.GuiIds.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;
import lumien.randomthings.Client.GUI.GuiDropFilter;
import lumien.randomthings.Client.GUI.GuiDyeingMachine;
import lumien.randomthings.Client.GUI.GuiEnderEnergyDistributor;
import lumien.randomthings.Client.GUI.GuiEnderLetter;
import lumien.randomthings.Client.GUI.GuiEnergyDistributor;
import lumien.randomthings.Client.GUI.GuiImbuingStation;
import lumien.randomthings.Client.GUI.GuiItemCollector;
import lumien.randomthings.Client.GUI.GuiItemFilter;
import lumien.randomthings.Client.GUI.GuiMagneticForce;
import lumien.randomthings.Client.GUI.GuiOnlineDetector;
import lumien.randomthings.Client.GUI.GuiOpSpectreKey;
import lumien.randomthings.Client.GUI.GuiPlayerInterface;
import lumien.randomthings.Client.GUI.GuiSoundRecorder;
import lumien.randomthings.Client.GUI.GuiVoidStone;
import lumien.randomthings.Container.ContainerDropFilter;
import lumien.randomthings.Container.ContainerDyeingMachine;
import lumien.randomthings.Container.ContainerEnderEnergyDistributor;
import lumien.randomthings.Container.ContainerEnderLetter;
import lumien.randomthings.Container.ContainerEnergyDistributor;
import lumien.randomthings.Container.ContainerImbuingStation;
import lumien.randomthings.Container.ContainerItemCollector;
import lumien.randomthings.Container.ContainerItemFilter;
import lumien.randomthings.Container.ContainerMagneticForce;
import lumien.randomthings.Container.ContainerOnlineDetector;
import lumien.randomthings.Container.ContainerOpSpectreKey;
import lumien.randomthings.Container.ContainerPlayerInterface;
import lumien.randomthings.Container.ContainerSoundRecorder;
import lumien.randomthings.Container.ContainerVoidStone;
import lumien.randomthings.Items.ItemDropFilter;
import lumien.randomthings.Items.ItemEnderLetter;
import lumien.randomthings.Items.ItemFilter;
import lumien.randomthings.TileEntities.EnergyDistributors.TileEntityEnderEnergyDistributor;
import lumien.randomthings.TileEntities.EnergyDistributors.TileEntityEnergyDistributor;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
import lumien.randomthings.TileEntities.TileEntityImbuingStation;
import lumien.randomthings.TileEntities.TileEntityOnlineDetector;
import lumien.randomthings.TileEntities.TileEntityPlayerInterface;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        switch (ID) {
            case PLAYER_INTERFACE:
                return new ContainerPlayerInterface((TileEntityPlayerInterface) tileEntity);
            case ITEM_FILTER:
                IInventory inventoryFilter = ItemFilter.getItemFilterInv(player);
                if (inventoryFilter == null) {
                    inventoryFilter = new InventoryBasic("placeholder", false, 9);
                }
                return new ContainerItemFilter(player.getCurrentEquippedItem(), player.inventory, inventoryFilter);
            case ADVANCED_ITEMCOLLECTOR:
                return new ContainerItemCollector(player.inventory, (TileEntityAdvancedItemCollector) tileEntity);
            case ONLINE_DETECTOR:
                return new ContainerOnlineDetector();
            case VOID_STONE:
                return new ContainerVoidStone(player.inventory);
            case DROP_FILTER:
                IInventory inventoryDropFilter = ItemDropFilter.getDropFilterInv(player);
                if (inventoryDropFilter == null) {
                    inventoryDropFilter = new InventoryBasic("placeholder", false, 1);
                }
                return new ContainerDropFilter(player.inventory, inventoryDropFilter);
            case ENDER_LETTER:
                IInventory letterInventory = ItemEnderLetter.getLetterInventory(player);
                if (letterInventory == null) {
                    letterInventory = new InventoryBasic("placeholder", false, 9);
                }
                return new ContainerEnderLetter(player.getCurrentEquippedItem(), player.inventory, letterInventory);
            case SOUND_RECORDER:
                return new ContainerSoundRecorder();
            case MAGNETIC_FORCE:
                return new ContainerMagneticForce();
            case DYEING_MACHINE:
                return new ContainerDyeingMachine(player.inventory, world, x, y, z);
            case IMBUING_STATION:
                return new ContainerImbuingStation(player.inventory, (TileEntityImbuingStation) tileEntity);
            case OP_SPECTRE_KEY:
                return new ContainerOpSpectreKey();
            case ENERGY_DISTRIBUTOR:
                return new ContainerEnergyDistributor((TileEntityEnergyDistributor) tileEntity);
            case ENDER_ENERGY_DISTRIBUTOR:
                return new ContainerEnderEnergyDistributor(
                        player.inventory,
                        (TileEntityEnderEnergyDistributor) tileEntity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        switch (ID) {
            case PLAYER_INTERFACE:
                return new GuiPlayerInterface((TileEntityPlayerInterface) tileEntity);
            case ITEM_FILTER:
                IInventory inventoryFilter = ItemFilter.getItemFilterInv(player);
                if (inventoryFilter == null) {
                    inventoryFilter = new InventoryBasic("placeholder", false, 27);
                }
                return new GuiItemFilter(player.getCurrentEquippedItem(), player, player.inventory, inventoryFilter);
            case ADVANCED_ITEMCOLLECTOR:
                return new GuiItemCollector(player.inventory, (TileEntityAdvancedItemCollector) tileEntity);
            case ONLINE_DETECTOR:
                return new GuiOnlineDetector((TileEntityOnlineDetector) tileEntity);
            case VOID_STONE:
                return new GuiVoidStone(player.inventory);
            case DROP_FILTER:
                IInventory inventoryDropFilter = ItemDropFilter.getDropFilterInv(player);
                if (inventoryDropFilter == null) {
                    inventoryDropFilter = new InventoryBasic("placeholder", false, 1);
                }
                return new GuiDropFilter(player, player.inventory, inventoryDropFilter);
            case ENDER_LETTER:
                IInventory letterInventory = ItemFilter.getItemFilterInv(player);
                if (letterInventory == null) {
                    letterInventory = new InventoryBasic("placeholder", false, 9);
                }
                return new GuiEnderLetter(player.inventory, letterInventory, player.getCurrentEquippedItem());
            case SOUND_RECORDER:
                return new GuiSoundRecorder();
            case MAGNETIC_FORCE:
                return new GuiMagneticForce();
            case DYEING_MACHINE:
                return new GuiDyeingMachine(player.inventory, world, x, y, z);
            case IMBUING_STATION:
                return new GuiImbuingStation(player.inventory, (TileEntityImbuingStation) tileEntity);
            case OP_SPECTRE_KEY:
                return new GuiOpSpectreKey();
            case ENERGY_DISTRIBUTOR:
                return new GuiEnergyDistributor((TileEntityEnergyDistributor) tileEntity);
            case ENDER_ENERGY_DISTRIBUTOR:
                return new GuiEnderEnergyDistributor(player.inventory, (TileEntityEnderEnergyDistributor) tileEntity);
        }
        return null;
    }
}
