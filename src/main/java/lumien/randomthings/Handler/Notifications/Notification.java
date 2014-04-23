package lumien.randomthings.Handler.Notifications;

import net.minecraft.item.ItemStack;

public class Notification
{
	String title;
	String description;
	ItemStack icon;
	
	public Notification(String title,String description,ItemStack icon)
	{
		this.title = title;
		this.description = description;
		this.icon = icon;
	}
}
