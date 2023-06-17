package lumien.randomthings.Items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

import lumien.randomthings.Library.ChestCategory;

public class ItemCreativeChestGenerator extends ItemCreative {

    private static final Random rng = new Random();

    public ItemCreativeChestGenerator() {
        super("creativeChestGenerator");
        this.setMaxStackSize(1);
        this.setFull3D();
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        ItemStack is = new ItemStack(p_150895_1_, 1, 0);
        is.stackTagCompound = new NBTTagCompound();
        p_150895_3_.add(is);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

        NBTTagCompound nbt = par1ItemStack.stackTagCompound;
        if (nbt != null) {
            ChestCategory selectedCategory = ChestCategory.values()[nbt.getInteger("category")];
            par3List.add(I18n.format("text.miscellaneous.category", selectedCategory.getName()));
            par3List.add(I18n.format("text.miscellaneous.shiftCategory"));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        NBTTagCompound nbt = par1ItemStack.stackTagCompound;
        if (nbt != null) {
            ChestCategory selectedCategory = ChestCategory.values()[nbt.getInteger("category")];
            return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(par1ItemStack) + ".name"))
                    .trim() + " (" + selectedCategory.getName() + ")";
        } else {
            return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(par1ItemStack) + ".name"))
                    .trim();
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!par2World.isRemote && par3EntityPlayer.isSneaking()) {
            NBTTagCompound nbt = par1ItemStack.stackTagCompound;
            if (nbt == null) {
                par1ItemStack.stackTagCompound = new NBTTagCompound();
                par1ItemStack.stackTagCompound.setInteger("category", 0);
            } else {
                int currentCategory = par1ItemStack.stackTagCompound.getInteger("category");
                if (currentCategory + 1 < ChestCategory.values().length) {
                    currentCategory++;
                } else {
                    currentCategory = 0;
                }
                par1ItemStack.stackTagCompound.setInteger("category", currentCategory);
            }
        }
        return par1ItemStack;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer entityPlayer, World worldObj, int posX, int posY,
            int posZ, int side, float par8, float par9, float par10) {
        if (!worldObj.isRemote && !entityPlayer.isSneaking()) {
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

            if (!entityPlayer.canPlayerEdit(posX, posY, posZ, side, par1ItemStack)) {
                return false;
            } else {
                if (worldObj.isAirBlock(posX, posY, posZ)) {
                    if (Blocks.chest.canPlaceBlockAt(worldObj, posX, posY, posZ)) {
                        NBTTagCompound nbt = par1ItemStack.stackTagCompound;
                        if (nbt == null) {
                            par1ItemStack.stackTagCompound = new NBTTagCompound();
                            par1ItemStack.stackTagCompound.setInteger("category", 0);
                        }
                        ChestCategory category = ChestCategory.values()[par1ItemStack.stackTagCompound
                                .getInteger("category")];
                        worldObj.setBlock(posX, posY, posZ, Blocks.chest);
                        WeightedRandomChestContent.generateChestContents(
                                rng,
                                ChestGenHooks.getItems(category.getName(), rng),
                                (IInventory) worldObj.getTileEntity(posX, posY, posZ),
                                ChestGenHooks.getCount(category.getName(), rng));
                    }
                }
                return true;
            }
        }
        return false;
    }
}
