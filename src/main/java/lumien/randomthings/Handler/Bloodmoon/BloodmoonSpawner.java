package lumien.randomthings.Handler.Bloodmoon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeEventFactory;

import cpw.mods.fml.common.eventhandler.Event.Result;
import lumien.randomthings.Configuration.Settings;

public class BloodmoonSpawner {

    /** The 17x17 area around the player where mobs can spawn */
    private final HashMap<ChunkCoordIntPair, Boolean> eligibleChunksForSpawning = new HashMap<>();

    protected static ChunkPosition func_151350_a(World p_151350_0_, int p_151350_1_, int p_151350_2_) {
        Chunk chunk = p_151350_0_.getChunkFromChunkCoords(p_151350_1_, p_151350_2_);
        int k = p_151350_1_ * 16 + p_151350_0_.rand.nextInt(16);
        int l = p_151350_2_ * 16 + p_151350_0_.rand.nextInt(16);
        int i1 = p_151350_0_.rand
                .nextInt(chunk == null ? p_151350_0_.getActualHeight() : chunk.getTopFilledSegment() + 16 - 1);
        return new ChunkPosition(k, i1, l);
    }

    /**
     * adds all chunks within the spawn radius of the players to eligibleChunksForSpawning. pars: the world,
     * hostileCreatures, passiveCreatures. returns number of eligible chunks.
     */
    public int findChunksForSpawning( // TODO excuse me wtf ???
            WorldServer p_77192_1_, boolean p_77192_2_, boolean p_77192_3_, boolean p_77192_4_) {
        if (!p_77192_2_ && !p_77192_3_) {
            return 0;
        } else {
            this.eligibleChunksForSpawning.clear();
            int i;
            int k;

            for (i = 0; i < p_77192_1_.playerEntities.size(); ++i) {
                EntityPlayer entityplayer = (EntityPlayer) p_77192_1_.playerEntities.get(i);
                int j = MathHelper.floor_double(entityplayer.posX / 16.0D);
                k = MathHelper.floor_double(entityplayer.posZ / 16.0D);
                byte b0 = 8;

                for (int l = -b0; l <= b0; ++l) {
                    for (int i1 = -b0; i1 <= b0; ++i1) {
                        boolean flag3 = l == -b0 || l == b0 || i1 == -b0 || i1 == b0;
                        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(l + j, i1 + k);

                        if (!flag3) {
                            this.eligibleChunksForSpawning.put(chunkcoordintpair, Boolean.FALSE);
                        } else if (!this.eligibleChunksForSpawning.containsKey(chunkcoordintpair)) {
                            this.eligibleChunksForSpawning.put(chunkcoordintpair, Boolean.TRUE);
                        }
                    }
                }
            }

            i = 0;
            ChunkCoordinates chunkcoordinates = p_77192_1_.getSpawnPoint();
            EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();
            k = aenumcreaturetype.length;

            for (int k3 = 0; k3 < k; ++k3) {
                EnumCreatureType enumcreaturetype = aenumcreaturetype[k3];

                if ((!enumcreaturetype.getPeacefulCreature() || p_77192_3_)
                        && (enumcreaturetype.getPeacefulCreature() || p_77192_2_)
                        && (!enumcreaturetype.getAnimal() || p_77192_4_)
                        && p_77192_1_.countEntities(enumcreaturetype, true)
                                <= enumcreaturetype.getMaxNumberOfCreature() * Settings.BLOODMOON_SPAWNLIMIT_MULTIPLIER
                                        * this.eligibleChunksForSpawning.size()
                                        / 256) {
                    Iterator<ChunkCoordIntPair> iterator = this.eligibleChunksForSpawning.keySet().iterator();
                    ArrayList<ChunkCoordIntPair> tmp = new ArrayList<>(eligibleChunksForSpawning.keySet());
                    Collections.shuffle(tmp);
                    iterator = tmp.iterator();
                    label110: while (iterator.hasNext()) {
                        ChunkCoordIntPair chunkcoordintpair1 = iterator.next();

                        if (!(Boolean) this.eligibleChunksForSpawning.get(chunkcoordintpair1)) {
                            ChunkPosition chunkposition = func_151350_a(
                                    p_77192_1_,
                                    chunkcoordintpair1.chunkXPos,
                                    chunkcoordintpair1.chunkZPos);
                            int j1 = chunkposition.chunkPosX;
                            int k1 = chunkposition.chunkPosY;
                            int l1 = chunkposition.chunkPosZ;

                            if (!p_77192_1_.getBlock(j1, k1, l1).isNormalCube()
                                    && p_77192_1_.getBlock(j1, k1, l1).getMaterial()
                                            == enumcreaturetype.getCreatureMaterial()) {
                                int i2 = 0;
                                int j2 = 0;

                                while (j2 < 3) {
                                    int k2 = j1;
                                    int l2 = k1;
                                    int i3 = l1;
                                    byte b1 = 6;
                                    BiomeGenBase.SpawnListEntry spawnlistentry = null;
                                    IEntityLivingData ientitylivingdata = null;
                                    int j3 = 0;

                                    while (true) {
                                        if (j3 < 4) {
                                            label103: {
                                                k2 += p_77192_1_.rand.nextInt(b1) - p_77192_1_.rand.nextInt(b1);
                                                l2 += p_77192_1_.rand.nextInt(1) - p_77192_1_.rand.nextInt(1);
                                                i3 += p_77192_1_.rand.nextInt(b1) - p_77192_1_.rand.nextInt(b1);

                                                if (p_77192_1_.canBlockSeeTheSky(k2, l2, i3)
                                                        && canCreatureTypeSpawnAtLocation(
                                                                enumcreaturetype,
                                                                p_77192_1_,
                                                                k2,
                                                                l2,
                                                                i3)) {
                                                    float f = k2 + 0.5F;
                                                    float f1 = l2;
                                                    float f2 = i3 + 0.5F;

                                                    if (p_77192_1_
                                                            .getClosestPlayer(f, f1, f2, Settings.BLOODMOON_SPAWNRANGE)
                                                            == null) {
                                                        float f3 = f - chunkcoordinates.posX;
                                                        float f4 = f1 - chunkcoordinates.posY;
                                                        float f5 = f2 - chunkcoordinates.posZ;
                                                        float f6 = f3 * f3 + f4 * f4 + f5 * f5;

                                                        if (f6 >= 576.0F) {
                                                            if (spawnlistentry == null) {
                                                                spawnlistentry = p_77192_1_.spawnRandomCreature(
                                                                        enumcreaturetype,
                                                                        k2,
                                                                        l2,
                                                                        i3);

                                                                if (spawnlistentry == null) {
                                                                    break label103;
                                                                }
                                                            }

                                                            EntityLiving entityliving;

                                                            try {
                                                                entityliving = (EntityLiving) spawnlistentry.entityClass
                                                                        .getConstructor(new Class[] { World.class })
                                                                        .newInstance(new Object[] { p_77192_1_ });
                                                            } catch (Exception exception) {
                                                                exception.printStackTrace();
                                                                return i;
                                                            }

                                                            entityliving.setLocationAndAngles(
                                                                    f,
                                                                    f1,
                                                                    f2,
                                                                    p_77192_1_.rand.nextFloat() * 360.0F,
                                                                    0.0F);

                                                            Result canSpawn = ForgeEventFactory.canEntitySpawn(
                                                                    entityliving,
                                                                    p_77192_1_,
                                                                    f,
                                                                    f1,
                                                                    f2);
                                                            if (canSpawn == Result.ALLOW || (canSpawn == Result.DEFAULT
                                                                    && entityliving.getCanSpawnHere())) {
                                                                // Bloodmoon Data
                                                                if (Settings.BLOODMOON_VANISH) {
                                                                    entityliving.getEntityData()
                                                                            .setBoolean("bloodmoonSpawned", true);
                                                                }

                                                                ++i2;
                                                                p_77192_1_.spawnEntityInWorld(entityliving);
                                                                if (!ForgeEventFactory.doSpecialSpawn(
                                                                        entityliving,
                                                                        p_77192_1_,
                                                                        f,
                                                                        f1,
                                                                        f2)) {
                                                                    ientitylivingdata = entityliving
                                                                            .onSpawnWithEgg(ientitylivingdata);
                                                                }

                                                                if (j2 >= ForgeEventFactory
                                                                        .getMaxSpawnPackSize(entityliving)) {
                                                                    continue label110;
                                                                }
                                                            }

                                                            i += i2;
                                                        }
                                                    }
                                                }

                                                ++j3;
                                                continue;
                                            }
                                        }

                                        ++j2;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return i;
        }
    }

    /**
     * Returns whether or not the specified creature type can spawn at the specified location.
     */
    public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType p_77190_0_, World p_77190_1_, int p_77190_2_,
            int p_77190_3_, int p_77190_4_) {
        if (p_77190_0_.getCreatureMaterial() == Material.water) {
            return p_77190_1_.getBlock(p_77190_2_, p_77190_3_, p_77190_4_).getMaterial().isLiquid()
                    && p_77190_1_.getBlock(p_77190_2_, p_77190_3_ - 1, p_77190_4_).getMaterial().isLiquid()
                    && !p_77190_1_.getBlock(p_77190_2_, p_77190_3_ + 1, p_77190_4_).isNormalCube();
        } else if (!World.doesBlockHaveSolidTopSurface(p_77190_1_, p_77190_2_, p_77190_3_ - 1, p_77190_4_)) {
            return false;
        } else {
            Block block = p_77190_1_.getBlock(p_77190_2_, p_77190_3_ - 1, p_77190_4_);
            boolean spawnBlock = block.canCreatureSpawn(p_77190_0_, p_77190_1_, p_77190_2_, p_77190_3_ - 1, p_77190_4_);
            return spawnBlock && block != Blocks.bedrock
                    && !p_77190_1_.getBlock(p_77190_2_, p_77190_3_, p_77190_4_).isNormalCube()
                    && !p_77190_1_.getBlock(p_77190_2_, p_77190_3_, p_77190_4_).getMaterial().isLiquid()
                    && !p_77190_1_.getBlock(p_77190_2_, p_77190_3_ + 1, p_77190_4_).isNormalCube();
        }
    }
}
