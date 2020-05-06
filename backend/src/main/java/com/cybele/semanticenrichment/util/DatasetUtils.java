package com.cybele.semanticenrichment.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

public class DatasetUtils {
	
	public static URI randomURI(String concept) {
		URI uri=null;
    	Random rand = new Random();
		try {
			uri = new URI("https://www.cybele-project.eu/"+concept+"/id/"+Math.abs(rand.nextLong()));			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uri;	
	}
}
