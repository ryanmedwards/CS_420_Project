package mvc.destination;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import application.Application;
import components.ComboBox;
import components.FileChooser;
import components.Panel;
import components.custom.RootPanel;
import components.custom.dialog.Dialog;
import components.text.FormatField;
import components.text.TextField;
import guiutil.Grid;
import guiutil.OptionPane;

public class DestinationModel 
{
	// TODO: Create JComponents only if needed
	
	
	protected final JPanel rootPanel = new JPanel(new BorderLayout());
	

	protected final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.LEFT);
	
	
	protected final Dialog dialog = new Dialog(Application.getApp(), "Destination Selector", true)
										.initMode(Dialog.Mode.CANCEL_CONFIRM);
	

	// ********************************************************
	// ********************************************************
	// ***									             	***
	// *** Export A CSV	           				 			***
	// ***											        ***	
	// ********************************************************
	// ********************************************************

	protected String savedExportFolder;
	protected String savedExportFile;
	protected int    savedExportFormat;
	
	public final JPanel exportPanel = new RootPanel().get();
	
	protected final JLabel exportDescriptionLabel = new JLabel
	(
		  "<html>"
	    + "&emsp;Save the data to a file."
	    + "<p>"
	    + "&emsp;The data will be stored in a file with the given name."
	    + "<p><p>"
		+ "<html>"
	);
	
	protected final JPanel exportDescriptionPanel = new Panel()
			.initLayout(new BorderLayout())
			.initBorder(BorderFactory.createTitledBorder(
							BorderFactory.createMatteBorder(1, 0, 0, 0, exportPanel.getForeground()), "Description")).get();
	
	
	protected final JPanel exportControlsPanel = new Panel()
			.initLayout(new BorderLayout())
			.initBorder(BorderFactory.createTitledBorder(
							BorderFactory.createMatteBorder(1, 0, 0, 0, exportPanel.getForeground()), "Controls")).get();
	
	protected final JPanel exportPathPanel   = new JPanel(new GridBagLayout());
	protected final JLabel exportFolderLabel = new JLabel("Folder:");
	protected final JLabel exportFileLabel   = new JLabel("File:");
	protected final JLabel exportFormatLabel = new JLabel("Format:");
	
	protected final JButton exportSelectFolderButton = new JButton("Select");
	
	protected final JTextField exportFolderField = new TextField().initColumns(25).initEditable(false).get();
	protected final JTextField exportFileField   = new TextField().initColumns(25).get();

	protected final JComboBox<String> exportFormatBox = new JComboBox<String>();

	protected final JFileChooser fileChooser = new FileChooser().initFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
																.initAcceptAllFileFilterUsed(false)
																.get();


	

	
	// ********************************************************
	// ********************************************************
	// ***									             	***
	// ***	Create a Local Table   				 			***
	// ***											        ***	
	// ********************************************************
	// ********************************************************
	
	protected String savedLocalTable;
	
	
	protected final JPanel localPanel = new RootPanel().get();
	
	protected final JPanel localDescriptionPanel = new Panel()
			.initLayout(new BorderLayout())
			.initBorder(
					BorderFactory.createTitledBorder(
							BorderFactory.createMatteBorder(1, 0, 0, 0, localPanel.getForeground()), "Description")).get();
																   
	protected final JPanel localControlsPanel = new Panel()
			.initLayout(new BorderLayout())
			.initBorder(
					BorderFactory.createTitledBorder(
							BorderFactory.createMatteBorder(1, 0, 0, 0, localPanel.getForeground()), "Controls")).get();
	
	
	protected final JLabel localDescriptionLabel = new JLabel
	(
		  "<html>"
	    + "&emsp;Save the data in the local database."
	    + "<p>"
	    + "&emsp;The data will be stored in a table with the name provided."
	    + "<p><p>"
		+ "<html>"
	);

	
	protected final JPanel localTablePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
	protected final JLabel localTableLabel = new JLabel("Table Name:");
	protected final JFormattedTextField localTableField = new FormatField().initColumns(15).get();

	
	private final Map<Integer, Destination.Type> tabMap = new HashMap<>();
	
	protected DestinationModel(final List<Destination.Type> types, final List<String> fileFormats)
	{
		this.draw(types, fileFormats);
		this.assign();
	}
	
	
	private void draw(final List<Destination.Type> types, final List<String> fileFormats)
	{
		final Set<Destination.Type> set = new HashSet<>();
		int next = 0;
		
		for(final Destination.Type type: types)
		{
			if(set.add(type))
			{
				switch(type)
				{
				case FILE: this.tabPane.addTab(type.toString(), this.exportPanel); break;
		
				case LOCAL_DATABASE: this.tabPane.addTab(type.toString(), this.localPanel); break;	
				}
				
				this.tabMap.put(next++, type);
			}
		}
		
		for(final String f: fileFormats)
		{
			this.exportFormatBox.addItem(f);
		}
		
		this.drawExportView();
		this.drawLocalView();
		
		this.dialog.setPreferredSize(new Dimension(700, 500));
		
		
		this.rootPanel.add(this.tabPane, BorderLayout.CENTER);	
		
		this.dialog.addContent(this.rootPanel);
	}

	private void drawExportView()
	{
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(10, 10, 10, 10);
		
		int x = 0;  int y = 0;
		this.exportPathPanel.add(this.exportFolderLabel       , grid.set(  x, y));
		this.exportPathPanel.add(this.exportFolderField       , grid.set(++x, y));
		this.exportPathPanel.add(this.exportSelectFolderButton, grid.set(++x, y));
		
		x = 0;  y = 1;
		this.exportPathPanel.add(this.exportFileLabel, grid.set(  x, y));
		this.exportPathPanel.add(this.exportFileField, grid.set(++x, y));
		
		x = 0;  y = 2;
		this.exportPathPanel.add(this.exportFormatLabel, grid.set(  x, y));
		this.exportPathPanel.add(this.exportFormatBox  , grid.set(++x, y));
		
		this.exportControlsPanel.add(this.exportPathPanel, BorderLayout.NORTH);
		
		this.exportDescriptionPanel.add(this.exportDescriptionLabel, BorderLayout.NORTH);
		
		
		this.exportPanel.add(this.exportDescriptionPanel, BorderLayout.NORTH);
		this.exportPanel.add(this.exportControlsPanel   , BorderLayout.CENTER);
	}
	

	private void drawLocalView()
	{
		this.localDescriptionPanel.add(this.localDescriptionLabel, BorderLayout.NORTH);
		
		
		this.localTablePanel.add(this.localTableLabel);
		this.localTablePanel.add(this.localTableField);
		
		this.localControlsPanel.add(this.localTablePanel);
		
		this.localPanel.add(this.localDescriptionPanel, BorderLayout.NORTH);
		this.localPanel.add(this.localControlsPanel, BorderLayout.CENTER);
	}

	

	private void assign()
	{
		this.exportSelectFolderButton.addActionListener(e ->
		{
			final int result = this.fileChooser.showOpenDialog(this.dialog);
			
			if(result == JFileChooser.APPROVE_OPTION)
			{
				this.exportFolderField.setText(this.fileChooser.getSelectedFile().toString());
			}
		});
		
		this.dialog.setConfirmAction(getDialogConfirmActionListener());
	}	
	
	/**
	 * Checks if a valid destination has been chosen.
	 * 
	 * @return true if a valid destination has been chosen, false otherwise
	 */
	public boolean hasValidDestination()
	{
		try
		{
			final Destination destination = this.getDestination();
			
			if(destination == null)
			{
				return false;
			}
			
			if(destination.type == null || destination.location == null)
			{
				return false;
			}
			
			if(destination.location.isBlank() || destination.location.equals("\\.csv")) // Should change in future
			{
				return false;
			}
			
			return true;
		}
		catch(NullPointerException e)
		{
			// Thrown by textField.getText(), but only if its document is null not if text is blank
			e.printStackTrace();
			return false;
		}
		catch(IllegalFormatException e)
		{
			// Nothing wrong with Destination, just messed up String.format()
			e.printStackTrace();
			return false;
		}
	}
	

	/**
	 * Get the chosen destination.
	 * 
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalFormatException
	 */
	public Destination getDestination() throws NullPointerException, IllegalFormatException
	{
		final int index = this.tabPane.getSelectedIndex();
		
		switch(this.tabMap.get(index))
		{
		case FILE: 
			return new Destination(
					Destination.Type.FILE,
					 this.exportFolderField.getText()+"\\"
					+this.exportFileField.getText()
					+this.exportFormatBox.getSelectedItem());
		
		case LOCAL_DATABASE: 
			return new Destination(
					Destination.Type.LOCAL_DATABASE,
					this.localTableField.getText());
		
		default: return null;
		}
				
	}

	/**
	 * Save the current selected Destination.
	 */
	public void save()
	{
		if(this.hasValidDestination())
		{
			this.savedLocalTable = this.localTableField.getText();
			
			this.savedExportFolder = this.exportFolderField.getText();
			this.savedExportFile = this.exportFileField.getText();
			this.savedExportFormat = this.exportFormatBox.getSelectedIndex();
		}
	}
	
	/**
	 * Set the selected Destination to the saved Destination.
	 */
	public void revert()
	{
		this.localTableField.setText(this.savedLocalTable);
		
		this.exportFolderField.setText(this.savedExportFolder);
		this.exportFileField.setText(this.savedExportFile);
		this.exportFormatBox.setSelectedIndex(this.savedExportFormat);
	}
	
	/**
	 * Describes how the dialog should act when the confirm button is selected.
	 * 
	 * @return ActionListener
	 */
	private ActionListener getDialogConfirmActionListener()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(hasValidDestination())
				{
					dialog.setResult(true);
					dialog.dispose();
				}
				else
				{
					OptionPane.showError(dialog, "Invalid Destionation Selected.");
				}
			}
		};
	}
	
	
	/**
	 * Launch the Dialog to select a destination.
	 * 
	 * @return true if confirm button selected, false otherwise
	 */
	public boolean launch()
	{
		return this.dialog.launch();
	}

	
	

	
}


