package lumien.randomthings.Client.GUI.Elements.Buttons;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

public class GuiButtonBooleanProperty extends GuiButton
{
	boolean enabled;
	String propertyName;
	
	public GuiButtonBooleanProperty(GuiContainer gc, int id, int posX, int posY)
	{
		super(id, posX, posY, "");
	}

}
