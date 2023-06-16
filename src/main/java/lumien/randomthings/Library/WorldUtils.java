package lumien.randomthings.Library;

import java.util.HashSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import lumien.randomthings.Library.Interfaces.IValidator;

public class WorldUtils {

    public static void setConnectedBlocksTo(World worldObj, int startX, int startY, int startZ, Block b, int metadata,
            Block originalBlock, int originalMetadata) {
        Block atPositionBlock = worldObj.getBlock(startX, startY, startZ);
        int atPositionMetadata = worldObj.getBlockMetadata(startX, startY, startZ);
        if ((atPositionBlock != b || atPositionMetadata != metadata)
                && !atPositionBlock.isAir(worldObj, startX, startY, startZ)) {
            if (atPositionBlock == originalBlock && atPositionMetadata == originalMetadata) {
                worldObj.setBlock(startX, startY, startZ, b, metadata, 2);

                try {
                    setConnectedBlocksTo(
                            worldObj,
                            startX,
                            startY + 1,
                            startZ,
                            b,
                            metadata,
                            originalBlock,
                            originalMetadata);
                    setConnectedBlocksTo(
                            worldObj,
                            startX,
                            startY - 1,
                            startZ,
                            b,
                            metadata,
                            originalBlock,
                            originalMetadata);

                    setConnectedBlocksTo(
                            worldObj,
                            startX + 1,
                            startY,
                            startZ,
                            b,
                            metadata,
                            originalBlock,
                            originalMetadata);
                    setConnectedBlocksTo(
                            worldObj,
                            startX - 1,
                            startY,
                            startZ,
                            b,
                            metadata,
                            originalBlock,
                            originalMetadata);

                    setConnectedBlocksTo(
                            worldObj,
                            startX,
                            startY,
                            startZ + 1,
                            b,
                            metadata,
                            originalBlock,
                            originalMetadata);
                    setConnectedBlocksTo(
                            worldObj,
                            startX,
                            startY,
                            startZ - 1,
                            b,
                            metadata,
                            originalBlock,
                            originalMetadata);
                } catch (StackOverflowError ignored) {}
            }
        }
    }

    public static void dropItemStack(World world, double x, double y, double z, ItemStack stack) {
        if (!world.isRemote) {
            float f = 0.7F;
            double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }

    public static void notifyStrong(World worldObj, int posX, int posY, int posZ, Block block) {
        worldObj.notifyBlocksOfNeighborChange(posX, posY - 1, posZ, block);
        worldObj.notifyBlocksOfNeighborChange(posX, posY + 1, posZ, block);
        worldObj.notifyBlocksOfNeighborChange(posX - 1, posY, posZ, block);
        worldObj.notifyBlocksOfNeighborChange(posX + 1, posY, posZ, block);
        worldObj.notifyBlocksOfNeighborChange(posX, posY, posZ - 1, block);
        worldObj.notifyBlocksOfNeighborChange(posX, posY, posZ + 1, block);
    }

    public static void generateCube(World worldObj, int posX1, int posY1, int posZ1, int posX2, int posY2, int posZ2,
            Block b, int metadata, int flag) {
        int minX = Math.min(posX1, posX2);
        int minY = Math.min(posY1, posY2);
        int minZ = Math.min(posZ1, posZ2);

        int maxX = Math.max(posX1, posX2);
        int maxY = Math.max(posY1, posY2);
        int maxZ = Math.max(posZ1, posZ2);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if (x == minX || y == minY || z == minZ || x == maxX || y == maxY || z == maxZ) {
                        worldObj.setBlock(x, y, z, b, metadata, flag);
                    }
                }
            }
        }
    }

    public static boolean isPlayerOnline(String username) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            return MinecraftServer.getServer().getConfigurationManager().func_152612_a(username) != null;
        } else {
            NetHandlerPlayClient netclienthandler = Minecraft.getMinecraft().thePlayer.sendQueue;
            List list = netclienthandler.playerInfoList;

            for (Object o : list) {
                GuiPlayerInfo guiplayerinfo = (GuiPlayerInfo) o;
                if (guiplayerinfo.name.equalsIgnoreCase(username)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static HashSet<TileEntity> getConnectedTEs(World worldObj, int posX, int posY, int posZ,
            IValidator validator) {
        HashSet<TileEntity> tes = new HashSet<>();
        recConnectedTEs(tes, worldObj, posX, posY, posZ, validator);
        return tes;
    }

    public static void recConnectedTEs(HashSet<TileEntity> tes, World worldObj, int posX, int posY, int posZ,
            IValidator validator) {
        TileEntity te = worldObj.getTileEntity(posX, posY, posZ);
        if (te != null && !tes.contains(te) && validator.matches(te)) {
            tes.add(te);

            recConnectedTEs(tes, worldObj, posX - 1, posY, posZ, validator);
            recConnectedTEs(tes, worldObj, posX + 1, posY, posZ, validator);

            recConnectedTEs(tes, worldObj, posX, posY + 1, posZ, validator);
            recConnectedTEs(tes, worldObj, posX, posY - 1, posZ, validator);

            recConnectedTEs(tes, worldObj, posX, posY, posZ + 1, validator);
            recConnectedTEs(tes, worldObj, posX, posY, posZ - 1, validator);
        }
    }
}
