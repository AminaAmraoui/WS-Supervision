package modélisation;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

public class MyDefaultTreeCellRenderer implements TreeCellRenderer {
	public Component getTreeCellRendererComponent(JTree tree, Object value,
	boolean selected, boolean expanded, boolean leaf, int row, boolean
	hasFocus) {
	JLabel myLabel = new JLabel();
	JLabel labelExpanded = new JLabel();
	if (value instanceof DefaultMutableTreeNode) {
	DefaultMutableTreeNode myNode = (DefaultMutableTreeNode)value;
	String infoText = "- ";
	
	if (expanded)
	labelExpanded.setIcon(new ImageIcon("icone2.jpg"));
	else
	labelExpanded.setIcon(new ImageIcon("icone1.jpg"));
	
	infoText += (String)myNode.getUserObject();
	myLabel.setText(infoText);
	}
	JPanel myPanel = new JPanel(new BorderLayout());
	myPanel.add(BorderLayout.WEST,labelExpanded);
	myPanel.add(BorderLayout.CENTER,myLabel);
	return myPanel;
	}
}
