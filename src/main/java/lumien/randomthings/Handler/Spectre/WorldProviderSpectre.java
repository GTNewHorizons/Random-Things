package lumien.randomthings.Handler.Spectre;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderSpectre extends WorldProvider
{
	private float[] colorsSunriseSunset;

	public WorldProviderSpectre()
	{
		this.hasNoSky = true;
	}

	protected void generateLightBrightnessTable()
	{
		for (int i = 0; i < this.lightBrightnessTable.length; i++)
		{
			this.lightBrightnessTable[i] = 1;
		}
	}
	
    public boolean isSurfaceWorld()
    {
        return false;
    }
    
    public boolean canRespawnHere()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int par1, int par2)
    {
        return true;
    }

	@Override
	public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful)
	{
		super.setAllowedSpawnTypes(false, false);
	}

	@Override
	public String getDimensionName()
	{
		return "SpectreWorld";
	}

	@Override
	public void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0F);
		this.dimensionId = 32;
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderSpectre(this.worldObj);
	}
	
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float par1, float par2)
    {
        return new float[]{0,0,0,0};
    }
    
    @Override
    public float calculateCelestialAngle(long par1, float par3)
    {
    	return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return -5;
    }
    
    public String getWelcomeMessage()
    {
        return "Entering the Spectre World";
    }

    public String getDepartMessage()
    {
    	return "Entering the Spectre World";
    }

}
