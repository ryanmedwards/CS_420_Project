package opensky.statevector;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import data.Flight;
import mvc.source.Source;

public class StateVectorTest 
{
	
	final static String dir = System.getProperty("user.dir");
	
	
	final static String live_dir = "C:\\Users\\Ryan\\Desktop\\ECE_439\\Live_Data";
	
	final static String pm_dir = live_dir + "\\PM";
	final static String gi_dir = live_dir + "\\GI";
	final static String vd_dir = live_dir + "\\VD";
	
	
	final static String cd_dir = live_dir + "\\Combined";
	
	public static void main(String[] args)
	{
		
//		
//		final StateVectorList ap5au = StateVectorIO.loadSource(Source.IMPORT_FILE + IOModel.SPLITTER + live_dir + "\\April_5.csv");
//		final StateVectorList ap5pm = StateVectorIO.loadSource(Source.IMPORT_FILE + IOModel.SPLITTER + pm_dir + "\\April_5_PM.csv");
//		final StateVectorList ap5gi = StateVectorIO.loadSource(Source.IMPORT_FILE + IOModel.SPLITTER + gi_dir + "\\April_5_GI.csv");
//		final StateVectorList ap5vd = StateVectorIO.loadSource(Source.IMPORT_FILE + IOModel.SPLITTER + vd_dir + "\\April_5_VD.csv");
//		
//		
//		ap5au.addAll(ap5pm);
//		ap5au.addAll(ap5gi);
//		ap5au.addAll(ap5vd);
//		
//		
//		StateVectorIO.writeCSV(ap5au, cd_dir + "\\April_5_Combined.csv");
//		

		
		
		
		
		
		
		
//		// Array of columns
//		final int[] columns = new int[] 
//		{
//			StateVector.LAT,
//			StateVector.LON,
//			StateVector.VELOCITY,
//			StateVector.HEADING,
//			StateVector.VERTRATE,
//			StateVector.BAROALTITUDE,
//			StateVector.CLASSIFICATION
//		};
//		
//		final StateVectorList result = new StateVectorList("");
//
//		for(int i = 5; i < 15; ++i)
//		{
//			System.out.println("Randomizing April " + i);
//			final StateVectorList au = StateVectorIO.loadSource(Source.IMPORT_FILE + IOModel.SPLITTER + live_dir + "\\April_" + i + ".csv");
//			final StateVectorList pm = StateVectorIO.loadSource(Source.IMPORT_FILE + IOModel.SPLITTER + pm_dir + "\\April_" + i + "_PM.csv");
//			final StateVectorList gi = StateVectorIO.loadSource(Source.IMPORT_FILE + IOModel.SPLITTER + gi_dir + "\\April_" + i + "_GI.csv");
//			final StateVectorList vd = StateVectorIO.loadSource(Source.IMPORT_FILE + IOModel.SPLITTER + vd_dir + "\\April_" + i + "_VD.csv");
//			
//			final StateVectorList randAU = StateVectorOP.getRandomList(au, 2100);
//			final StateVectorList randPM = StateVectorOP.getRandomList(pm, 700);
//			final StateVectorList randGI = StateVectorOP.getRandomList(gi, 700);
//			final StateVectorList randVD = StateVectorOP.getRandomList(vd, 700);
//			
//			result.addAll(randAU);
//			result.addAll(randPM);
//			result.addAll(randGI);
//			result.addAll(randVD);
//		}
//		
//		System.out.println(result.size());
//		
//		StateVectorIO.writeCSV(result, "Training_Set.csv", columns);
//
//		
//		
//		// Filter Nulls
//		StateVectorOP.filterNulls(data, columns);
//		
//		// Radius
//		StateVectorOP.filterBoundaryRadius(data, 41.9802, -87.9090, 50*1.609);
//		
//		// Remove Entries with positions older than 3 seconds (Anomolous Samples)
//		StateVectorOP.filterPosExpired(data, 3);
//		
//		// Filter Duplicates
//		StateVectorOP.filterDuplicates(data, columns);	
//		
//		StateVectorIO.writeCSV(data, live_dir + "\\filtered.csv");
//		
//	
//		for(int i = 5; i < 15; ++i)
//		{
//			final StateVectorList temp = new StateVectorList(data);
//			
//			final long offset = i - 5;
//			
//			final long start = 1712293200 + (60*60*24) * offset;
//			final long stop = 1712379600 + (60*60*24) * offset;
//			
//			StateVectorOP.filterDateTime(temp, start, stop);
//
//			System.out.println(temp.size());
//			
//			StateVectorIO.writeCSV(temp, live_dir + "\\April_" + i + ".csv");
//		}
	}
}



//5	-> 	1712293200	-> 1075447
//6	->	1712379600	-> 873985
//7	->	1712466000	-> 845743
//8	->	1712552400	-> 570304
//9	->	1712638800	-> 1044832
//10	->	1712725200	-> 1030305
//11	->	1712811600	-> 1052879
//12	->	1712898000	-> 1201756
//13	->	1712984400	-> 1011781
//14	->	1713070800	-> 1009953
//15	->	1713157200





















