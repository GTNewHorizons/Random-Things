package lumien.randomthings.Library;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockPattern {
    private static class BlockInfo {
        Block block;
        int metadata;

        int modX;
        int modY;
        int modZ;

        private BlockInfo(Block block, int metadata, int modX, int modY, int modZ) {
            this.block = block;
            this.metadata = metadata;

            this.modX = modX;
            this.modY = modY;
            this.modZ = modZ;
        }
    }

    ArrayList<BlockInfo> blockInfos;

    public BlockPattern() {
        blockInfos = new ArrayList<>();
    }

    public void addBlock(Block block, int metadata, int modX, int modY, int modZ) {
        this.blockInfos.add(new BlockInfo(block, metadata, modX, modY, modZ));
    }

    public void place(World worldObj, int posX, int posY, int posZ) {
        for (BlockInfo info : blockInfos) {
            worldObj.setBlock(posX + info.modX, posY + info.modY, posZ + info.modZ, info.block, info.metadata, 3);
        }
    }

    public boolean matches(World worldObj, int posX, int posY, int posZ) {
        for (BlockInfo info : blockInfos) {
            if (worldObj.getBlock(posX + info.modX, posY + info.modY, posZ + info.modZ) != info.block
                    || worldObj.getBlockMetadata(posX + info.modX, posY + info.modY, posZ + info.modZ)
                            != info.metadata) {
                return false;
            }
        }
        return true;
    }
}
