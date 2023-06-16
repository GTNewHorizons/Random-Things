package lumien.randomthings.Items;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.RandomThings;

public class ItemBiomeCapsule extends ItemBase {

    public static HashMap<Integer, Integer> biomeColors;
    static Random rng = new Random();

    static final float modColor = 1F / 255F;

    public ItemBiomeCapsule() {
        super("biomeCapsule");
        this.setMaxStackSize(1);

        biomeColors = new HashMap<>();
        {
            biomeColors.put(0, 1452177); // Ozean
            biomeColors.put(7, 4303848); // Fluss
            biomeColors.put(8, 6029312); // Nether
            biomeColors.put(9, 8223332); // The End
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldObj, Entity entity, int slot, boolean p_77663_5_) {
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 20 == 0 && stack.getItemDamage() != 0) {
            int biomeID = stack.getItemDamage() - 1;
            BiomeGenBase biome = BiomeGenBase.getBiome(biomeID);
            if (biome == null && entity instanceof EntityPlayer) {
                ((EntityPlayer) entity).inventory.setInventorySlotContents(slot, null);
                ((EntityPlayer) entity).inventoryContainer.detectAndSendChanges();
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (stack.getItemDamage() != 0) {
            NBTTagCompound nbt = stack.stackTagCompound;
            if (nbt == null) {
                nbt = stack.stackTagCompound = new NBTTagCompound();
                nbt.setInteger("charges", 0);
            }

            par3List.add("Charges: " + nbt.getInteger("charges") + " / " + getMaxDamage(stack));
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 256;
    }

    @Override
    public int getDisplayDamage(ItemStack stack) {
        if (stack.getItemDamage() != 0) {
            NBTTagCompound nbt = stack.stackTagCompound;
            if (nbt == null) {
                nbt = stack.stackTagCompound = new NBTTagCompound();
                nbt.setInteger("charges", 0);
            }

            return 256 - nbt.getInteger("charges");
        }
        return 0;
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon("RandomThings:biomeCapsule");
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        if (entityItem.worldObj.isRemote) {
            ItemStack is = entityItem.getEntityItem();
            BiomeGenBase biome = entityItem.worldObj
                    .getBiomeGenForCoords((int) Math.floor(entityItem.posX), (int) Math.floor(entityItem.posZ));

            NBTTagCompound nbt = is.stackTagCompound;
            if (nbt == null) {
                nbt = is.stackTagCompound = new NBTTagCompound();
                nbt.setInteger("charges", 0);
            }

            int charges = is.stackTagCompound.getInteger("charges");
            int biomeID = is.getItemDamage() - 1;
            if (charges < 256 && biomeID != -1 && biome.biomeID == biomeID) {
                int intColor = getColorForBiome(BiomeGenBase.getBiome(biomeID));
                Color c = new Color(intColor);
                RandomThings.proxy.spawnColoredDust(
                        entityItem.posX,
                        entityItem.posY + 0.1,
                        entityItem.posZ,
                        0,
                        0,
                        0,
                        modColor * c.getRed(),
                        modColor * c.getGreen(),
                        modColor * c.getBlue());
            }
        } else {
            if (entityItem.getEntityItem().getItemDamage() == 0) {
                NBTTagCompound nbt = entityItem.getEntityData();
                if (nbt.getInteger("selectingTimer") == 0) {
                    nbt.setInteger("selectingTimer", 200);
                } else {
                    int currentTime = nbt.getInteger("selectingTimer");
                    if (currentTime == 1) {
                        BiomeGenBase biome = entityItem.worldObj.getBiomeGenForCoords(
                                (int) Math.floor(entityItem.posX),
                                (int) Math.floor(entityItem.posZ));
                        ItemStack is = entityItem.getEntityItem();
                        is.setItemDamage(biome.biomeID + 1);
                        entityItem.setEntityItemStack(is);
                    } else {
                        currentTime--;
                        nbt.setInteger("selectingTimer", currentTime);
                    }
                }
            } else {
                entityItem.age = 0;
                ItemStack is = entityItem.getEntityItem();
                NBTTagCompound nbt = is.stackTagCompound;
                if (nbt == null) {
                    nbt = is.stackTagCompound = new NBTTagCompound();
                    nbt.setInteger("charges", 0);
                }
                int charges = is.stackTagCompound.getInteger("charges");
                int biomeID = is.getItemDamage() - 1;
                if (charges < 256 && entityItem.worldObj.getTotalWorldTime() % Settings.BIOME_CHARGE_TIME == 0) {
                    int itemPosX = (int) Math.floor(entityItem.posX);
                    int itemPosY = (int) Math.floor(entityItem.posY);
                    int itemPosZ = (int) Math.floor(entityItem.posZ);

                    int foundBiomeID = entityItem.worldObj.getBiomeGenForCoords(itemPosX, itemPosZ).biomeID;
                    if (foundBiomeID == biomeID) {
                        charges++;
                        is.stackTagCompound.setInteger("charges", charges);
                    }
                }
            }
        }
        return false;
    }

    public static int getColorForBiome(BiomeGenBase b) {
        if (biomeColors.containsKey(b.biomeID)) {
            return biomeColors.get(b.biomeID);
        } else {
            return b.getBiomeFoliageColor(0, 0, 0);
        }
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        if (par1ItemStack.getItemDamage() == 0) {
            return 16777215;
        } else {
            int biomeID = par1ItemStack.getItemDamage() - 1;
            BiomeGenBase biome = BiomeGenBase.getBiome(biomeID);
            if (biome != null) {
                return getColorForBiome(biome);
            } else {
                return BiomeGenBase.ocean.getBiomeFoliageColor(0, 0, 0);
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        String biomeName;
        if (par1ItemStack.getItemDamage() == 0) {
            return ("" + StatCollector.translateToLocal("item.biomeCapsule" + ".name")).trim();
        } else {
            int biomeID = par1ItemStack.getItemDamage() - 1;
            BiomeGenBase biome = BiomeGenBase.getBiome(biomeID);
            if (biome != null) {
                biomeName = biome.biomeName;
            } else {
                biomeName = "Invalid";
            }
        }
        return biomeName + " Capsule";
    }
}
