package modélisation;


import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import event.QuitListener;


public class IntroPanel extends JPanel implements QuitListener {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private parseur_Frame parent;
    
    
    public IntroPanel(parseur_Frame parent) {
        super(new BorderLayout());
        setOpaque(false);

        this.parent = parent;
        this.parent.addQuitListener(this);
       
        Font font = new Font("Tahoma", Font.BOLD, 22);
        SimpleInternalFrame managerFrame = new SimpleInternalFrame(UIHelper.readImageIcon("img.PNG"), "");
        add(BorderLayout.CENTER,managerFrame );   
       
       //JLabel label1 = new JLabel("                              Modélisation et surveillance des SW ");
        JLabel label1 = new JLabel("                               ");
        label1.setFont(font);
        label1.setBounds(0, 0, 100, 10);
        label1.setOpaque(false);
        add(BorderLayout.NORTH, label1);
    }

    public void quit() {
        
    }
}
