package lumien.randomthings.Network;

import lumien.randomthings.Network.Messages.MessageAnswerTeleport;
import lumien.randomthings.Network.Messages.MessageBloodmoon;
import lumien.randomthings.Network.Messages.MessageChangeItemProperty;
import lumien.randomthings.Network.Messages.MessageContainerProperty;
import lumien.randomthings.Network.Messages.MessageItemCollector;
import lumien.randomthings.Network.Messages.MessageItemFilter;
import lumien.randomthings.Network.Messages.MessageMagneticForceParticle;
import lumien.randomthings.Network.Messages.MessageNotification;
import lumien.randomthings.Network.Messages.MessageOnlineDetector;
import lumien.randomthings.Network.Messages.MessagePaintBiome;
import lumien.randomthings.Network.Messages.MessageRequestTeleport;
import lumien.randomthings.Network.Messages.MessageSpectreData;
import lumien.randomthings.Network.Messages.MessageWhitestone;

public class PacketHandler {

    public static final RandomThingsNetworkWrapper INSTANCE = new RandomThingsNetworkWrapper();

    public static void init() {
        // INSTANCE.registerMessage(MessageEnderLetter.class,
        // MessageEnderLetter.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MessageItemCollector.class);
        INSTANCE.registerMessage(MessageItemFilter.class);
        INSTANCE.registerMessage(MessageNotification.class);
        INSTANCE.registerMessage(MessageOnlineDetector.class);
        INSTANCE.registerMessage(MessagePaintBiome.class);
        INSTANCE.registerMessage(MessageWhitestone.class);
        INSTANCE.registerMessage(MessageRequestTeleport.class);
        INSTANCE.registerMessage(MessageAnswerTeleport.class);
        INSTANCE.registerMessage(MessageMagneticForceParticle.class);
        INSTANCE.registerMessage(MessageChangeItemProperty.class);
        INSTANCE.registerMessage(MessageSpectreData.class);
        INSTANCE.registerMessage(MessageBloodmoon.class);
        INSTANCE.registerMessage(MessageContainerProperty.class);
    }
}
