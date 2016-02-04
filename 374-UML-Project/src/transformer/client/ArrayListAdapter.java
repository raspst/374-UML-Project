package transformer.client;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class ArrayListAdapter implements Enumeration {
	private Iterator iter;
	
	public ArrayListAdapter(List list) {
		this.iter = list.iterator();
	}

	@Override
	public boolean hasMoreElements() {
		// TODO Auto-generated method stub
		return iter.hasNext();
	}

	@Override
	public Object nextElement() {
		// TODO Auto-generated method stub
		return iter.next();
	}
}
