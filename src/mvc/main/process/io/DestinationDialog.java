package mvc.main.process.io;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import application.Application;
import components.FileChooser;
import components.buttons.Button;
import components.custom.Dialog;
import components.custom.RootPanel;
import components.text.FormatField;
import components.text.TextField;
import guiutil.Grid;

public class DestinationDialog extends Dialog
{

	public final JPanel rootPanel = new RootPanel().get();
	
	
	
	protected final JLabel descriptionLabel = new JLabel
	(
			  "<html>"
			+ "Enter the name for the table that will hold the processed data."
			+ "<br>"
			+ ""
			+ "<html>"
	);
	
	
	
	protected final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.LEFT);
	
	
	protected final JPanel localPanel = new JPanel(new GridBagLayout());
	
	protected final JPanel exportPanel = new JPanel(new GridBagLayout());
	
	
	protected final JLabel folderLabel = new JLabel("Folder:");
	protected final JLabel nameLabel = new JLabel("File Name:");
	
	protected final JTextField folderField = new TextField().initColumns(25).get();
	protected final JTextField nameField = new TextField().initColumns(25).get();
	
	
	protected final JFileChooser fileChooser = new FileChooser()
			.initFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
			.get();
	protected final JButton browseButton = new JButton("Browse");
	
	protected final JLabel destinationLabel = new JLabel("Destination:");
	protected final JTextField destinationField = new TextField()
												  .initText("Local Database")
											   	  .initEditable(false)
											   	  .initColumns(15)
											   	  .get();
	
	protected final JLabel tableLabel = new JLabel("Table:");
	protected final JFormattedTextField tableField = new FormatField()
											   	         .initColumns(15)
											   	         .get();
										
	
	
	
	
	public String getTable()
	{
		return tableField.getText();
	}
	
	
	
	public DestinationDialog(final JFrame parent, final String title, final boolean setModal) 
	{
		super(parent, title, setModal);
		
		this.setPreferredSize(new Dimension(500, 200));
		
		this.setMode(DialogMode.CANCEL_CONFIRM);
		
		this.draw();
		
		this.assign();
	}
	
	private void draw()
	{
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(10, 10, 10, 10);
		
		int x = 0;
		int y = 0;
		
		localPanel.add(destinationLabel, grid.set(  x, y));
		localPanel.add(destinationField, grid.set(++x, y));
		
		localPanel.add(tableLabel, grid.set(--x, ++y));
		localPanel.add(tableField, grid.set(++x, y));
		
		x = 0;
		y = 0;
		
		exportPanel.add(folderLabel, grid.set(x, y));
		exportPanel.add(folderField, grid.set(++x, y));
		exportPanel.add(browseButton, grid.set(++x, y));
		
		
		
		
		tabPane.addTab("Local Database", localPanel);
		tabPane.addTab("Export", exportPanel);
		
		
	    rootPanel.add(tabPane, BorderLayout.NORTH);
	    
	    this.addContent(rootPanel);
	}	
	
	private void assign()
	{
		browseButton.addActionListener(e ->
		{
			try
			{
				final int result = fileChooser.showDialog(Application.getApp(), "Select");
				
				if(result == JFileChooser.APPROVE_OPTION)
				{
					folderField.setText(fileChooser.getCurrentDirectory().toString());
				}
			}
			catch(Exception he)
			{
				
			}
		});
	}
}























