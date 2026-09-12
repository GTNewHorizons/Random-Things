package lumien.randomthings.Handler.Bloodmoon;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;

import org.apache.commons.lang3.ArrayUtils;

import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Mixins.ducks.SpawnerAnimalsExt;
import lumien.randomthings.Mixins.early.WorldAccessor;
import lumien.randomthings.Mixins.early.WorldServerAccessor;
import lumien.randomthings.Network.Messages.MessageBloodmoon;
import lumien.randomthings.Network.PacketHandler;

public class ServerBloodmoonHandler extends WorldSavedData {

    public static ServerBloodmoonHandler INSTANCE;

    private final Set<Integer> activeBloodMoons = new HashSet<>();
    private final Set<Integer> forceBloodMoonDims = new HashSet<>();
    private final Map<Integer, Boolean> nightMap = new HashMap<>();

    public ServerBloodmoonHandler() {
        super("Bloodmoon");
    }

    public ServerBloodmoonHandler(String name) {
        super("Bloodmoon");
    }

    public void playerJoinedWorld(EntityPlayer player) {
        int dim = player.worldObj.provider.dimensionId;
        if (activeBloodMoons.contains(dim)) {
            PacketHandler.INSTANCE.sendTo(new MessageBloodmoon(true), (EntityPlayerMP) player);
        } else {
            PacketHandler.INSTANCE.sendTo(new MessageBloodmoon(false), (EntityPlayerMP) player);
        }
    }

    public void endWorldTick(World world) {
        int dim = world.provider.dimensionId;

        if (Settings.BLOODMOON_DIM_WHITELIST.length != 0
                && !ArrayUtils.contains(Settings.BLOODMOON_DIM_WHITELIST, dim)) {
            return;
        }

        float angle = world.getCelestialAngle(1.0F);
        // 0.215 (dusk), 0.785 (dawn)
        boolean isNight = angle >= 0.215F && angle <= 0.785F;
        boolean wasNight = nightMap.getOrDefault(dim, isNight);
        nightMap.put(dim, isNight);

        boolean duskJustStarted = isNight && !wasNight;
        boolean dawnJustStarted = !isNight && wasNight;
        int date = (int) Math.floor(world.getWorldTime() / 24000d);

        boolean bloodMoon = activeBloodMoons.contains(dim);
        boolean forced = forceBloodMoonDims.contains(dim);

        if (bloodMoon) {
            boolean spawnHostiles = ((WorldAccessor) world).isSpawnHostileMobs();
            boolean doMobSpawning = world.getGameRules().getGameRuleBooleanValue("doMobSpawning");
            if (!Settings.BLOODMOON_RESPECT_GAMERULE || (spawnHostiles && doMobSpawning)) {
                for (int i = 0; i < Settings.BLOODMOON_SPAWNSPEED; i++) {
                    final SpawnerAnimals spawnerAnimals = ((WorldServerAccessor) world).getAnimalSpawner();
                    final SpawnerAnimalsExt accessor = (SpawnerAnimalsExt) (Object) spawnerAnimals;
                    accessor.rt$setBloodmoon(true);
                    spawnerAnimals.findChunksForSpawning(
                            (WorldServer) world,
                            true,
                            false,
                            world.getTotalWorldTime() % 20 == 0);
                    accessor.rt$setBloodmoon(false);
                }
            }
            if (dawnJustStarted) {
                setBloodmoon(false, dim);
            }
        } else if (duskJustStarted) {
            if (forced || (date >= Settings.BLOODMOON_INITIAL_PAUSE
                    && (isBloodMoonCycle(date) || Math.random() < Settings.BLOODMOON_CHANCE))) {
                if (forced) {
                    forceBloodMoonDims.remove(dim);
                    this.markDirty();
                }
                setBloodmoon(true, dim);

                if (Settings.BLOODMOON_MESSAGE) {
                    for (EntityPlayer player : ((List<EntityPlayer>) world.playerEntities)) {
                        player.addChatMessage(
                                new ChatComponentTranslation("text.bloodmoon.notify")
                                        .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                    }
                }
            }
        }
    }

    private void setBloodmoon(boolean bloodMoon, int dimID) {
        if (activeBloodMoons.contains(dimID) != bloodMoon) {
            if (bloodMoon) {
                activeBloodMoons.add(dimID);
            } else {
                activeBloodMoons.remove(dimID);
            }
            PacketHandler.INSTANCE.sendToDimension(new MessageBloodmoon(bloodMoon), dimID);
            this.markDirty();
        }
    }

    public void force(int dimID) {
        forceBloodMoonDims.add(dimID);
        this.markDirty();
    }

    public boolean isBloodmoonActive(int dimID) {
        return activeBloodMoons.contains(dimID);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        activeBloodMoons.clear();
        forceBloodMoonDims.clear();

        if (nbt.hasKey("bloodMoon") && nbt.getBoolean("bloodMoon")) {
            activeBloodMoons.add(0);
        }
        if (nbt.hasKey("forceBloodMoon") && nbt.getBoolean("forceBloodMoon")) {
            forceBloodMoonDims.add(0);
        }

        if (nbt.hasKey("activeBloodMoons")) {
            for (int dim : nbt.getIntArray("activeBloodMoons")) {
                activeBloodMoons.add(dim);
            }
        }
        if (nbt.hasKey("forceBloodMoonDims")) {
            for (int dim : nbt.getIntArray("forceBloodMoonDims")) {
                forceBloodMoonDims.add(dim);
            }
        }
        if (nbt.hasKey("nightDims")) {
            for (int dim : nbt.getIntArray("nightDims")) {
                nightMap.put(dim, true);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        int[] activeArray = new int[activeBloodMoons.size()];
        int index = 0;
        for (Integer dim : activeBloodMoons) {
            activeArray[index++] = dim;
        }
        nbt.setIntArray("activeBloodMoons", activeArray);

        int[] forcedArray = new int[forceBloodMoonDims.size()];
        index = 0;
        for (Integer dim : forceBloodMoonDims) {
            forcedArray[index++] = dim;
        }
        nbt.setIntArray("forceBloodMoonDims", forcedArray);

        int nightCount = 0;
        for (Boolean isNight : nightMap.values()) {
            if (isNight) {
                nightCount++;
            }
        }
        int[] nightArray = new int[nightCount];
        index = 0;
        for (Map.Entry<Integer, Boolean> entry : nightMap.entrySet()) {
            if (entry.getValue()) {
                nightArray[index++] = entry.getKey();
            }
        }
        nbt.setIntArray("nightDims", nightArray);
    }

    public boolean isBloodmoonScheduled(int dimID) {
        return forceBloodMoonDims.contains(dimID);
    }

    public boolean isBloodMoonCycle(int day) {
        if (Settings.BLOODMOON_CYCLE > 0 && day > 0) {
            return day % Settings.BLOODMOON_CYCLE == 0;
        }
        return false;
    }
}
