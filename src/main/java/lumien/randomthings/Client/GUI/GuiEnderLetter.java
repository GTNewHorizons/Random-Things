package lumien.randomthings.Client.GUI;

import lumien.randomthings.Container.ContainerEnderLetter;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Network.Messages.MessageChangeItemProperty;
import lumien.randomthings.Network.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiEnderLetter extends GuiContainer {

    final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/enderLetter.png");

    GuiTextField receiverName;
    ItemStack enderLetter;

    String oldReceiver = "";

    EntityPlayer thePlayer = Minecraft.getMinecraft().thePlayer;

    public GuiEnderLetter(IInventory inventoryPlayer, IInventory letterInventory, ItemStack enderLetter) {
        super(new ContainerEnderLetter(enderLetter, inventoryPlayer, letterInventory));
        this.enderLetter = enderLetter;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString(I18n.format("item.enderLetter.name"), 8, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        this.mc.renderEngine.bindTexture(background);
        // this.mc.renderEngine.bindTexture("/gui/demo_bg.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        receiverName.drawTextBox();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (!oldReceiver.equals(receiverName.getText())) {
            oldReceiver = receiverName.getText();
            PacketHandler.INSTANCE.sendToServer(
                    new MessageChangeItemProperty(
                            Item.getIdFromItem(ModItems.enderLetter),
                            0,
                            thePlayer.inventory.currentItem,
                            "receiver",
                            oldReceiver));
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        receiverName = new GuiTextField(
                this.fontRendererObj,
                (width - xSize) / 2 + 92,
                (height - ySize) / 2 + 5,
                76,
                10);
        receiverName.setFocused(false);
        receiverName.setCanLoseFocus(true);
        Keyboard.enableRepeatEvents(true);

        String receiver = enderLetter.stackTagCompound.getString("receiver");
        receiverName.setText(receiver);
        oldReceiver = receiver;

        if (enderLetter.getItemDamage() == 1) {
            receiverName.setEnabled(false);
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        PacketHandler.INSTANCE.sendToServer(
                new MessageChangeItemProperty(
                        Item.getIdFromItem(ModItems.enderLetter),
                        0,
                        thePlayer.inventory.currentItem,
                        "receiver",
                        receiverName.getText()));
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        this.receiverName.mouseClicked(par1, par2, par3);
    }

    @Override
    protected void keyTyped(char par1, int pressedKey) {
        if (pressedKey == Keyboard.KEY_ESCAPE || (!this.receiverName.isFocused()
                && pressedKey == this.mc.gameSettings.keyBindInventory.getKeyCode())) {
            this.mc.thePlayer.closeScreen();
        }
        if (receiverName.isFocused()) {
            this.receiverName.textboxKeyTyped(par1, pressedKey);
        }
    }
}
