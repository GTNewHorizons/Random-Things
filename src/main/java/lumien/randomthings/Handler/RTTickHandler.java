package lumien.randomthings.Handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Client.Renderer.RenderBlut;
import lumien.randomthings.Handler.Bloodmoon.ClientBloodmoonHandler;
import lumien.randomthings.Handler.Bloodmoon.ServerBloodmoonHandler;
import lumien.randomthings.RandomThings;

public class RTTickHandler {

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            MagneticForceHandler.INSTANCE.update();
            RandomThings.instance.letterHandler.update();
            if (RandomThings.instance.spectreHandler != null) {
                RandomThings.instance.spectreHandler.update();
            }
        }
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ServerBloodmoonHandler.INSTANCE.endWorldTick(event.world);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            RandomThings.instance.notificationHandler.update();
            ClientBloodmoonHandler.INSTANCE.tick();
            RenderBlut.counter += 0.01;
        }
    }
}
