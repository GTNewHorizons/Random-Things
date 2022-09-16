package lumien.randomthings.TileEntities;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

@Optional.InterfaceList(
        value = {
            @Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers"),
            @Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft")
        })
public class TileEntityCreativePlayerInterface extends TileEntityPlayerInterface
        implements SimpleComponent, IPeripheral {
    @Override
    @Optional.Method(modid = "OpenComputers")
    public String getComponentName() {
        return "playerinterface";
    }

    @Callback
    @Optional.Method(modid = "OpenComputers")
    public Object[] getPlayerName(Context context, Arguments args) {
        return new Object[] {this.getPlayerName()};
    }

    @Callback
    @Optional.Method(modid = "OpenComputers")
    public Object[] setPlayerName(Context context, Arguments args) {
        this.setPlayerName(args.checkString(0));
        return new Object[] {};
    }

    @Callback
    @Optional.Method(modid = "OpenComputers")
    public Object[] isCurrentlyConnected(Context context, Arguments args) {
        return new Object[] {this.playerEntity != null};
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public String getInventoryName() {
        return "Creative Player Interface";
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public String getType() {
        return "playerinterface";
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public String[] getMethodNames() {
        return new String[] {"getPlayerName", "setPlayerName", "isCurrentlyConnected"};
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments)
            throws Exception {
        switch (method) {
            case 0:
                return new Object[] {this.getPlayerName()};
            case 1:
                if (arguments.length < 1) {
                    return null;
                }
                this.setPlayerName(arguments[0] + "");
                break;
            case 2:
                return new Object[] {this.playerEntity != null};
        }
        return null;
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
