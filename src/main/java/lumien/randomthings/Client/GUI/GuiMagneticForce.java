package lumien.randomthings.Client.GUI;

import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Client.GUI.Elements.GuiSlotPlayerList;
import lumien.randomthings.Container.ContainerMagneticForce;
import lumien.randomthings.Library.Interfaces.IPlayerListGUI;
import lumien.randomthings.Network.Messages.MessageAnswerTeleport;
import lumien.randomthings.Network.Messages.MessageRequestTeleport;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.RandomThings;

public class GuiMagneticForce extends GuiContainer implements IPlayerListGUI {

    final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/magneticForce.png");
    GuiSlotPlayerList playerList;
    ArrayList<String> players;

    boolean selected;

    int counter;

    MessageAnswerTeleport.STATUS status;

    public GuiMagneticForce() {
        super(new ContainerMagneticForce());

        this.xSize = 122;
        this.ySize = 135;
        counter = 0;

        status = null;

        selected = false;
    }

    @Override
    public void pressedPlayer(String player) {
        if (!selected) {
            selected = true;

            MessageRequestTeleport request = new MessageRequestTeleport();
            request.setUsername(player);
            PacketHandler.INSTANCE.sendToServer(request);
        }
    }

    public void setStatus(MessageAnswerTeleport.STATUS status) {
        this.status = status;
        selected = false;
    }

    @Override
    public void initGui() {
        super.initGui();

        int guiX = (width / 2 - xSize / 2);
        int guiY = (height / 2 - ySize / 2);

        players = RandomThings.proxy.getUsernameList();
        players.remove(mc.thePlayer.getCommandSenderName());

        playerList = new GuiSlotPlayerList(
                this,
                this.mc,
                100,
                110,
                guiX + xSize / 2 - 100 / 2,
                guiY - 10 + ySize / 2 - 100 / 2 + 10,
                players);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        counter++;

        if (counter % 5 == 0) {
            players.removeAll(new ArrayList<>(players));
            players.addAll(RandomThings.proxy.getUsernameList());

            if (!mc.thePlayer.getCommandSenderName().equals(RandomThings.AUTHOR_USERNAME))
                players.remove(mc.thePlayer.getCommandSenderName());
        }
    }

    public int getGuiLeft() {
        return guiLeft;
    }

    public int getGuiTop() {
        return guiTop;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        if (status != null) {
            String toDraw = "";
            switch (status) {
                case INVALID_USERNAME:
                    toDraw = I18n.format("text.magneticForce.invalidUsername");
                    break;
                case NOT_ONLINE:
                    toDraw = I18n.format("text.magneticForce.notOnline");
                    break;
                case NO_RIGHT:
                    toDraw = I18n.format("text.magneticForce.noRights");
                    break;
                case SAME_PLAYER:
                    toDraw = I18n.format("text.magneticForce.notYourself");
                    break;
                default:
                    break;
            }
            this.drawCenteredString(fontRendererObj, toDraw, xSize / 2, 5, 0xFFFFFF);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        this.mc.renderEngine.bindTexture(background);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int p_571_1_, int p_571_2_, float p_571_3_) {
        super.drawScreen(p_571_1_, p_571_2_, p_571_3_);
        GL11.glDisable(GL11.GL_LIGHTING);
        playerList.drawScreen(p_571_1_, p_571_2_, p_571_3_);
        GL11.glEnable(GL11.GL_LIGHTING);
    }
}
