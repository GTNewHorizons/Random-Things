package lumien.randomthings.TileEntities;

import java.util.HashSet;

import lumien.randomthings.Blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityWirelessLever extends TileEntity
{
	public static HashSet<TileEntityWirelessLever> loadedLevers = new HashSet<TileEntityWirelessLever>();
	
	int targetX,targetY,targetZ;
	
    public void validate()
    {
    	super.validate();
    	
    	loadedLevers.add(this);
    	System.out.println("Validate");
    	this.worldObj.notifyBlockOfNeighborChange(targetX, targetY, targetZ, ModBlocks.wirelessLever);
    }
    
    public void invalidate()
    {
        super.invalidate();
        
        loadedLevers.remove(this);
        System.out.println("Invalidate");
        this.worldObj.notifyBlockOfNeighborChange(targetX, targetY, targetZ, ModBlocks.wirelessLever);
    }
    
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z)
    {
        return (oldBlock != newBlock);
    }
    
    public static boolean isPowered(World worldObj,int posX,int posY,int posZ)
    {
    	for (TileEntityWirelessLever te : loadedLevers)
    	{
    		if (te.worldObj == worldObj && te.targetX==posX && te.targetY==posY && te.targetZ==posZ && (te.worldObj.getBlockMetadata(te.xCoord, te.yCoord, te.zCoord) & 8) > 0)
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }

	public void setTarget(int x, int y, int z)
	{
		targetX = x;
		targetY = y;
		targetZ = z;
		
		updateTarget();
	}

	public void updateTarget()
	{
		this.worldObj.notifyBlockOfNeighborChange(targetX, targetY, targetZ, ModBlocks.wirelessLever);
	}
}
