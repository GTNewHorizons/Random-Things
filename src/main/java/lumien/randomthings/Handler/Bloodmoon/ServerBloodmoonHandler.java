package lumien.randomthings.Handler.Bloodmoon;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;

import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Mixins.Minecraft.WorldAccessor;
import lumien.randomthings.Network.Messages.MessageBloodmoon;
import lumien.randomthings.Network.PacketHandler;

public class ServerBloodmoonHandler extends WorldSavedData {

    public static ServerBloodmoonHandler INSTANCE;

    private final BloodmoonSpawner bloodMoonSpawner;

    boolean bloodMoon;
    boolean forceBloodMoon;

    public ServerBloodmoonHandler() {
        super("Bloodmoon");
        bloodMoonSpawner = new BloodmoonSpawner();
        bloodMoon = false;
        forceBloodMoon = false;
    }

    public ServerBloodmoonHandler(String name) {
        super("Bloodmoon");
        bloodMoonSpawner = new BloodmoonSpawner();
        bloodMoon = false;
        forceBloodMoon = false;
    }

    public void playerJoinedWorld(EntityPlayer player) {
        if (bloodMoon) {
            PacketHandler.INSTANCE.sendTo(new MessageBloodmoon(bloodMoon), (EntityPlayerMP) player);
        }
    }

    public void endWorldTick(World world) {
        if (world.provider.dimensionId == 0) {
            int time = (int) (world.getWorldTime() % 24000);
            int date = (int) Math.floor(world.getWorldTime() / 24000d);

            if (bloodMoon) {
                boolean spawnHostiles = ((WorldAccessor) world).isSpawnHostileMobs();
                boolean doMobSpawning = world.getGameRules().getGameRuleBooleanValue("doMobSpawning");
                if (!Settings.BLOODMOON_RESPECT_GAMERULE || spawnHostiles && doMobSpawning) {
                    for (int i = 0; i < Settings.BLOODMOON_SPAWNSPEED; i++) {
                        bloodMoonSpawner.findChunksForSpawning(
                                (WorldServer) world,
                                true,
                                false,
                                world.getTotalWorldTime() % 20 == 0);
                    }
                }
                if (time >= 0 && time < 12000) {
                    setBloodmoon(false);
                }
            } else {
                if (time == 12000) {
                    if (forceBloodMoon || isBloodMoonCycle(date) || Math.random() < Settings.BLOODMOON_CHANCE) {
                        forceBloodMoon = false;
                        setBloodmoon(true);

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
        }
    }

    private void setBloodmoon(boolean bloodMoon) {
        if (this.bloodMoon != bloodMoon) {
            PacketHandler.INSTANCE.sendToDimension(new MessageBloodmoon(bloodMoon), 0);
            this.markDirty();
        }
        this.bloodMoon = bloodMoon;
    }

    public void force() {
        forceBloodMoon = true;
        this.markDirty();
    }

    public boolean isBloodmoonActive() {
        return bloodMoon;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.bloodMoon = nbt.getBoolean("bloodMoon");
        this.forceBloodMoon = nbt.getBoolean("forceBloodMoon");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("bloodMoon", bloodMoon);
        nbt.setBoolean("forceBloodMoon", forceBloodMoon);
    }

    public boolean isBloodmoonScheduled() {
        return forceBloodMoon;
    }

    public boolean isBloodMoonCycle(int day) {
        if (Settings.BLOODMOON_CYCLE > 0 && day > 0) {
            return day % Settings.BLOODMOON_CYCLE == 0;
        }
        return false;
    }
}
