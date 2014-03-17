package lumien.randomthings.Items;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Entity.EntityWhitestone;
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
		
		GameRegistry.registerItem(this, "whitestone");
	}
	
    @Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
    	return par1ItemStack.getItemDamage()==1;
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
