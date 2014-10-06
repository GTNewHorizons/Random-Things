package lumien.randomthings.Items;

import java.util.List;

import lumien.randomthings.Client.ClientTickHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class ItemOp extends ItemBase
{
	public ItemOp(String name)
	{
		super(name);
	}

	@Override
	public void onUpdate(ItemStack itemStack, World worldObj, net.minecraft.entity.Entity entity, int slot, boolean p_77663_5_)
	{
		if (worldObj.getTotalWorldTime() % 10 == 0 && !worldObj.isRemote)
		{
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;

				if (!MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile()))
				{
					player.inventory.setInventorySlotContents(slot, null);
					player.inventoryContainer.detectAndSendChanges();
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("\u00A7cOperators only");
	}

	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return ClientTickHandler.getCurrentOPColor();
	}
}
