package mvc.source;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.Panel;
import components.custom.RootPanel;
import components.text.TextField;
import guiutil.Grid;

public class ImportFileModel 
{
	public final JPanel rootPanel = new RootPanel().get();

	
	protected final JPanel descriptionPanel = new Panel()
			.initLayout(new BorderLayout())
			.initBorder(BorderFactory.createTitledBorder(
							BorderFactory.createMatteBorder(1, 0, 0, 0, rootPanel.getForeground()), "Description")).get();
	
	
	protected final JPanel controlsPanel = new Panel()
			.initLayout(new BorderLayout())
			.initBorder(BorderFactory.createTitledBorder(
							BorderFactory.createMatteBorder(1, 0, 0, 0, rootPanel.getForeground()), "Controls")).get();
	
	
	protected final JLabel descriptionLabel = new JLabel
	(
		  "<html>"
		+ "<p><p>"
		+ "Select a file as the source of data."
		+ "<p><p>"
		+ "The file should contain the following columns in the listed order:"
		+ "<p><p>"
		+ "&emsp;&emsp;time, icao24, lat, lon, velocity, heading, vertrate, callsign, onground, alert, spi, squawk, baroaltitude, geoaltitude, lastposupdate, lastcontact, hour"
		+ "<p><p>"
		+ "<html>"
	);
	
	protected final JPanel northPanel = new JPanel(new BorderLayout());

	protected final JPanel contentPanel = new JPanel(new GridBagLayout());
	
	protected final JLabel fileLabel = new JLabel("File:");
	
	protected final JTextField fileField = new TextField().initEditable(false).initColumns(50).get();
	
	protected final JButton selectFileButton = new JButton("Select");
	
	protected final JFileChooser fileChooser = new JFileChooser();	
	
	
	
	protected ImportFileModel()
	{
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
		
		
		this.descriptionPanel.add(this.descriptionLabel, BorderLayout.NORTH);
		
		
		this.contentPanel.add(this.fileLabel   , grid.set(  x, y));
		this.contentPanel.add(this.fileField   , grid.set(++x, y));
		this.contentPanel.add(this.selectFileButton, grid.set(++x, y));
		
		this.northPanel.add(this.contentPanel, BorderLayout.WEST);
		
		this.controlsPanel.add(this.northPanel, BorderLayout.NORTH);
		
		
		
		this.rootPanel.add(this.descriptionPanel, BorderLayout.NORTH);
		this.rootPanel.add(this.controlsPanel, BorderLayout.CENTER);
	}
	
	
	private void assign()
	{
		this.selectFileButton.addActionListener(e ->
		{
			final int result = this.fileChooser.showOpenDialog(null);

	        if (result == JFileChooser.APPROVE_OPTION) 
	        {
	            this.fileField.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
	        } 
		});	



	}
	
	public Source getSource()
	{
		return new Source(Source.Type.IMPORT_FILE, this.fileField.getText());
	}
	
	
	
	
}
