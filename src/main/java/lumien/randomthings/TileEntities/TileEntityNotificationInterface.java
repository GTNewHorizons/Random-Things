package lumien.randomthings.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.registry.GameData;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import lumien.randomthings.Library.WorldUtils;
import lumien.randomthings.Network.Messages.MessageNotification;
import lumien.randomthings.Network.PacketHandler;

@Optional.InterfaceList(
        value = { @Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers"),
                @Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft") })
public class TileEntityNotificationInterface extends TileEntity implements IPeripheral, SimpleComponent {

    @Override
    @Optional.Method(modid = "OpenComputers")
    public String getComponentName() {
        return "notificationInterface";
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public String getType() {
        return "notificationInterface";
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public String[] getMethodNames() {
        return new String[] { "sendNotification" };
    }

    @Callback
    @Optional.Method(modid = "OpenComputers")
    public Object[] sendNotification(Context context, Arguments args) throws Exception {
        String receiver = args.checkString(0);
        String title = args.checkString(1);
        String description = args.checkString(2);
        int duration = args.checkInteger(3);
        String iconString = args.checkString(4);

        if (duration <= 0) {
            throw new Exception("Duration has to be > 0");
        }

        if (!WorldUtils.isPlayerOnline(receiver)) {
            throw new Exception("Selected Receiver is not Online");
        }

        int metadata = 0;

        if (args.count() >= 6) {
            metadata = args.checkInteger(5);
        }
        Item i = GameData.getItemRegistry().getObject(iconString);
        Block b = GameData.getBlockRegistry().getObject(iconString);

        ItemStack is;

        if (i != null) {
            is = new ItemStack(i, 1, metadata);
        } else if (b != null) {
            is = new ItemStack(b, 1, metadata);
        } else {
            throw new Exception("Invalid IconString");
        }

        EntityPlayerMP receiverEntity = MinecraftServer.getServer().getConfigurationManager().func_152612_a(receiver);
        if (receiverEntity == null) {
            return new Object[] {};
        }

        MessageNotification packet = new MessageNotification(title, description, duration, is);

        PacketHandler.INSTANCE.sendTo(packet, receiverEntity);

        return new Object[] {};
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments)
            throws Exception {
        if (method == 0) {
            if (arguments.length < 5) {
                throw new Exception(
                        "Usage: sendNotification(Receiver,Title,Description,DisplayDuration,IconString,(optional) metadata)");
            } else {
                String receiver = arguments[0] + "";
                String title = arguments[1] + "";
                String description = arguments[2] + "";
                int duration = (int) Math.floor(Double.parseDouble(arguments[3] + ""));

                String iconString = arguments[4] + "";

                int metadata = 0;
                if (arguments.length > 5) {
                    metadata = (int) Double.parseDouble(arguments[5] + "");
                }

                if (duration <= 0) {
                    throw new Exception("Duration has to be > 0");
                }

                if (!WorldUtils.isPlayerOnline(receiver)) {
                    throw new Exception("Selected Receiver is not Online");
                } else {
                    Item i = GameData.getItemRegistry().getObject(iconString);
                    Block b = GameData.getBlockRegistry().getObject(iconString);

                    ItemStack is;

                    if (i != null) {
                        is = new ItemStack(i, 1, metadata);
                    } else if (b != null) {
                        is = new ItemStack(b, 1, metadata);
                    } else {
                        throw new Exception("Invalid IconString");
                    }

                    EntityPlayerMP receiverEntity = MinecraftServer.getServer().getConfigurationManager()
                            .func_152612_a(receiver);
                    if (receiverEntity == null) {
                        throw new Exception("Player entity not found");
                    }

                    MessageNotification packet = new MessageNotification(title, description, duration, is);

                    PacketHandler.INSTANCE.sendTo(packet, receiverEntity);
                    return null;
                }
            }
        }
        throw new Exception("Usage: sendNotification(Receiver,Title,Description,IconString)");
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public void attach(IComputerAccess computer) {}

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public void detach(IComputerAccess computer) {}

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public boolean equals(IPeripheral other) {
        return false;
    }
}
