package lumien.randomthings.Handler;

import java.util.ArrayList;
import java.util.Iterator;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Network.Messages.MessageNotification;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.RandomThings;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;

public class LetterHandler {
    ArrayList<ItemStack> waitingLetters;

    int tickCounter;
    final int tickRate;

    public LetterHandler() {
        waitingLetters = new ArrayList<>();

        tickCounter = 0;
        tickRate = 60;
    }

    public void addLetter(ItemStack letter) {
        waitingLetters.add(letter);
        writeToNBT();
        RandomThings.instance.saveNBT();
    }

    public void update() {
        tickCounter++;
        if (tickCounter >= tickRate) {
            tickCounter = 0;
            Iterator<ItemStack> iterator = waitingLetters.iterator();
            while (iterator.hasNext()) {
                ItemStack toCheck = iterator.next();

                String receiver = toCheck.stackTagCompound.getString("receiver");
                EntityPlayerMP receiverEntity =
                        MinecraftServer.getServer().getConfigurationManager().func_152612_a(receiver);
                if (receiverEntity != null) {
                    int freeSlot = receiverEntity.inventory.getFirstEmptyStack();
                    if (freeSlot != -1) {
                        receiverEntity.inventory.setInventorySlotContents(freeSlot, toCheck);
                        receiverEntity.inventory.markDirty();
                        receiverEntity.inventoryContainer.detectAndSendChanges();

                        iterator.remove();
                        writeToNBT();
                        RandomThings.instance.saveNBT();

                        PacketHandler.INSTANCE.sendTo(
                                new MessageNotification(
                                        "Received Ender Letter",
                                        "Sender: " + toCheck.stackTagCompound.getString("sender"),
                                        100,
                                        new ItemStack(ModItems.enderLetter, 1, 1)),
                                receiverEntity);
                    }
                }
            }
        }
    }

    public void writeToNBT() {
        NBTTagList letters = new NBTTagList();

        for (ItemStack is : waitingLetters) {
            NBTTagCompound itemNBT = new NBTTagCompound();
            is.writeToNBT(itemNBT);
            letters.appendTag(itemNBT);
        }
        RandomThings.instance.modNBT.setTag("letters", letters);
    }

    public void readFromNBT() {
        NBTTagCompound nbt = RandomThings.instance.modNBT;
        NBTTagList letters = nbt.getTagList("letters", 10);

        for (int index = 0; index < letters.tagCount(); index++) {
            NBTTagCompound tag = letters.getCompoundTagAt(index);
            ItemStack letter = ItemStack.loadItemStackFromNBT(tag);
            waitingLetters.add(letter);
        }
    }
}
