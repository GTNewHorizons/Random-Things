package lumien.randomthings.Client.GUI;

import lumien.randomthings.Client.GUI.Elements.Buttons.GuiButtonBooleanProperty;
import lumien.randomthings.Client.GUI.Elements.Buttons.GuiButtonListtype;
import lumien.randomthings.Client.GUI.Elements.Buttons.GuiButtonOreDictionary;
import lumien.randomthings.Container.ContainerItemFilter;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Library.ClientUtil;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.Network.Messages.MessageItemFilter;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiItemFilter extends GuiContainer
{
	final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/itemFilter.png");
	EntityPlayer player;
	ItemStack itemFilter;

	GuiButtonBooleanProperty oreDictButton;
	GuiButtonBooleanProperty metadataButton;
	GuiButtonListtype listTypeButton;

	public GuiItemFilter(ItemStack filter, EntityPlayer player, IInventory inventoryPlayer, IInventory inventoryFilter)
	{
		super(new ContainerItemFilter(filter, inventoryPlayer, inventoryFilter));

		xSize = 220;
		ySize = 133;

		this.player = player;
		this.itemFilter = player.getCurrentEquippedItem();
	}

	@Override
	public void initGui()
	{
		super.initGui();

		metadataButton = new GuiButtonBooleanProperty(this, "metadata", Item.getIdFromItem(ModItems.filter), 1, player.inventory.currentItem, 0, guiLeft + 195, guiTop + 4, new ResourceLocation("randomthings:textures/gui/buttonMetadata.png"), ClientUtil.translate("text.miscellaneous.useMetadata"), ClientUtil.translate("text.miscellaneous.ignoreMetadata"));
		metadataButton.setValue(itemFilter.stackTagCompound.getBoolean("metadata"));
		this.buttonList.add(metadataButton);
		
		oreDictButton = new GuiButtonBooleanProperty(this, "oreDict", Item.getIdFromItem(ModItems.filter), 1, player.inventory.currentItem, 0, guiLeft + 173, guiTop + 4, new ResourceLocation("randomthings:textures/gui/buttonOreDict.png"), ClientUtil.translate("text.miscellaneous.useOreDict"), ClientUtil.translate("text.miscellaneous.ignoreOreDict"));
		oreDictButton.setValue(itemFilter.stackTagCompound.getBoolean("oreDict"));
		this.buttonList.add(oreDictButton);

		listTypeButton = new GuiButtonListtype(this, 1, guiLeft + 173, guiTop + 4 + 22, itemFilter.stackTagCompound.getInteger("listType"));
		this.buttonList.add(listTypeButton);
	}

	@Override
	protected void actionPerformed(GuiButton pressedButton)
	{
		if (pressedButton == listTypeButton)
		{
			int type = listTypeButton.getType();
			listTypeButton.setType(type == 0 ? 1 : 0);
			PacketHandler.INSTANCE.sendToServer(new MessageItemFilter(MessageItemFilter.ACTION.LISTTYPE));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.renderEngine.bindTexture(background);
		// this.mc.renderEngine.bindTexture("/gui/demo_bg.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRendererObj.drawString(I18n.format("item.filterItem.name", new Object[0]), 8, 6, 4210752);
	}
}
