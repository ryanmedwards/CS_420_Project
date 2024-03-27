package mvc.main.user;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.custom.RootPanel;
import components.text.TextField;

public class UserModel 
{
	public final JPanel rootPanel = new RootPanel().get(); 
	
	
	protected final JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	
	protected final JPanel logoffPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	
	
	protected final JTextField usernameField = new TextField().initColumns(25).get();
	
	
	protected final JButton loginButton = new JButton("Log In");
	
	protected final JButton logoffButton = new JButton("Log Off");
	
	
	
	protected final JLabel userLabel = new JLabel();
	
	
	
	
	
	
	
	
	
	public final UserView view = new UserView(this);
	public final UserController control = new UserController(this);
}
