package lumien.randomthings.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockSpectreBlock extends Block
{

	public BlockSpectreBlock()
	{
		super(Material.rock);

		this.setBlockTextureName("RandomThings:spectreBlock");
		this.setBlockName("spectreBlock");
		this.setCreativeTab(RandomThings.creativeTab);
		this.lightValue=15;
		
		this.blockHardness = -1;
		this.setBlockUnbreakable().setResistance(6000000.0F).setStepSound(soundTypeGlass);
		
		GameRegistry.registerBlock(this, "spectreBlock");
	}
	
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
    {
        return 16777215;
    }
    
    public float getAmbientOcclusionLightValue()
    {
        return 21F;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
