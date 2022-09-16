package lumien.randomthings.Handler;

import java.util.ArrayList;
import lumien.randomthings.Configuration.ConfigItems;
import lumien.randomthings.Items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;

public class SoundRecorderHandler {
    public ArrayList<String> playedSounds;

    public SoundRecorderHandler() {
        playedSounds = new ArrayList<String>();
    }

    public void soundPlayed(PlaySoundEvent17 event) {
        if (ConfigItems.soundRecorder && Minecraft.getMinecraft().thePlayer != null) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player.inventory.hasItemStack(new ItemStack(ModItems.soundRecorder, 1, 1))) {
                if (playedSounds.size() >= 10) {
                    playedSounds.remove(9);
                }
                playedSounds.add(
                        0,
                        event.sound.getPositionedSoundLocation().getResourceDomain() + ":"
                                + event.sound.getPositionedSoundLocation().getResourcePath());
            }
        }
    }
}
