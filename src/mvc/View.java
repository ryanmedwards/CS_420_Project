package mvc;

public abstract class View<M extends Model<M, ?, ?, ?>>
{
	public abstract void draw(final M model);
}
