package lumien.randomthings.Handler;

import java.util.ArrayList;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import lumien.randomthings.Library.RandomThingsNBTKeys;
import lumien.randomthings.Network.Messages.MessageMagneticForceParticle;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.RandomThings;

public class MagneticForceHandler {

    public static int TELEPORT_LENGTH = 200; // 10 Seconds
    public static MagneticForceHandler INSTANCE = new MagneticForceHandler();
    ArrayList<MagneticForceEvent> events;

    public MagneticForceHandler() {
        this.events = new ArrayList<>();
    }

    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList tagList = nbt.getTagList(RandomThingsNBTKeys.MAGNETIC_FORCES, 9);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            MagneticForceEvent event = new MagneticForceEvent();
            event.readFromNBT(tag);
            events.add(event);
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList tagList = new NBTTagList();
        for (MagneticForceEvent event : events) {
            NBTTagCompound tag = new NBTTagCompound();
            event.writeToNBT(tag);
            tagList.appendTag(tag);
        }
        nbt.setTag(RandomThingsNBTKeys.MAGNETIC_FORCES, tagList);
    }

    public void addEvent(String user, String target) {
        events.add(new MagneticForceEvent(user, target));
        writeToNBT(RandomThings.instance.modNBT);
        RandomThings.instance.saveNBT();
    }

    private static class MagneticForceEvent {

        String user;
        String target;
        int progress;

        float f;

        public MagneticForceEvent(String user, String target) {
            this.user = user;
            this.target = target;
            this.progress = TELEPORT_LENGTH;
            f = 0;
        }

        public MagneticForceEvent() {}

        public void readFromNBT(NBTTagCompound nbt) {
            this.user = nbt.getString(RandomThingsNBTKeys.USER);
            this.target = nbt.getString(RandomThingsNBTKeys.TARGET);
            this.progress = nbt.getInteger(RandomThingsNBTKeys.PROGRESS);
        }

        public void writeToNBT(NBTTagCompound nbt) {
            nbt.setString(RandomThingsNBTKeys.USER, user);
            nbt.setString(RandomThingsNBTKeys.TARGET, target);
            nbt.setInteger(RandomThingsNBTKeys.PROGRESS, progress);
        }

        public boolean tick() {
            this.progress--;
            if (this.progress <= 0) {
                teleport();
                return true;
            } else {
                f += 0.1f;
                EntityPlayerMP userEntity = MinecraftServer.getServer().getConfigurationManager().func_152612_a(user);
                EntityPlayerMP targetEntity = MinecraftServer.getServer().getConfigurationManager()
                        .func_152612_a(target);

                if (userEntity != null) {
                    Set<EntityPlayer> trackingUser = ((WorldServer) userEntity.worldObj).getEntityTracker()
                            .getTrackingPlayers(userEntity);
                    PacketHandler.INSTANCE.sendTo(
                            new MessageMagneticForceParticle(
                                    userEntity.getEntityId(),
                                    userEntity.worldObj.provider.dimensionId,
                                    f),
                            userEntity);
                    for (EntityPlayer player : trackingUser) {
                        PacketHandler.INSTANCE.sendTo(
                                new MessageMagneticForceParticle(
                                        userEntity.getEntityId(),
                                        userEntity.worldObj.provider.dimensionId,
                                        f),
                                (EntityPlayerMP) player);
                    }
                }
                if (targetEntity != null) {
                    Set<EntityPlayer> trackingTarget = ((WorldServer) targetEntity.worldObj).getEntityTracker()
                            .getTrackingPlayers(targetEntity);
                    PacketHandler.INSTANCE.sendTo(
                            new MessageMagneticForceParticle(
                                    targetEntity.getEntityId(),
                                    targetEntity.worldObj.provider.dimensionId,
                                    f),
                            targetEntity);

                    for (EntityPlayer player : trackingTarget) {
                        PacketHandler.INSTANCE.sendTo(
                                new MessageMagneticForceParticle(
                                        targetEntity.getEntityId(),
                                        targetEntity.worldObj.provider.dimensionId,
                                        f),
                                (EntityPlayerMP) player);
                    }
                }

                return false;
            }
        }

        private void teleport() {
            EntityPlayerMP userEntity = MinecraftServer.getServer().getConfigurationManager().func_152612_a(user);
            EntityPlayerMP targetEntity = MinecraftServer.getServer().getConfigurationManager().func_152612_a(target);

            if (userEntity != null && targetEntity != null) {
                userEntity.mountEntity(null);
                if (userEntity.worldObj.provider.dimensionId != targetEntity.worldObj.provider.dimensionId) {
                    userEntity.travelToDimension(targetEntity.worldObj.provider.dimensionId);
                }
                userEntity.playerNetServerHandler.setPlayerLocation(
                        targetEntity.posX,
                        targetEntity.posY,
                        targetEntity.posZ,
                        targetEntity.rotationYaw,
                        targetEntity.rotationPitch);
            }
        }
    }

    public void update() {
        int size = events.size();
        events.removeIf(MagneticForceEvent::tick);
        if (size != events.size()) {
            writeToNBT(RandomThings.instance.modNBT);
            RandomThings.instance.saveNBT();
        }
    }
}
