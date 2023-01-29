package lumien.randomthings.Items;

import java.util.List;

import lumien.randomthings.Entity.EntityBloodmoonCircle;
import lumien.randomthings.Handler.Bloodmoon.ServerBloodmoonHandler;
import lumien.randomthings.Library.BlockPattern;
import lumien.randomthings.RandomThings;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBloodstone extends ItemBase {

    public static final float MAX_CHARGES = 400;

    public static BlockPattern ritualPattern;

    public ItemBloodstone() {
        super("bloodStone");

        this.setMaxDamage((int) MAX_CHARGES);
        this.setMaxStackSize(1);

        ritualPattern = new BlockPattern();

        ritualPattern.addBlock(Blocks.obsidian, 0, -1, 0, -1);
        ritualPattern.addBlock(Blocks.obsidian, 0, -1, 0, 0);
        ritualPattern.addBlock(Blocks.obsidian, 0, -1, 0, 1);

        ritualPattern.addBlock(Blocks.obsidian, 0, 0, 0, -1);
        ritualPattern.addBlock(Blocks.obsidian, 0, 0, 0, 0);
        ritualPattern.addBlock(Blocks.obsidian, 0, 0, 0, 1);

        ritualPattern.addBlock(Blocks.obsidian, 0, 1, 0, -1);
        ritualPattern.addBlock(Blocks.obsidian, 0, 1, 0, 0);
        ritualPattern.addBlock(Blocks.obsidian, 0, 1, 0, 1);

        ritualPattern.addBlock(Blocks.redstone_block, 0, -2, 0, 0);
        ritualPattern.addBlock(Blocks.redstone_block, 0, 2, 0, 0);
        ritualPattern.addBlock(Blocks.redstone_block, 0, 0, 0, -2);
        ritualPattern.addBlock(Blocks.redstone_block, 0, 0, 0, 2);

        ritualPattern.addBlock(Blocks.quartz_block, 0, -2, 0, -2);
        ritualPattern.addBlock(Blocks.quartz_block, 0, -2, 0, 2);
        ritualPattern.addBlock(Blocks.quartz_block, 0, 2, 0, 2);
        ritualPattern.addBlock(Blocks.quartz_block, 0, 2, 0, -2);

        ritualPattern.addBlock(Blocks.nether_brick, 0, -2, 0, 1);
        ritualPattern.addBlock(Blocks.nether_brick, 0, -2, 0, -1);
        ritualPattern.addBlock(Blocks.nether_brick, 0, 2, 0, 1);
        ritualPattern.addBlock(Blocks.nether_brick, 0, 2, 0, -1);

        ritualPattern.addBlock(Blocks.nether_brick, 0, -1, 0, -2);
        ritualPattern.addBlock(Blocks.nether_brick, 0, 1, 0, -2);
        ritualPattern.addBlock(Blocks.nether_brick, 0, -1, 0, 2);
        ritualPattern.addBlock(Blocks.nether_brick, 0, 1, 0, 2);
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World worldObj, int posX, int posY, int posZ, int side,
            float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (worldObj.getBlock(posX, posY, posZ) == Blocks.obsidian) {
            if (!worldObj.isRemote && worldObj.getTotalWorldTime() > 15000
                    && !ServerBloodmoonHandler.INSTANCE.isBloodmoonScheduled()) {
                if (ritualPattern.matches(worldObj, posX, posY, posZ)) {
                    int charges = 0;
                    if (is.stackTagCompound != null) {
                        charges = is.stackTagCompound.getInteger("charges");
                    }

                    if (charges >= 50) {
                        List<EntityBloodmoonCircle> list = worldObj.getEntitiesWithinAABB(
                                EntityBloodmoonCircle.class,
                                AxisAlignedBB.getBoundingBox(
                                        posX + 0.5f - 2,
                                        posY + 1 - 2,
                                        posZ + 0.5f - 2,
                                        posX + 0.5f + 2,
                                        posY + 1 + 2,
                                        posZ + 0.5f + 2));
                        if (list.isEmpty()) {
                            worldObj.spawnEntityInWorld(
                                    new EntityBloodmoonCircle(
                                            worldObj,
                                            posX + 0.5f,
                                            posY + 1,
                                            posZ + 0.5f,
                                            posX,
                                            posY,
                                            posZ));

                            if (!player.capabilities.isCreativeMode) {
                                is.stackTagCompound.setInteger("charges", charges - 50);
                            }
                        }
                    }
                }
            }
            return true;
        }
        if (!worldObj.isRemote && player.isSneaking()
                && player.getCommandSenderName().equals(RandomThings.AUTHOR_USERNAME)) {
            ritualPattern.place(worldObj, posX, posY, posZ);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List itemList) {
        super.getSubItems(item, creativeTab, itemList);

        ItemStack full = new ItemStack(this, 1, 0);
        full.stackTagCompound = new NBTTagCompound();
        full.stackTagCompound.setInteger("charges", (int) MAX_CHARGES);
        itemList.add(full);
    }

    @Override
    public void onUpdate(ItemStack is, World worldObj, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        super.onUpdate(is, worldObj, entity, p_77663_4_, p_77663_5_);

        int charges;
        if (is.stackTagCompound == null) {
            charges = 0;
        } else {
            charges = is.stackTagCompound.getInteger("charges");
        }
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            int rate;
            if (charges <= 200) {
                rate = (int) (200 - 180F / 200 * charges);
            } else {
                rate = (int) (20F - ((charges - 200) * 10F / 200F));
            }

            if (worldObj.getTotalWorldTime() % rate == 0) {
                player.heal(1);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List informationList, boolean extendedInformation) {
        int charges;
        if (is.stackTagCompound == null) {
            charges = 0;
        } else {
            charges = is.stackTagCompound.getInteger("charges");
        }

        informationList.add(I18n.format("text.miscellaneous.charges", charges));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return par1ItemStack.stackTagCompound != null
                && par1ItemStack.stackTagCompound.getInteger("charges") == MAX_CHARGES;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.stackTagCompound == null) {
            return 1;
        } else {
            return 1 - stack.stackTagCompound.getInteger("charges") / MAX_CHARGES;
        }
    }

    @Override
    public boolean showDamage(ItemStack item) {
        if (item.stackTagCompound == null) {
            return true;
        } else {
            return item.stackTagCompound.getInteger("charges") < MAX_CHARGES;
        }
    }

    @Override
    public int getDurabilityColor(ItemStack item, double health) {
        return 13107200;
    }
}
