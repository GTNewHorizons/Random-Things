package lumien.randomthings.TileEntities;

import java.util.ArrayDeque;

import net.minecraft.tileentity.TileEntity;

public class TileEntityFogGenerator extends TileEntity {

    public static ArrayDeque<TileEntityFogGenerator> loadedFogGenerators = new ArrayDeque<>();

    public TileEntityFogGenerator() {
        loadedFogGenerators.add(this);
    }

    @Override
    public void invalidate() {
        super.invalidate();

        if (worldObj.isRemote) loadedFogGenerators.remove(this);
    }
}
