package lumien.randomthings.Mixins.early;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;

import lumien.randomthings.Handler.CoreHandler;

@Mixin(BlockLeavesBase.class)
public abstract class MixinBlockLeavesBase extends Block {

    protected MixinBlockLeavesBase(Material p_i45394_1_) {
        super(p_i45394_1_);
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_,
            Block p_149695_5_) {
        CoreHandler.handleLeaveDecay(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, this);
    }
}
