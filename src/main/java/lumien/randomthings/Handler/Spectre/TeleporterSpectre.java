package lumien.randomthings.Handler.Spectre;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Library.RandomThingsNBTKeys;
import lumien.randomthings.Mixins.early.EntityAccessor;

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

            final double movementFactor = entity.worldObj.provider.getMovementFactor();
            final NBTTagCompound nbt = entity.getEntityData();
            nbt.setInteger(RandomThingsNBTKeys.OLD_DIMENSION, entity.worldObj.provider.dimensionId);
            nbt.setDouble(RandomThingsNBTKeys.OLD_POSX, entity.posX / movementFactor);
            nbt.setDouble(RandomThingsNBTKeys.OLD_POSY, entity.posY);
            nbt.setDouble(RandomThingsNBTKeys.OLD_POSZ, entity.posZ / movementFactor);

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
                            if (this.world.getBlock(x, y, z) != ModBlocks.spectreBlock) {
                                this.world.setBlock(x, y, z, ModBlocks.spectreBlock, 12, 2);
                            }
                        }
                    }
                }
            }

            entity.setLocationAndAngles(coord * 32 + 9 - 1, 42, 2 - 0.5, entity.rotationYaw, 0.0F);

        } else {

            if (((EntityAccessor) entity).getCustomEntityData() != null) {
                NBTTagCompound nbt = entity.getEntityData();
                if (nbt.hasKey(RandomThingsNBTKeys.OLD_POSX)) {
                    double oldPosX = nbt.getDouble(RandomThingsNBTKeys.OLD_POSX);
                    double oldPosY = nbt.getDouble(RandomThingsNBTKeys.OLD_POSY);
                    double oldPosZ = nbt.getDouble(RandomThingsNBTKeys.OLD_POSZ);
                    entity.setLocationAndAngles(oldPosX, oldPosY, oldPosZ, entity.rotationYaw, 0.0F);
                    return;
                }
            }
            ChunkCoordinates cc = this.world.provider.getRandomizedSpawnPoint();
            entity.setLocationAndAngles(cc.posX, cc.posY, cc.posZ, entity.rotationYaw, 0.0F);

        }
    }
}
