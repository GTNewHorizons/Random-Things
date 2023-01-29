package lumien.randomthings.Items;

import java.util.List;

import lumien.randomthings.Client.ClientTickHandler;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class ItemCreative extends ItemBase {

    public ItemCreative(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add("\u00A7d" + I18n.format("text.miscellaneous.creativeOnly"));
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        return ClientTickHandler.getCurrentCreativeColor();
    }
}
