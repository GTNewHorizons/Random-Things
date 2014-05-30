package lumien.randomthings.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.ConfigItems;

public class ModItems
{
	public static ItemFilter filter;
	public static ItemBiomeSolution biomeSolution;
	public static ItemBiomePainter biomePainter;
	public static ItemIngredient ingredients;
	public static ItemKojaku kojaku;
	public static ItemWhiteStone whitestone;
	public static ItemMagneticForce magneticForce;
	public static ItemVoidStone voidStone;
	public static ItemDropFilter dropFilter;
	public static ItemEnderLetter enderLetter;
	public static ItemSpectreKey spectreKey;
	public static ItemSoundRecorder soundRecorder;
	
	public static ItemCreativeSword creativeSword;
	public static ItemCreativeGrower creativeGrower;
	public static ItemCreativeChestGenerator creativeChestGenerator;
	
	public static ArmorMaterial spectreArmorMaterial = EnumHelper.addArmorMaterial("Spectre", 35, new int[]{3, 9, 7, 3}, 17);
	public static ItemSpectreArmor spectreHelmet;
	public static ItemSpectreArmor spectreChestplate;
	public static ItemSpectreArmor spectreLeggings;
	public static ItemSpectreArmor spectreBoots;
	public static ItemSpectreSword spectreSword;

	public static void init()
	{
		RandomThings.instance.logger.debug("Initializing Items");
		
		if (ConfigItems.biomeSolution)
			biomeSolution = new ItemBiomeSolution();
		if (ConfigItems.biomePainter)
			biomePainter = new ItemBiomePainter();
		if (ConfigItems.whitestone)
			whitestone = new ItemWhiteStone();
		if (ConfigItems.magneticForce)
			magneticForce = new ItemMagneticForce();
		if (ConfigItems.voidStone)
			voidStone = new ItemVoidStone();
		if (ConfigItems.dropFilter)
			dropFilter = new ItemDropFilter();
		if (ConfigItems.enderLetter)
			enderLetter = new ItemEnderLetter();
		if (ConfigItems.spectreKey)
			spectreKey = new ItemSpectreKey();
		if (ConfigItems.spectreArmor)
		{
			spectreHelmet = (ItemSpectreArmor) new ItemSpectreArmor(0).setUnlocalizedName("spectreHelmet").setTextureName("RandomThings:armor/spectreHelmet");
			spectreChestplate = (ItemSpectreArmor) new ItemSpectreArmor(1).setUnlocalizedName("spectreChestplate").setTextureName("RandomThings:armor/spectreChestplate");
			spectreLeggings = (ItemSpectreArmor) new ItemSpectreArmor(2).setUnlocalizedName("spectreLeggings").setTextureName("RandomThings:armor/spectreLeggings");
			spectreBoots = (ItemSpectreArmor) new ItemSpectreArmor(3).setUnlocalizedName("spectreBoots").setTextureName("RandomThings:armor/spectreBoots");
			GameRegistry.registerItem(spectreHelmet, "spectreHelmet");
			GameRegistry.registerItem(spectreChestplate, "spectreChestplate");
			GameRegistry.registerItem(spectreLeggings, "spectreLeggings");
			GameRegistry.registerItem(spectreBoots, "spectreBoots");
		}
		if (ConfigItems.spectreSword)
			spectreSword = new ItemSpectreSword();
		
		if (ConfigItems.creativeSword)
			creativeSword = new ItemCreativeSword();
		if (ConfigItems.creativeGrower)
			creativeGrower = new ItemCreativeGrower();
		if (ConfigItems.creativeChestGenerator)
			creativeChestGenerator = new ItemCreativeChestGenerator();
		if (ConfigItems.soundRecorder)
			soundRecorder = new ItemSoundRecorder();
		
		ingredients = new ItemIngredient();
		filter = new ItemFilter();
		
		kojaku = new ItemKojaku();
	}
}
