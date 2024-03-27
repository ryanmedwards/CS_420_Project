package mvc.main.user;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import application.Application;
import components.custom.dialog.Dialog;
import guiutil.OptionPane;
import opensky.Trino;

public class UserController 
{
	private final UserModel model;
	
	protected UserController(final UserModel model)
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{
		model.loginButton.addActionListener(e -> login());
		
		model.logoffButton.addActionListener(e -> logoff());
	}

	private void login()
	{
		// Check if user name is provided
		if(model.usernameField.getText().isBlank())
		{
			OptionPane.showError("A username must be provided");
			return;
		}
		
		// Open Trino Login in Browser
		if (Desktop.isDesktopSupported() && 
			Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) 
		{	
			// Ask user if they wish to visit the website.
			if(OptionPane.showYesNoCancel(
					String.format("Please visit: %s to login.", Trino.LOGIN_URL)))
			{
				try 
			    {
					Desktop.getDesktop().browse(new URI(Trino.LOGIN_URL));
				} 
			    catch (IOException | URISyntaxException e) 
			    {
					e.printStackTrace();
					return;
				}
			}
			else
			{
				OptionPane.showMessage("");
			}
		}
		else
		{
			
		}
		
		
		// Check if Connection through Trino was successfully created
		if( ! Trino.createConnection(model.usernameField.getText()) )
		{
			OptionPane.showError(Trino.getErrorMessage());
			return;
		}
		
		model.userLabel.setText(model.usernameField.getText());
		model.view.drawLogoffPanel();
	}
	
	private void logoff()
	{
		
	}
}
