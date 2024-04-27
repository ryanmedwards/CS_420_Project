package opensky.statevector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import mvc.filters.Filter;
import mvc.source.Source;

public class StateVectorList extends LinkedList<StateVector>
{

	public final Source source;



	public StateVectorList(final Source source)
	{
		this.source = source;
	}

	public StateVectorList()
	{
		this.source = new Source(Source.Type.IMPORT_FILE, "DOES_NOT_EXIST");
	}
	
}
