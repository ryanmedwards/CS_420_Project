package mvc.main.process;

import java.awt.BorderLayout;

import mvc.View;

public class ProcessView
{
	public ProcessView(final ProcessModel model)
	{
		model.rootPanel.add(model.filterModel.rootPanel, BorderLayout.WEST);
	}
}
