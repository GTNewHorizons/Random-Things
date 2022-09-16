package lumien.randomthings.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.RandomThings;
import lumien.randomthings.TileEntities.TileEntityImbuingStation;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockImbuingStation extends BlockContainerBase {
    IIcon[] icons;

    public BlockImbuingStation() {
        super("imbuingStation", Material.rock);

        this.setHardness(1.25F);
    }

    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.icons = new IIcon[3];
        this.icons[0] = ir.registerIcon("RandomThings:imbuingStation/imbuingStationBottom");
        this.icons[1] = ir.registerIcon("RandomThings:imbuingStation/imbuingStationTop");
        this.icons[2] = ir.registerIcon("RandomThings:imbuingStation/imbuingStationSide");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        if (side == 0) {
            return icons[0];
        } else if (side == 1) {
            return icons[1];
        } else {
            return icons[2];
        }
    }

    @Override
    protected <T extends TileEntity> Class getTileEntityClass() {
        return TileEntityImbuingStation.class;
    }

    @Override
    public boolean onBlockActivated(
            World worldObj,
            int posX,
            int posY,
            int posZ,
            EntityPlayer player,
            int par6,
            float par7,
            float par8,
            float par9) {
        if (!worldObj.isRemote) {
            player.openGui(RandomThings.instance, GuiIds.IMBUING_STATION, worldObj, posX, posY, posZ);
        }
        return true;
    }
}
