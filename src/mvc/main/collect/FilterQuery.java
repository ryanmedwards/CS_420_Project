package mvc.main.collect;

public class FilterQuery 
{
	public static enum QueryOption
	{
		EXPLAIN,
		COUNT,
		EXECUTE
	}
	
	
	protected final StringBuilder query = new StringBuilder();
	
	protected final StringBuilder select = new StringBuilder();
	
	protected final StringBuilder where = new StringBuilder("\nWHERE\n");
	
	protected final StringBuilder hour = new StringBuilder();
	
	protected final StringBuilder time = new StringBuilder();
	
	protected final StringBuilder boundary = new StringBuilder();
	
	
	
	public void updateSelect(final QueryOption option)
	{
		select.delete(0, select.length());
		
		switch(option)
		{
		case EXPLAIN: select.append("EXPLAIN SELECT * FROM state_vectors_data4"); break;
			
		case COUNT:   select.append("SELECT COUNT(*) FROM state_vectors_data4");  break;
			
		case EXECUTE: select.append("SELECT * FROM state_vectors_data4");         break;
		}
		
		updateQuery();
	}
	
	
	
	public void updateDateTime(final long start, final long stop)
	{
		hour.delete(0, hour.length());
		
		
		final long startHour = start - (start % 3600);
		final long stopHour  = stop  - (stop  % 3600);
		
		
		if(     (startHour == start && stopHour == stop)
			&&	(startHour == stopHour || startHour + 3600 == stopHour)
				)
		{
			hour.append(String.format("hour = %d", startHour));
		}
		else if(stop < start)
		{
			hour.append(String.format("hour = %d", startHour));
			hour.append(String.format("\nAND time >= %d", start));
		}
		
		
		
		
		if(stop <= start)
		{
			hour.append(String.format("hour = %d", startHour));
			
			if(start != startHour)
			{
				hour.append(hour.append(String.format("\nAND time >= %d", start)));
			}
		}
		else
		{
			if(startHour + 3600 == stopHour)
			{
				hour.append(String.format("hour >= %d", startHour, stopHour));
			}
			
			hour.append(String.format("hour >= %d AND hour <= %d", startHour, stopHour));
			
			if(start != startHour)
			{
				hour.append(String.format("\nAND time >= %d", start));
			}
			
			if(stop != stopHour)
			{
				hour.append(String.format(" AND time <= %d", stop));
			}
		}

		updateQuery();
	}
	
	
	
	public void updateBoundary(final double[] bounds)
	{
		boundary.delete(0, boundary.length());
		
		boundary.append(String.format("\nAND lat <= %f", bounds[0])); // North
		boundary.append(String.format("\nAND lat >= %f", bounds[1])); // South
		boundary.append(String.format("\nAND lon <= %f", bounds[2])); // East
		boundary.append(String.format("\nAND lon >= %f", bounds[3])); // West
		
		updateQuery();
	}
	
	
	
	protected void updateQuery()
	{
		query.delete(0, query.length());
		
		query.append(select.toString());
		query.append(where.toString());
		query.append(hour.toString());
		query.append(time.toString());
		query.append(boundary.toString());
		
	}
	
	public String getQuery()
	{
		return query.toString();
	}

	
}
