package components.custom.checkbox;

import javax.swing.JCheckBox;


public class CheckBoxNode extends JCheckBox
{
	 public final CheckBoxNode[] children;
	 
	 public boolean isAvailable = true;
	 
	 
	 public final int id;
	 
	 public CheckBoxNode(final String text, final int id) 
	 {
		 this(text, id, new CheckBoxNode[] {});
	 }
	 
	 public CheckBoxNode(final String text, final int id, final CheckBoxNode[] children) 
	 {
		 super(text);
		 this.id = id;
		 this.children = children;
		 
		 this.addActionListener(e -> 
		 {
			 if(!this.isSelected())
				{
					this.close();
					this.revalidate();
				}
				else
				{
					this.open();
					this.revalidate();
				}
		 });
	 }
	 
	 public void close()
	 {
		 if(children.length == 0)
		 {
			 return;
		 }
		 else
		 {
			 for(final CheckBoxNode child: children)
			 {
				 child.setSelected(false);
				 child.setVisible(false);
				 child.close();
			 }
		 }
	 }
	 
	 public void open()
	 {
		 for(final CheckBoxNode child: children)
			{
				if(child.isAvailable)
				{
					child.setVisible(true);
				}
			}
	 }
}
