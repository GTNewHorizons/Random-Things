package lumien.randomthings.Library;

public class DimensionCoordinates
{
	/** the dimension */
	public int dimension;

	/** the x coordinate */
	public int posX;
	/** the y coordinate */
	public int posY;
	/** the z coordinate */
	public int posZ;

	public DimensionCoordinates(int dimension, int posX, int posY, int posZ)
	{
		this.dimension = dimension;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	@Override
	public int hashCode()
	{
		return dimension + this.posX << 8 + this.posZ << 16 + this.posY << 32;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof DimensionCoordinates))
		{
			return false;
		}
		else
		{
			DimensionCoordinates dimensionCoordinates = (DimensionCoordinates) object;
			return this.dimension == dimensionCoordinates.dimension && this.posX == dimensionCoordinates.posX && this.posY == dimensionCoordinates.posY && this.posZ == dimensionCoordinates.posZ;
		}
	}

}
