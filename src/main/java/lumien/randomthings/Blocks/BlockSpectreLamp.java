package lumien.randomthings.Blocks;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockSpectreLamp extends Block
{

	public BlockSpectreLamp()
	{
		super(Material.redstoneLight);

		this.setCreativeTab(RandomThings.creativeTab);
		this.setBlockName("spectreLamp");
		
		GameRegistry.registerBlock(this, "spectreLamp");
		
		this.setBlockTextureName("RandomThings:spectreLamp");
		this.setHardness(0.3F);
		this.setStepSound(soundTypeGlass);
	}
	
    public int getRenderColor(int p_149741_1_)
    {
    	GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        return 16777215;
    }
	
    public boolean isOpaqueCube()
    {
        return false;
    }
	
    public int getRenderBlockPass()
    {
        return 1;
    }
	
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
