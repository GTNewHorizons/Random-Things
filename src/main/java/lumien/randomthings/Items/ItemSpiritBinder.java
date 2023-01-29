package lumien.randomthings.Items;

import java.util.List;

import lumien.randomthings.Library.Colors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpiritBinder extends ItemBase {

    public ItemSpiritBinder() {
        super("spiritBinder");
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.getBoolean("hasSpawner")) {
            String entityName = par1ItemStack.stackTagCompound.getString("EntityId");

            par3List.add(Colors.DARK_GREEN + "Holding " + entityName + " Spawner");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.getBoolean("hasSpawner");
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int posX,
            int posY, int posZ, int side, float par8, float par9, float par10) {
        if (par1ItemStack.stackTagCompound == null || !par1ItemStack.stackTagCompound.getBoolean("hasSpawner")) {
            Block b = par3World.getBlock(posX, posY, posZ);
            if (!b.isAir(par3World, posX, posY, posZ)) {
                if (b instanceof BlockMobSpawner) {
                    TileEntityMobSpawner te = (TileEntityMobSpawner) par3World.getTileEntity(posX, posY, posZ);

                    NBTTagCompound nbt = new NBTTagCompound();
                    te.writeToNBT(nbt);
                    nbt.setBoolean("hasSpawner", true);
                    par1ItemStack.stackTagCompound = nbt;
                    par3World.setBlockToAir(posX, posY, posZ);
                    te.invalidate();
                    return true;
                }
            }
        } else {
            if (side == 0) {
                --posY;
            }

            if (side == 1) {
                ++posY;
            }

            if (side == 2) {
                --posZ;
            }

            if (side == 3) {
                ++posZ;
            }

            if (side == 4) {
                --posX;
            }

            if (side == 5) {
                ++posX;
            }

            TileEntityMobSpawner te = new TileEntityMobSpawner();

            par1ItemStack.stackTagCompound.setInteger("x", posX);
            par1ItemStack.stackTagCompound.setInteger("y", posY);
            par1ItemStack.stackTagCompound.setInteger("z", posZ);

            te.readFromNBT(par1ItemStack.stackTagCompound);

            par3World.setBlock(posX, posY, posZ, Blocks.mob_spawner);
            par3World.getTileEntity(posX, posY, posZ).readFromNBT(par1ItemStack.stackTagCompound);
            par1ItemStack.stackTagCompound.setBoolean("hasSpawner", false);
            return true;
        }
        return false;
    }
}
