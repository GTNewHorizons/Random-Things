package lumien.randomthings.Items;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Entity.EntityWhitestone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWhiteStone extends Item
{
	public ItemWhiteStone()
	{
		this.setUnlocalizedName("whitestone");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setTextureName("RandomThings:whitestone");
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		
		GameRegistry.registerItem(this, "whitestone");
	}
	
    @Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
    	return par1ItemStack.getItemDamage()==1;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
    }
    
    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        switch (par1ItemStack.getItemDamage())
        {
        	case 0:
        		return "item.whitestoneUncharged";
        	case 1:
        		return "item.whitestoneCharged";
        }
        return "item.whitestoneUncharged";
    }
    
    @Override
    public boolean hasCustomEntity(ItemStack stack)
    {
        return stack.getItemDamage()==0;
    }
    
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack)
    {
    	EntityWhitestone entity = new EntityWhitestone(world,location.posX,location.posY,location.posZ,itemstack);
    	
    	entity.delayBeforeCanPickup=60;
    	entity.motionX = location.motionX;
    	entity.motionY = location.motionY;
    	entity.motionZ = location.motionZ;
    	
        return entity;
    }
}
