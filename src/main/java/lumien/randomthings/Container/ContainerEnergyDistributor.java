package lumien.randomthings.Container;

import lumien.randomthings.Library.Interfaces.IContainerWithProperties;
import lumien.randomthings.Network.Messages.MessageContainerProperty;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.TileEntities.EnergyDistributors.TileEntityEnergyDistributor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.common.util.ForgeDirection;

public class ContainerEnergyDistributor extends Container implements IContainerWithProperties {

    TileEntityEnergyDistributor te;
    int oldBuffer;
    int oldEnergyDistributedLastTick;
    int oldMachinesConnected;
    public int energyDistributedLastTick;
    public int buffer;
    public int machinesConnected;

    public ContainerEnergyDistributor(TileEntityEnergyDistributor te) {
        this.te = te;
        this.oldBuffer = 0;
        this.buffer = 0;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return te.isUseableByPlayer(player);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);

        EntityPlayerMP player = (EntityPlayerMP) crafting;

        PacketHandler.INSTANCE
                .sendTo(new MessageContainerProperty(0, this.te.getEnergyStored(ForgeDirection.DOWN)), player);
        PacketHandler.INSTANCE.sendTo(new MessageContainerProperty(1, this.te.getEnergyDistributedLastTick()), player);
        PacketHandler.INSTANCE.sendTo(new MessageContainerProperty(2, this.te.getMachinesConnected()), player);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;
            EntityPlayerMP player = (EntityPlayerMP) icrafting;
            if (this.oldBuffer != this.te.getEnergyStored(ForgeDirection.DOWN)) {
                PacketHandler.INSTANCE
                        .sendTo(new MessageContainerProperty(0, this.te.getEnergyStored(ForgeDirection.DOWN)), player);
                this.oldBuffer = te.getEnergyStored(ForgeDirection.DOWN);
            }

            if (this.oldEnergyDistributedLastTick != this.te.getEnergyDistributedLastTick()) {
                PacketHandler.INSTANCE
                        .sendTo(new MessageContainerProperty(1, this.te.getEnergyDistributedLastTick()), player);
                this.oldEnergyDistributedLastTick = te.getEnergyStored(ForgeDirection.DOWN);
            }

            if (this.oldMachinesConnected != this.te.getMachinesConnected()) {
                PacketHandler.INSTANCE.sendTo(new MessageContainerProperty(2, this.te.getMachinesConnected()), player);
                this.oldMachinesConnected = te.getMachinesConnected();
            }
        }
    }

    @Override
    public void setValue(int index, int value) {
        switch (index) {
            case 0:
                this.buffer = value;
                break;
            case 1:
                this.energyDistributedLastTick = value;
                break;
            case 2:
                this.machinesConnected = value;
                break;
        }
    }
}
