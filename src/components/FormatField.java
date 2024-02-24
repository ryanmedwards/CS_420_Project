package components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

public class FormatField extends JFormattedTextField
{

	
    public static final int DOUBLE           = 0;
	public static final int INTEGER          = 1;
	public static final int POSITIVE_INTEGER = 2;

	private final int format;
	
	public FormatField(final String name, final int format) 
	{
		this(name, format, 10);
	}
	
	public FormatField(final String name, final int format, final int col) 
	{
		super(getFormat(format));

		this.format = format;
		
		this.setColumns(col);
		this.setName(name);
		
		this.setInputVerifier(new Verifier());
		
		this.setFocusLostBehavior(FormatField.COMMIT);
	}
	
	public static Format getFormat(final int format)
	{
		switch(format)
		{

		case DOUBLE:           return NumberFormat.getNumberInstance();
			
		case INTEGER:          return NumberFormat.getIntegerInstance();
	
		case POSITIVE_INTEGER: return NumberFormat.getIntegerInstance();

		}
		
		return NumberFormat.getIntegerInstance();
	}

	private class Verifier extends InputVerifier
	{

		@Override
	    public boolean verify(JComponent input)
	    {
	        if (input instanceof FormatField) 
	        {
	        	final FormatField ftf = (FormatField)input;
	        	
	            final AbstractFormatter formatter = ftf.getFormatter();
	            
	            if (formatter != null) 
	            {
	                final String text = ftf.getText();
	                
	                if(text.length() == 0)
	                {
	                	return true;
	                }
	                
	                switch(format)
	                {
	                case DOUBLE: return checkDouble(text, formatter);
	                case INTEGER: return checkInteger(text, formatter);
	                case POSITIVE_INTEGER: return checkPosInt(text, formatter);
	                default: return true;
	                }
	               
	             }
	         }
	         return true;
	     }
	    
	     public boolean shouldYieldFocus(JComponent input) 
	     {
	         return verify(input);
	     }
		
	}
	
	private boolean checkDouble(final String text, final AbstractFormatter formatter)
	{
		try 
        {
			formatter.stringToValue(text);
            return true;
         } 
        catch (ParseException pe) 
        {
        	OptionFrame.showMessageDialog(new Frame(), "\'" + getName() + "\' field must be a number.", "Invalid Input!",
			        OptionFrame.ERROR_MESSAGE); 
             return false;
        }
	}
	
	private boolean checkInteger(final String text, final AbstractFormatter formatter)
	{
		try 
        {
            formatter.stringToValue(text);

             return true;
         } 
        catch (ParseException pe) 
        {
        	OptionFrame.showMessageDialog(new Frame(), "\'" + getName() + "\' field must be an integer.", "Invalid Input!",
			        OptionFrame.ERROR_MESSAGE); 
             return false;
        }
	}
	
	private boolean checkPosInt(final String text, final AbstractFormatter formatter)
	{
		 try 
         {
             final Object val = formatter.stringToValue(text);
             
             final Integer x = Integer.parseInt(val.toString());
              
             if(x < 0)
             {
            	 throw new ParseException("Negative Number", 0);
             }
              
              return true;
          } 
         catch (ParseException pe) 
         {
         	OptionFrame.showMessageDialog(new Frame(), "\'" + getName() + "\' field must be a positive integer.", "Invalid Input!",
 			        OptionFrame.ERROR_MESSAGE); 
         	setText("");
            return false;
         }
	}

}
