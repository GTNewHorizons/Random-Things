package lumien.randomthings.Handler.Spectre;

import lumien.randomthings.Configuration.Settings;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderSpectre extends WorldProvider {
    private float[] colorsSunriseSunset;

    public WorldProviderSpectre() {
        this.hasNoSky = true;
    }

    @Override
    protected void generateLightBrightnessTable() {
        for (int i = 0; i < this.lightBrightnessTable.length; i++) {
            this.lightBrightnessTable[i] = 1;
        }
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean doesXZShowFog(int par1, int par2) {
        return Settings.SPECTRE_DIMENSION_FOG;
    }

    @Override
    public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful) {
        super.setAllowedSpawnTypes(false, false);
    }

    @Override
    public String getDimensionName() {
        return "SpectreWorld";
    }

    @Override
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0F);
        this.dimensionId = Settings.SPECTRE_DIMENSON_ID;
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderSpectre(this.worldObj);
    }

    @Override
    public float[] calcSunriseSunsetColors(float par1, float par2) {
        return new float[] {0, 0, 0, 0};
    }

    @Override
    public float calculateCelestialAngle(long par1, float par3) {
        return 0;
    }

    @Override
    public float getCloudHeight() {
        return -5;
    }

    @Override
    public String getWelcomeMessage() {
        return "Entering the Spectre World";
    }

    @Override
    public String getDepartMessage() {
        return "Leaving the Spectre World";
    }
}
