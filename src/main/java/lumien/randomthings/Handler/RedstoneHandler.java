package lumien.randomthings.Handler;

import java.util.HashSet;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

public class RedstoneHandler
{
	static HashSet<Vector4d> poweredBlocks = new HashSet<Vector4d>();
	
	
	public static void addPoweredBlock(int dimensionID,int posX,int posY,int posZ)
	{
		Vector4d vector = new Vector4d(dimensionID,posX,posY,posZ);
		poweredBlocks.add(vector);
	}
	
	public static boolean isPowered(int dimensionID,int posX,int posY,int posZ)
	{
		return poweredBlocks.contains(new Vector4d(dimensionID,posX,posY,posZ));
	}
	
	public static void removePoweredBlock(int dimensionID,int posX,int posY,int posZ)
	{
		poweredBlocks.remove(new Vector4d(dimensionID,posX,posY,posZ));
	}

	public static void reset()
	{
		poweredBlocks = new HashSet<Vector4d>();
		
	}
}
