package lumien.randomthings.Entity;

import lumien.randomthings.Handler.Spectre.TeleporterSpectre;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityReviveCircle extends Entity {
    public int age;
    EntitySoul toRevive;

    public EntityReviveCircle(World worldObj) {
        super(worldObj);

        age = 0;
        this.noClip = true;
        this.renderDistanceWeight = 5;
        this.ignoreFrustumCheck = true;
    }

    public EntityReviveCircle(World worldObj, double posX, double posY, double posZ, EntitySoul toRevive) {
        super(worldObj);

        this.setPosition(posX, posY, posZ);
        this.toRevive = toRevive;
        this.noClip = true;
        this.renderDistanceWeight = 5;
        this.ignoreFrustumCheck = true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        age++;

        if (!worldObj.isRemote) {
            if (toRevive.isDead) {
                this.kill();
            }

            if (this.age >= 200) {
                toRevive.setDead();
                this.setDead();

                EntityPlayerMP player =
                        MinecraftServer.getServer().getConfigurationManager().func_152612_a(toRevive.playerName);
                if (player != null) {
                    if (player.getHealth() <= 0) {
                        EntityPlayerMP revived =
                                player.playerNetServerHandler.playerEntity = MinecraftServer.getServer()
                                        .getConfigurationManager()
                                        .respawnPlayer(player, 0, false);
                        if (revived.worldObj.provider.dimensionId != this.dimension) {
                            MinecraftServer.getServer()
                                    .getConfigurationManager()
                                    .transferPlayerToDimension(
                                            revived,
                                            this.worldObj.provider.dimensionId,
                                            new TeleporterSpectre((WorldServer) this.worldObj));
                        }
                        revived.playerNetServerHandler.setPlayerLocation(
                                posX, posY, posZ, revived.rotationYaw, revived.rotationPitch);
                        revived.setPositionAndUpdate(posX, posY, posZ);
                    }
                }
            }
        }
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {}
}
