package lumien.randomthings.Library;

import lumien.randomthings.Client.Audio.BroadcastSoundEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class ClientUtil
{
	public static void broadcastEffect(String s, float volume, float pitch)
	{
		BroadcastSoundEffect toPlay = new BroadcastSoundEffect(new ResourceLocation("randomthings:" + s), 1, 1);
		Minecraft.getMinecraft().getSoundHandler().playSound(toPlay);
	}

	public static String translate(String key)
	{
		return I18n.format(key, new Object[] {});
	}
}
