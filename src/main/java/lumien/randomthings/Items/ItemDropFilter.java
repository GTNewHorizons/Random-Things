package lumien.randomthings.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.Library.Inventorys.InventoryDropFilter;
import lumien.randomthings.RandomThings;

public class ItemDropFilter extends ItemBase {

    IIcon[] icons;

    public ItemDropFilter() {
        super("dropFilter");
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(I18n.format("text.tooltip.item.dropFilter.generic"));
        if (par1ItemStack.getItemDamage() == 0) par3List.add(I18n.format("text.tooltip.item.dropFilter"));
        else if (par1ItemStack.getItemDamage() == 1) par3List.add(I18n.format("text.tooltip.item.dropFilterVoiding"));

        if (!(ItemDropFilter.getDropFilterInv(par2EntityPlayer, par1ItemStack) == null)) {
            IInventory inventoryFilter = new InventoryDropFilter(par2EntityPlayer, par1ItemStack);
            inventoryFilter.openInventory();
            if (inventoryFilter.getStackInSlot(0) != null) {
                ModItems.filter.addInformation(inventoryFilter.getStackInSlot(0), par2EntityPlayer, par3List, par4);
            }
        }
    }

    @Override
    public void registerIcons(IIconRegister ir) {
        icons = new IIcon[2];
        icons[0] = ir.registerIcon("RandomThings:dropFilter");
        icons[1] = ir.registerIcon("RandomThings:dropFilterVoiding");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    @Override
    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par1ItemStack.stackTagCompound = new NBTTagCompound();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par1ItemStack.stackTagCompound == null) {
            par1ItemStack.stackTagCompound = new NBTTagCompound();
        }
        if (!par2World.isRemote) {
            par3EntityPlayer.openGui(
                    RandomThings.instance,
                    GuiIds.DROP_FILTER,
                    par2World,
                    (int) par3EntityPlayer.posX,
                    (int) par3EntityPlayer.posY,
                    (int) par3EntityPlayer.posZ);
        }

        return par1ItemStack;
    }

    @Override
    public IIcon getIconFromDamage(int metadata) {
        if (metadata < 0 || metadata > 1) {
            return icons[0];
        }
        return icons[metadata];
    }

    public static IInventory getDropFilterInv(EntityPlayer player) {
        ItemStack dropFilter;
        IInventory dropFilterInventory = null;
        dropFilter = player.getCurrentEquippedItem();

        if (dropFilter != null && dropFilter.getItem() instanceof ItemDropFilter) {
            dropFilterInventory = new InventoryDropFilter(player, dropFilter);
        }

        return dropFilterInventory;
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        String type = "error";
        switch (par1ItemStack.getItemDamage()) {
            case 0: // Drop Filter
                type = "dropFilter";
                break;
            case 1: // Voiding Drop Filter
                type = "dropFilterVoiding";
                break;
        }
        return "item." + type;
    }

    public static IInventory getDropFilterInv(EntityPlayer player, ItemStack dropFilterStack) {
        IInventory dropFilter = null;

        if (dropFilterStack != null && dropFilterStack.getItem() instanceof ItemDropFilter) {
            dropFilter = new InventoryDropFilter(player, dropFilterStack);
        }

        return dropFilter;
    }
}
