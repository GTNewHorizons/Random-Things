package lumien.randomthings.Client.GUI;

import lumien.randomthings.Container.ContainerImbuingStation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class GuiImbuingStation extends GuiContainer
{

	public GuiImbuingStation()
	{
		super(new ContainerImbuingStation());
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		// TODO Auto-generated method stub
		
	}

}
