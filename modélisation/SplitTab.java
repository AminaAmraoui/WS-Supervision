package modélisation;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.looks.LookUtils;



final class SplitTab {
	JTextField matiere;
	JRadioButton rb1;
	JRadioButton rb2;
	JCheckBox cb1;
	JCheckBox cb2;
	JTable table;
	static int j=0;
	String[][] list;
	private SalesModel model;
	DefaultMutableTreeNode root;
	DefaultTreeModel Droot;
	
	DefaultMutableTreeNode II1S1;
	DefaultMutableTreeNode II1S2;
	DefaultMutableTreeNode II2S1;
	DefaultMutableTreeNode II2S2;


	public SplitTab(DefaultTreeModel r) {
		Droot=r;
	}
	public SplitTab() {
		Droot=null;
	}
    /**
     * Builds and returns the panel.
     */
    JComponent build() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(Borders.DIALOG_BORDER);
        panel.add(BorderLayout.CENTER,buildHorizontalSplit());
	    return panel;
    }

    
	class PerformSaleAction implements ActionListener {

		 public void actionPerformed(ActionEvent e) {
		 	if(!matiere.getText().equals("")){
		 	
			DefaultMutableTreeNode n;
			DefaultMutableTreeNode p;
			if(rb1.isSelected())
			{
				if(cb1.isSelected()){
					
					n=II1S1;
				}
				else		
					n=II1S2;
			}
			else
			{
				if(cb1.isSelected())
					n=II2S1;
				else		
					n=II2S2;
		
			}
		int j;
		for(j=0;j<n.getChildCount();j++)
		{
				if(n.getChildAt(j).toString().equals(matiere.getText()))
				{
					//matiere.setText("OK");
						break;
				}
		}

		if((j<n.getChildCount()) &&  (n.getChildAt(j).toString().equals(matiere.getText()))){
				n=(DefaultMutableTreeNode) n.getChildAt(j);
		}
		else{
			p = new DefaultMutableTreeNode(matiere.getText());
			Droot.insertNodeInto(p,n,0);
			n=p;
			p=null;
		}
		
		for(int i=0;i<model._size();i++){
			if(model.getValueAt(i,0).equals(""))
				break;

			Droot.insertNodeInto(new DefaultMutableTreeNode(model.getValueAt(i,0).toString()),n,0);	
		}		
				
		 }
		 }

	 }
	class PerformclearAction implements ActionListener {

		 public void actionPerformed(ActionEvent e) {
		
				model.reset();
			Droot.reload();
					
				
		 }

	 }
	class PerformresetAction implements ActionListener {

		 public void actionPerformed(ActionEvent e) {
		
			II1S1.removeAllChildren();
			II1S2.removeAllChildren();
			II2S1.removeAllChildren();
			II2S2.removeAllChildren();

			Droot.reload();
			
					
				
		 }

	 }
	class PerformsaveAction implements ActionListener {

		 public void actionPerformed(ActionEvent e) {
		
			try {
				FileOutputStream ff = new FileOutputStream("ListEtudient");
				ObjectOutputStream fich = new ObjectOutputStream(ff);
				fich.writeObject(Droot);
				fich.flush();
				ff.close();
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//matiere.setText("OK1");
			}
			
		 }

	 }

    private JComponent buildHorizontalSplit() {
        JComponent left = new JScrollPane(buildTree());
        left.setPreferredSize(new Dimension(200, 100));

        JComponent upperRight = new JScrollPane(buildRadioButtonRow());
        upperRight.setPreferredSize(new Dimension(100, 100));

        JComponent lowerRight = new JScrollPane(buildTable());
        lowerRight.setPreferredSize(new Dimension(100, 100));

        JSplitPane verticalSplit = UIFSplitPane.createStrippedSplitPane(
                    JSplitPane.VERTICAL_SPLIT, 
                    upperRight, 
                    lowerRight);
        verticalSplit.setOpaque(false);
        JSplitPane horizontalSplit = UIFSplitPane.createStrippedSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            left,
            verticalSplit);
        horizontalSplit.setOpaque(false);
        return horizontalSplit;
    }
    

    /**
     * Builds and returns a sample tree.
     */
    private JTree buildTree() {
        JTree tree = new JTree(createSampleTreeModel());
        tree.expandRow(3);
        tree.expandRow(2);
        tree.expandRow(1);
        return tree;
    }

    private JComponent buildRadioButtonRow() {
		rb1 = new JRadioButton("	II.1	");
		rb2 = new JRadioButton("	II.2	");
        return buildButtonRow(
            new AbstractButton[] {
                rb1,
                rb2,
            },
            false,
            false);
    }
    private JComponent buildButtonRow(
            AbstractButton[] buttons,
            boolean borderPainted,
            boolean contentAreaFilled) {
            buttons[0].setSelected(true);
            buttons[1].setSelected(false);
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setBorderPainted(borderPainted);
                buttons[i].setContentAreaFilled(contentAreaFilled);
            }

            return buildGrid(buttons[0],
                              buttons[1],
                              FormFactory.BUTTON_COLSPEC);
        }
    private JComponent buildGrid(
            Component c1, 
            Component c2, 
            ColumnSpec colSpec) {
    FormLayout layout = new FormLayout("", "pref,pref,pref,pref,pref,,pref,pref");
    for (int i = 0; i < 4; i++) {
        layout.appendColumn(colSpec);
        layout.appendColumn(FormFactory.RELATED_GAP_COLSPEC);
    }
    PanelBuilder builder = new PanelBuilder(layout);
    builder.getPanel().setOpaque(false);
    CellConstraints cc = new CellConstraints();
   // JLabel label =new JLabel("Année   : ");
   // builder.addLabel("Année :");
   builder.addLabel("Année  :  ", cc.xy(1,1));
    builder.add(c1, cc.xy(3, 1));
    c1.addMouseListener(new P_Action1());
    //saleButton.addActionListener(new PerformSaleAction());
    builder.add(c2, cc.xy(5, 1));
   
    builder.nextLine();
    builder.addLabel("Semestre : ",cc.xy(1,2));
    cb1=new JCheckBox(" 1ère ");
    cb2=new JCheckBox(" 2ème ");
    cb1.setSelected(true);
	cb2.setSelected(false);
	c2.addMouseListener(new P_Action2());
	cb1.addMouseListener(new P_Action3());
	cb2.addMouseListener(new P_Action4());
    builder.add( cb1, cc.xy(3,2));
    builder.add( cb2, cc.xy(5,2));
	builder.nextLine();
	builder.addLabel("Mateire : ",cc.xy(1,3));
	matiere= new JTextField(15);
	matiere.setText("");
	builder.addLabel("Matiere : ",cc.xy(1,3));
	builder.add(matiere,cc.xy(3,3));
	JButton saleButton = UIHelper.createButton("   Load  ");
	saleButton.addActionListener(new PerformSaleAction());
	JButton Button = UIHelper.createButton("   reset  ");
	Button.addActionListener(new PerformresetAction());
	builder.add(saleButton,cc.xy(5,4));
	builder.add(Button,cc.xy(3,4));
	JButton Button1 = UIHelper.createButton("   save  ");
	Button1.addActionListener(new PerformsaveAction());
	builder.add(Button1,cc.xy(1,4));
	JButton Button2 = UIHelper.createButton("clear Tab");
	Button2.addActionListener(new PerformclearAction());
	builder.add(Button2,cc.xy(5,5));
    return builder.getPanel();
}
    /**
     * Builds and returns a sample table.
     */
    private JTable buildTable() {
		this.model = new SalesModel();
		
	
		JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.setRowSelectionInterval(2, 2);
        int tableFontSize    = table.getFont().getSize();
        int minimumRowHeight = tableFontSize + 6;
        int defaultRowHeight = LookUtils.IS_LOW_RESOLUTION ? 17 : 18;
        table.setRowHeight(Math.max(minimumRowHeight, defaultRowHeight));
        return table;
    }

    
    /**
     * Creates and returns a sample tree model.
     */
    private TreeModel createSampleTreeModel() {
		if(Droot==null){
		
        root = new DefaultMutableTreeNode("Etudient");
        DefaultMutableTreeNode parent;

        //
        parent = new DefaultMutableTreeNode("II.1");
        root.add(parent);
        II1S1=new DefaultMutableTreeNode("1er Semestre");
        parent.add(II1S1);
        II1S2=new DefaultMutableTreeNode("2eme Semestre");
        parent.add(II1S2);
        
        //
        parent = new DefaultMutableTreeNode("II.2");
        root.add(parent);
		II2S1=new DefaultMutableTreeNode("1er Semestre");
		parent.add(II2S1);
		II2S2=new DefaultMutableTreeNode("2eme Semestre");
		parent.add(II2S2);

		if(Droot==null)
        	Droot =new DefaultTreeModel(root);
    }else
    {
    	root=(DefaultMutableTreeNode)Droot.getRoot();
    	II1S1=(DefaultMutableTreeNode)root.getChildAt(0).getChildAt(0);
		II1S2=(DefaultMutableTreeNode)root.getChildAt(0).getChildAt(1);
		II2S1=(DefaultMutableTreeNode)root.getChildAt(1).getChildAt(0);
		II2S2=(DefaultMutableTreeNode)root.getChildAt(1).getChildAt(1);
    }
		
        return Droot;
    }

    
    class P_Action1 implements MouseListener {

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
			 */
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
			
				
			}

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
			 */
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				rb2.setSelected(!rb2.isSelected());
			}

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
			 */
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
			 */
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
			 */
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	}
	
	class P_Action2 implements MouseListener {

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
			
			
			
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			rb1.setSelected(!rb1.isSelected());
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	class P_Action3 implements MouseListener {

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
			 */
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
			
				
			}

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
			 */
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				cb2.setSelected(!cb2.isSelected());
			}

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
			 */
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
			 */
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
			 */
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	}
	
	class P_Action4 implements MouseListener {

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
			
			
			
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			cb1.setSelected(!cb1.isSelected());
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	class SalesModel extends AbstractTableModel {

			/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

			private ArrayList items;

			private final String[] columnsNames = new String[] { "Name", "Note" };

			SalesModel() {
				this.items = new ArrayList(15);
			}

			public String[] getItems() {
				return (String[]) items.toArray(new String[0]);
			}

			public String getColumnName(int columnIndex) {
				return columnsNames[columnIndex];
			}

			public int getColumnCount() {
				return columnsNames.length;
			}

			public int getRowCount() {
				return 15;
			}

			public Object getValueAt(int rowIndex, int columnIndex) {
				if (rowIndex >= items.size())
					return null;

				String[] item = (String[]) items.get(rowIndex);
				return item[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columIndex) {
					return true;
			}

			public void setValueAt(Object value, int rowIndex, int columnIndex) {
				String[] item;
				if (rowIndex >= items.size()) {
					item = new String[getColumnCount()];
					items.add(item);
				} else {
					item = (String[]) items.get(rowIndex);
				}

				item[columnIndex] = value.toString();
				fireTableCellUpdated(rowIndex >= items.size() ? items.size() - 1 : rowIndex, columnIndex);
			}
			public int _size(){
				return items.size();				
			}
			public void reset(){
			for(int i=0;i<items.size();i++)
			{
				this.setValueAt("",i,0);
				this.setValueAt("",i,1);
			}
				
		
		}
	}


}
