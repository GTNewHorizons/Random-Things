package lumien.randomthings.Network;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Network.Messages.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(RandomThings.MOD_ID.toLowerCase());

    public static void init()
    {
    	INSTANCE.registerMessage(MessageEnderLetter.class, MessageEnderLetter.class, 0, Side.SERVER);
    	INSTANCE.registerMessage(MessageItemCollector.class, MessageItemCollector.class, 1, Side.SERVER);
    	INSTANCE.registerMessage(MessageItemFilter.class, MessageItemFilter.class, 2, Side.SERVER);
    	INSTANCE.registerMessage(MessageNotification.class, MessageNotification.class, 3, Side.CLIENT);
    	INSTANCE.registerMessage(MessageOnlineDetector.class, MessageOnlineDetector.class, 4, Side.SERVER);
    	INSTANCE.registerMessage(MessagePaintBiome.class, MessagePaintBiome.class, 5, Side.CLIENT);
    	INSTANCE.registerMessage(MessageWhitestone.class, MessageWhitestone.class, 6, Side.CLIENT);
    	// Magnetic Force
    	INSTANCE.registerMessage(MessageRequestTeleport.class, MessageRequestTeleport.class, 7, Side.SERVER);
    	INSTANCE.registerMessage(MessageAnswerTeleport.class, MessageAnswerTeleport.class, 8, Side.CLIENT);
    	INSTANCE.registerMessage(MessageMagneticForceParticle.class, MessageMagneticForceParticle.class, 9, Side.CLIENT);
    	// Bloodmoon
    	INSTANCE.registerMessage(MessageBloodMoon.class, MessageBloodMoon.class, 10, Side.CLIENT);
    }
}
