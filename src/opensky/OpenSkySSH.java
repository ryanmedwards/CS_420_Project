package opensky;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.swing.JOptionPane;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class OpenSkySSH 
{
	
	// ********************************************************
	// ********************************************************
	// ***									             	***
	// ***	Static Fields       				 			***
	// ***											        ***	
	// ********************************************************
	// ********************************************************
	
	/**
	 * 
	 */
	private static final String HOST = "data.opensky-network.org";
	
	/**
	 * 
	 */
	private static final int PORT = 2230;
	
	/**
	 * 
	 */
	private static final int TIMEOUT = 30000;
	
	/**
	 * 
	 */
	private static final String TYPE  = "shell";
	
	public static void explain(final String query)
	{
		final byte[] input = ("explain " + query + "\n\rquit;\n\r").getBytes(StandardCharsets.UTF_8);
		
		try
		{
			final String user = "CESecurity";

			final String password = "sd429ProjAIMR";
			
			final Session session = new JSch().getSession(user, HOST, PORT);
			
			
			session.setPassword(password);
			
			session.setConfig("StrictHostKeyChecking", "no");
			
			session.connect(TIMEOUT);
			
			
			final Channel channel = session.openChannel(TYPE);
			
			
			
			final ByteArrayInputStream stream = new ByteArrayInputStream(input);

			
			channel.setInputStream(stream);
			
			
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			channel.setOutputStream(outputStream);
			
			channel.connect(3 * 1000);
			
			while(channel.isConnected())
			{
				
			}
			
			
			final byte[] output = outputStream.toByteArray();

			System.out.println(output);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void executeQuery(final String query, final String outputfile)
	{
		final byte[] input = (query + "\n\rquit;\n\r").getBytes(StandardCharsets.UTF_8);
		
		try
		{
			final String user = "CESecurity";

			final String password = "sd429ProjAIMR";
			
			final Session session = new JSch().getSession(user, HOST, PORT);
			
			
			session.setPassword(password);
			
			session.setConfig("StrictHostKeyChecking", "no");
			
			session.connect(TIMEOUT);
			
			
			final Channel channel = session.openChannel(TYPE);
			
			
			
			final ByteArrayInputStream stream = new ByteArrayInputStream(input);

			
			channel.setInputStream(stream);
			

			channel.setOutputStream(new FileOutputStream(new File("test.txt")));
			
			channel.connect(3 * 1000);
			
			while(channel.isConnected())
			{
				
			}
			
			final File file = new File("test.txt");
			
			final BufferedReader reader = new BufferedReader(new FileReader(file));
			
			
			for(String line; (line = reader.readLine()) != null;)
			{
				System.out.println(line);
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
