package mvc.main.user;

import java.awt.BorderLayout;

public class UserView 
{
	private final UserModel model;
	
	protected UserView(final UserModel model)
	{
		this.model = model;
		
		this.draw();
	}
	
	private void draw()
	{
		this.drawLoginPanel();
	}
	
	protected void drawLoginPanel()
	{
		model.rootPanel.removeAll();
		
		model.loginPanel.add(model.usernameField);
		model.loginPanel.add(model.loginButton);
		
		model.rootPanel.add(model.loginPanel, BorderLayout.CENTER);
		
		this.refresh();
	}
	
	protected void drawLogoffPanel()
	{
		model.rootPanel.removeAll();
		
		model.logoffPanel.add(model.userLabel);
		model.logoffPanel.add(model.logoffButton);
		
		model.rootPanel.add(model.logoffPanel, BorderLayout.CENTER);
		
		this.refresh();
	}
	
	private void refresh()
	{
		model.rootPanel.revalidate();
		model.rootPanel.repaint();
	}
}
