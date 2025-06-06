package lumien.randomthings.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import lumien.randomthings.Configuration.ConfigDungeonLoot;
import lumien.randomthings.Configuration.ConfigItems;
import lumien.randomthings.Handler.Bloodmoon.ClientBloodmoonHandler;
import lumien.randomthings.Handler.Bloodmoon.ServerBloodmoonHandler;
import lumien.randomthings.Network.Messages.MessageWhitestone;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.RandomThings;

public class ItemWhiteStone extends ItemBase {

    public ItemWhiteStone() {
        super("whitestone");
        this.setTextureName("RandomThings:whitestone");
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);

        ChestGenHooks.addItem(
                ChestGenHooks.DUNGEON_CHEST,
                new WeightedRandomChestContent(new ItemStack(this, 1, 0), 1, 1, ConfigDungeonLoot.WHITESTONE_CHANCE));
    }

    @Override
    public int getDurabilityColor(ItemStack item, double health) {
        return 16777215;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (stack.getItemDamage() == 0) {
            if (stack.stackTagCompound != null) {
                double charge = stack.stackTagCompound.getInteger("charge") / 1200D * 100D;
                charge = round(charge, 2);
                par3List.add("Charge: " + charge + "%");
            }
        }
    }

    private double round(double wert, int stellen) {
        return Math.round(wert * Math.pow(10, stellen)) / Math.pow(10, stellen);
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return par1ItemStack.getItemDamage() == 1;
    }

    @Override
    public boolean showDamage(ItemStack item) {
        return item.getItemDamage() == 0;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldObj, Entity entity, int par4, boolean par5) {
        int time = (int) (worldObj.getWorldTime() % 24000);
        if (stack.getItemDamage() == 0 && !worldObj.isRemote
                && (entity instanceof EntityPlayer)
                && worldObj.getCurrentMoonPhaseFactor() == 1F
                && time >= 18000
                && time <= 22000
                && worldObj.canBlockSeeTheSky(
                        (int) Math.floor(entity.posX),
                        (int) Math.floor(entity.posY),
                        (int) Math.floor(entity.posZ))) {
            EntityPlayer player = (EntityPlayer) entity;

            if (stack.stackTagCompound == null) {
                stack.stackTagCompound = new NBTTagCompound();
                stack.stackTagCompound.setInteger("charge", 0);
            }
            int charges = stack.stackTagCompound.getInteger("charge");

            stack.stackTagCompound.setInteger("charge", charges += 1);

            if (charges % 5 == 0) PacketHandler.INSTANCE.sendToAllAround(
                    new MessageWhitestone(entity.getEntityId()),
                    new TargetPoint(worldObj.provider.dimensionId, entity.posX, entity.posY, entity.posZ, 16));

            if (charges == 1201) {
                stack.stackTagCompound.setInteger("charge", 0);
                stack.setItemDamage(1);
            }
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.getItemDamage() == 1) {
            return 1;
        } else {
            if (stack.stackTagCompound == null) {
                return 1;
            } else {
                return 1 - stack.stackTagCompound.getInteger("charge") / 1200F;
            }
        }
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        if (ConfigItems.bloodStone && entityItem.dimension == 0
                && entityItem.getEntityItem().getItemDamage() == 1
                && entityItem.worldObj.canBlockSeeTheSky(
                        (int) Math.floor(entityItem.posX),
                        (int) Math.floor(entityItem.posY),
                        (int) Math.floor(entityItem.posZ))) {
            if (entityItem.worldObj.isRemote) {
                if (ClientBloodmoonHandler.INSTANCE.isBloodmoonActive()) RandomThings.proxy
                        .spawnColoredDust(entityItem.posX, entityItem.posY, entityItem.posZ, 0, 0.1, 0, 1, 0, 0);
            } else {
                if (ServerBloodmoonHandler.INSTANCE.isBloodmoonActive()) {
                    final NBTTagCompound nbt = entityItem.getEntityData();
                    final int newProgress = nbt.getInteger("progress") + 1;
                    nbt.setInteger("progress", newProgress);
                    if (newProgress >= 200) {
                        entityItem.setEntityItemStack(new ItemStack(ModItems.bloodStone));
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        switch (par1ItemStack.getItemDamage()) {
            case 0:
                return "item.whitestoneUncharged";
            case 1:
                return "item.whitestoneCharged";
        }
        return "item.whitestoneUncharged";
    }
}
