package application;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class Log 
{
	private static JTextArea log = new JTextArea();
	
	public static void setLog(final JTextArea log)
	{
		Log.log = log;
	}
	
	public static void append(final String text)
	{
		log.append(text);
		log.update(log.getGraphics());
	}
	
	public static void clear()
	{
		log.setText("");
		log.update(log.getGraphics());
	}
	
	public static void delete(final int offset, final int length)
	{
		try 
		{
			log.getDocument().remove(offset, length);
		} 
		catch (BadLocationException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
		}
	}
	
	public static int length()
	{
		return log.getDocument().getLength();
	}
}
