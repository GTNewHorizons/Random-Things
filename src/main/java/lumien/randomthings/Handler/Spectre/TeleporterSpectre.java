package lumien.randomthings.Handler.Spectre;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Configuration.Settings;

public class TeleporterSpectre extends Teleporter {

    private final WorldServer world;
    private final int coord;

    public TeleporterSpectre(WorldServer world) {
        super(world);
        this.world = world;
        this.coord = 0;
    }

    public TeleporterSpectre(WorldServer world, int coord) {
        super(world);
        this.world = world;
        this.coord = coord;
    }

    @Override
    public void placeInPortal(Entity entity, double par2, double par4, double par6, float par8) {
        if (this.world.provider.dimensionId == Settings.SPECTRE_DIMENSON_ID) {
            final int minX = Math.min(coord * 32, coord * 32 + 15);
            final int minY = 40;
            final int minZ = 0;

            final int maxX = Math.max(coord * 32, coord * 32 + 15);
            final int maxY = 52;
            final int maxZ = 15;

            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        if (x == minX || y == minY || z == minZ || x == maxX || y == maxY || z == maxZ) {
                            this.world.setBlock(x, y, z, ModBlocks.spectreBlock, 12, 2);
                        }
                    }
                }
            }
            entity.setLocationAndAngles(coord * 32 + 9 - 1, 42, 2 - 0.5, entity.rotationYaw, 0.0F);
        }
    }
}
