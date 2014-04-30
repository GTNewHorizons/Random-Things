package lumien.randomthings.Handler;

import java.util.ArrayList;

import net.minecraftforge.client.event.sound.PlaySoundEvent17;

public class SoundRecorderHandler
{
	ArrayList<String> playedSounds;
	
	public SoundRecorderHandler()
	{
		playedSounds = new ArrayList<String>();
	}
	
	public void soundPlayed(PlaySoundEvent17 event)
	{
		if (playedSounds.size()>=10)
		{
			playedSounds.remove(0);
		}
		playedSounds.add(event.name);
	}
}
