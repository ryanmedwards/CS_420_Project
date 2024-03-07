package opensky.statevector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StateVectorIO 
{
	
	private final static String defaultHeader = "time,icao24,lat,lon,velocity,heading,vertrate,callsign,onground,alert,spi,squawk,baroaltitude,geoaltitude,lastposupdate,lastcontact,hour";
	private final static String classHeader = "time,icao24,lat,lon,velocity,heading,vertrate,callsign,onground,alert,spi,squawk,baroaltitude,geoaltitude,lastposupdate,lastcontact,hour,class";
	
	public static List<StateVector> loadCSV(final String filepath) 
	{
		List<StateVector> result = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(filepath)))
		{
			boolean hasClass = false;
			
			String line = br.readLine();
			
			if(line.equals(defaultHeader))
			{
				hasClass = false;
			}
			else if(line.equals(classHeader))
			{
				hasClass = true;
			}
			else
			{
				System.out.println("No Header");
				return result;
			}
			
			if(hasClass)
			{
				while((line = br.readLine()) != null)
				{
					Object[] values = line.split(",");	

					result.add(new StateVector(values));
				}
			}
			else
			{
				while((line = br.readLine()) != null)
				{
					Object[] temp = line.split(",");

					Object[] values = new Object[StateVector.SIZE];
					
					for(int i = 0; i < StateVector.SIZE - 1; ++i) 
					{
						values[i] = temp[i];
					}
					
					values[StateVector.SIZE - 1] = StateVector.AUTHENTIC;
					
					result.add(new StateVector(values));
				}
			}

			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void writeCSV(final List<StateVector> data, final String filename)
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename)))
		{
			bw.write(classHeader);
			bw.newLine();
			for(StateVector entry: data)
			{
				for(int j = 0; j < entry.values.length; ++j)
				{
					bw.write(entry.values[j].toString());
					if(j != entry.values.length-1)
					{
						bw.write(",");
					}
				}
				bw.newLine();
			}
		} 
		catch (IOException e) 
		{

			e.printStackTrace();
		}
	}
}
