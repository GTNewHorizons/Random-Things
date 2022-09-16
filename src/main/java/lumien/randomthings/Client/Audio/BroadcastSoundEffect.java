package lumien.randomthings.Client.Audio;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;

public class BroadcastSoundEffect extends PositionedSound {

    public BroadcastSoundEffect(ResourceLocation soundFile, float volume, float pitch) {
        super(soundFile);

        this.volume = volume;
        this.field_147663_c = pitch;
        this.field_147666_i = ISound.AttenuationType.NONE;
    }
}
