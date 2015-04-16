package MAS_LendingSystem;

import javax.swing.JButton;
import javax.swing.JPanel;

import repast.simphony.userpanel.ui.UserPanelCreator;

public class OptionsPanel implements UserPanelCreator {

	public JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.add(new JButton("a"));
		return panel;
	}

}
