package lumien.randomthings.TileEntities;

import lumien.randomthings.Blocks.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPlayerInterface extends TileEntity implements ISidedInventory {
    String playerName;
    EntityPlayerMP playerEntity;

    int[] armorSlots = new int[4];
    int[] hotbarSlots = new int[9];
    int[] mainSlots = new int[27];

    public TileEntityPlayerInterface() {
        int i = 0;
        for (int slot = 36; slot < 40; slot++) {
            armorSlots[i] = slot;
            i += 1;
        }

        i = 0;
        for (int slot = 9; slot < 36; slot++) {
            mainSlots[i] = slot;
            i += 1;
        }

        i = 0;
        for (int slot = 0; slot < 9; slot++) {
            hotbarSlots[i] = slot;
            i += 1;
        }

        playerName = "";
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    public EntityPlayer getPlayer() {
        return this.playerEntity;
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            if (this.worldObj.getTotalWorldTime() % 20 == 0) {
                if (this.playerEntity == null && !this.playerName.equals("")) {
                    EntityPlayerMP tempPlayer = MinecraftServer.getServer()
                            .getConfigurationManager()
                            .func_152612_a(this.playerName);
                    if (tempPlayer != null) {
                        playerEntity = MinecraftServer.getServer()
                                .getConfigurationManager()
                                .func_152612_a(this.playerName);
                        this.worldObj.notifyBlocksOfNeighborChange(
                                this.xCoord, this.yCoord, this.zCoord, ModBlocks.playerInterface);
                    }
                } else {
                    EntityPlayerMP tempPlayer = MinecraftServer.getServer()
                            .getConfigurationManager()
                            .func_152612_a(this.playerName);
                    if (tempPlayer != playerEntity) {
                        this.playerEntity = null;
                        this.worldObj.notifyBlocksOfNeighborChange(
                                this.xCoord, this.yCoord, this.zCoord, ModBlocks.playerInterface);
                    }
                }
            }
        }
    }

    public void setPlayerName(String name) {
        this.playerName = name;
        this.markDirty();
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    private void checkPlayerEntity() {
        if (this.playerEntity != null) {
            if (this.playerEntity.isDead) {
                this.playerEntity = null;
            }
        }
    }

    @Override
    public int getSizeInventory() {
        checkPlayerEntity();
        if (this.playerEntity == null) {
            return 0;
        }
        return playerEntity.inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        checkPlayerEntity();
        if (this.playerEntity == null) {
            return null;
        }
        return playerEntity.inventory.getStackInSlot(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        checkPlayerEntity();
        if (this.playerEntity == null) {
            return null;
        }
        ItemStack newStack = playerEntity.inventory.decrStackSize(i, j);
        playerEntity.inventory.markDirty();
        return newStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        checkPlayerEntity();
        if (this.playerEntity == null) {
            return null;
        }
        return playerEntity.inventory.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        checkPlayerEntity();
        if (this.playerEntity == null) {
            return;
        }
        playerEntity.inventory.setInventorySlotContents(i, itemstack);
        playerEntity.inventory.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        checkPlayerEntity();
        if (this.playerEntity == null) {
            return 0;
        }
        return playerEntity.inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
                && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    @Override
    public void openInventory() {
        checkPlayerEntity();
        if (this.playerEntity != null) {
            this.playerEntity.inventory.openInventory();
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        checkPlayerEntity();
        if (this.playerEntity != null && this.playerEntity.inventoryContainer != null) {
            this.playerEntity.inventoryContainer.detectAndSendChanges();
            this.playerEntity.sendContainerToPlayer(playerEntity.inventoryContainer);
        }
    }

    @Override
    public void closeInventory() {
        checkPlayerEntity();
        if (this.playerEntity != null) {
            this.playerEntity.inventory.closeInventory();
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        checkPlayerEntity();
        if (this.playerEntity == null
                || this.playerEntity.inventoryContainer == null
                || this.playerEntity.inventory == null
                || this.playerEntity.inventoryContainer.getSlotFromInventory(this.playerEntity.inventory, i) == null) {
            return false;
        }
        return this.playerEntity
                .inventoryContainer
                .getSlotFromInventory(this.playerEntity.inventory, i)
                .isItemValid(itemstack);
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setString("playerName", this.playerName);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.playerName = par1NBTTagCompound.getString("playerName");
    }

    public boolean hasPlayer() {
        return this.playerEntity != null;
    }

    public InventoryPlayer getPlayerInventory() {
        return this.playerEntity.inventory;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        checkPlayerEntity();
        if (this.playerEntity == null) {
            return new int[] {};
        }
        if (side == 0) {
            return hotbarSlots;
        } else if (side == 1) {
            return armorSlots;
        } else {
            return mainSlots;
        }
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        return isItemValidForSlot(i, itemstack);
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return isItemValidForSlot(i, itemstack);
    }

    @Override
    public String getInventoryName() {
        return "Player Interface";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }
}
