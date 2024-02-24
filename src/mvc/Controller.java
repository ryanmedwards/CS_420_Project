package mvc;

public abstract class Controller<M extends Model<M, ?, ?, ?>> 
{
	public abstract void assign(final M model);
}
