package lumien.randomthings.Library;

public class DimensionCoordinate
{
	/** the dimension */
	public int dimension;

	/** the x coordinate */
	public int posX;
	/** the y coordinate */
	public int posY;
	/** the z coordinate */
	public int posZ;

	public DimensionCoordinate(int dimension, int posX, int posY, int posZ)
	{
		this.dimension = dimension;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public double getDistanceSqrd(double posX, double posY, double posZ)
	{
		double difX = posX - this.posX;
		double difY = posY - this.posY;
		double difZ = posZ - this.posZ;
		return difX * difX + difY * difY + difZ * difZ;
	}

	@Override
	public int hashCode()
	{
		return dimension + this.posX << 8 + this.posZ << 16 + this.posY << 32;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof DimensionCoordinate))
		{
			return false;
		}
		else
		{
			DimensionCoordinate dimensionCoordinates = (DimensionCoordinate) object;
			return this.dimension == dimensionCoordinates.dimension && this.posX == dimensionCoordinates.posX && this.posY == dimensionCoordinates.posY && this.posZ == dimensionCoordinates.posZ;
		}
	}

}
