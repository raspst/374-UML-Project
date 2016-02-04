package transformer.client;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import transformer.lib.LinearTransformer;

public class App {
	public static void main(String[] args) throws Exception {
		// We want to use ArrayList instead of Vector here, i.e.,
		// ArrayList<String> vect = new ArrayList<String>();
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 1; i <= 3; ++i) {
			list.add("Tick Tick " + i);
		}
		ArrayListAdapter adap = new ArrayListAdapter(list);
		LinearTransformer<String> lT = new LinearTransformer<String>(adap);
		lT.transform(System.out);
	}
}
