package lumien.randomthings.Items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import lumien.randomthings.Client.ClientTickHandler;

public class ItemOp extends ItemBase {

    public ItemOp(String name) {
        super(name);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World worldObj, net.minecraft.entity.Entity entity, int slot,
            boolean p_77663_5_) {
        if (worldObj.getTotalWorldTime() % 10 == 0 && !worldObj.isRemote) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;

                if (!MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile())) {
                    player.inventory.setInventorySlotContents(slot, null);
                    player.inventoryContainer.detectAndSendChanges();
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add("\u00A7c" + I18n.format("text.miscellaneous.opOnly"));
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        return ClientTickHandler.getCurrentOPColor();
    }
}
