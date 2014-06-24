package lumien.randomthings.Blocks;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockLapisLamp extends BlockBase
{

	public BlockLapisLamp()
	{
		super("lapisLamp",Material.redstoneLight);

		this.setHardness(0.3F);
		this.setStepSound(soundTypeGlass);
	}
	
    @Override
	public int getRenderColor(int p_149741_1_)
    {
    	GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        return 16777215;
    }
	
    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }
	
    @Override
	public int getRenderBlockPass()
    {
        return 1;
    }
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block != this)
        {
            return block.getLightValue(world, x, y, z);
        }

        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
        	return 15;
        }
        else
        {
        	return 0;
        }
    }
}
