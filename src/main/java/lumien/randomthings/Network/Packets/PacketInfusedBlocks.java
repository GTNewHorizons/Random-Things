package lumien.randomthings.Network.Packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.vecmath.Vector3d;

import net.minecraft.entity.player.EntityPlayer;

import lumien.randomthings.Handler.RedstoneHandler;
import lumien.randomthings.Network.AbstractPacket;

public class PacketInfusedBlocks extends AbstractPacket
{
	int dimensionID;
	int numberOfBlocks;
	boolean reset;
	ArrayList<Vector3d> blocks;

	public PacketInfusedBlocks()
	{
		blocks = new ArrayList<Vector3d>();
	}

	public PacketInfusedBlocks(int dimensionID, boolean reset, ArrayList<Vector3d> blocks)
	{
		this.blocks = blocks;
		this.dimensionID = dimensionID;
		this.reset = reset;
		this.numberOfBlocks = blocks.size();
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(dimensionID);
		buffer.writeInt(numberOfBlocks);
		buffer.writeBoolean(reset);

		for (int i = 0; i < blocks.size(); i++)
		{
			buffer.writeInt((int) blocks.get(i).x);
			buffer.writeInt((int) blocks.get(i).y);
			buffer.writeInt((int) blocks.get(i).z);
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		dimensionID = buffer.readInt();
		numberOfBlocks = buffer.readInt();
		reset = buffer.readBoolean();

		for (int i = 0; i < numberOfBlocks; i++)
		{
			blocks.add(new Vector3d(buffer.readInt(), buffer.readInt(), buffer.readInt()));
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		if (reset)
		{
			RedstoneHandler.reset();
		}
		for (Vector3d v : blocks)
		{
			RedstoneHandler.addPoweredBlock(dimensionID, (int) v.x, (int) v.y, (int) v.z);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{

	}
}
