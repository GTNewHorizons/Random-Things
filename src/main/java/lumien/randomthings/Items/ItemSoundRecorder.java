package lumien.randomthings.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSoundRecorder extends Item
{
	public ItemSoundRecorder()
	{
		this.setCreativeTab(RandomThings.creativeTab);
		this.setUnlocalizedName("soundRecorder");
		
		GameRegistry.registerItem(this, "soundRecorder");
	}
	
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	if (par3EntityPlayer.isSneaking())
    	{
    		if (par1ItemStack.getItemDamage()==0)
    		{
    			par1ItemStack.setItemDamage(1);
    		}
    		else
    		{
    			par1ItemStack.setItemDamage(0);
    		}
    	}
    	else
    	{
    		par3EntityPlayer.openGui(RandomThings.instance, GuiIds.SOUND_RECORDER, par2World, 0, 0, 0);
    	}
        return par1ItemStack;
    }
}
