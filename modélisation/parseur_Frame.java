package mod�lisation;


import event.QuitListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.tree.DefaultTreeModel;



//import menu.QuitAction;

import com.jgoodies.clearlook.ClearLookManager;
import com.jgoodies.plaf.Options;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
;




public class parseur_Frame  extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList quitListeners;
	private Settings settings;
    private SimpleInternalFrame managerFrame;
	SplitTab sale;
	DefaultTreeModel monObjet;
	environnement Split;
 
    public parseur_Frame (Settings settings) {
        this.settings = settings;
        this.quitListeners = new ArrayList();
        
        configureUI();
        build();

        setSize(new java.awt.Dimension(850, 520));
        setResizable(true);
      //  UIHelper.centerOnScreen(this);
       
        
        pack();
        

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt)
			{
				quit();
			}
		});
    }

 
    private void build() {
        setContentPane(buildContentPane());
        setTitle(getWindowTitle());
        
    }

    /**
     * Renvoie le titre de la fenêtre.
     * 
     * @return Le titre de la fenêtre
     */
    private String getWindowTitle() {
        return "Service web";
    }

    /**
     * Construit le panneau de composants principal. Au retour de cette méthode,
     * tous les composants graphiques de la fenêtre ont été créés.
     * 
     * @return Le panneau de composants principal. Il s'agit du père de tous les
     *         composants de l'application.
     */
    private Container buildContentPane() {
        // en-tête et titre
        JPanel headerPanel = new JPanel(new BorderLayout());
        HeaderPanel header = new HeaderPanel("Annuaire service Web", UIHelper.readImageIcon("network.png"), 0.0f);
        headerPanel.add(BorderLayout.NORTH, header);
        headerPanel.add(BorderLayout.SOUTH, new JSeparator(JSeparator.HORIZONTAL));
        headerPanel.setBorder(new EmptyBorder(0, 0, 6, 0));

        // contenu
        SimpleInternalFrame operationsFrame =
            new SimpleInternalFrame(UIHelper.readImageIcon("operations.png"), "Op�rations");
        managerFrame = new SimpleInternalFrame(UIHelper.readImageIcon("manager.png"), "R�sultats");
        JSplitPane splitPane =
            Factory.createStrippedSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                operationsFrame,
                managerFrame,
                0.15f);
        splitPane.setContinuousLayout(true);
        // liste des opérations possibles
        

		JButton viewSale = UIHelper.createButton("D�marrer ", "nv1", true);
		viewSale.setFont(new java.awt.Font("Dialog", 1, 11));
        viewSale.addActionListener(new ViewSaleAction());
       //ici action du bouton
		
        viewSale.addActionListener(null);

		JButton quit = UIHelper.createButton("Quitter", "quit_highlight", true);
		quit.setFont(new java.awt.Font("Dialog", 1, 11));
        quit.addActionListener(new QuitAction());

		Box buttonsBox = Box.createVerticalBox();
		buttonsBox.setOpaque(false);
		//buttonsBox.add(newSale);
		buttonsBox.add(viewSale);
		buttonsBox.add(quit);

		JScrollPane operations = new JScrollPane(buttonsBox);
		operations.setOpaque(false);
		operations.getViewport().setOpaque(false);
		operations.setViewportBorder(null);
        operationsFrame.setContent(operations);

        // contenu par défaut, intro animée
        managerFrame.setContent(buildIntro());
        // panneau de la fenêtre
        JPanel pane = new JPanel(new BorderLayout());
        pane.add(BorderLayout.NORTH, headerPanel);
        pane.add(BorderLayout.CENTER, splitPane);
        return pane;
    }

	/**
     * Construit le composant contenant l'introduction animée.
     * 
     * @return Un panneau contenant une animation.
     */
    private Component buildIntro() {
        return new IntroPanel(this);
    }

    
    private Component buildNewSalePane() {
		
		
			sale = new SplitTab();
		  
		 
		return sale.build();
    }
    
   
    private Component buildViewSalePane() {
		
			Split = new environnement();
		  
        return Split.build();
    }

   
    private void configureUI() {
        Options.setDefaultIconSize(new Dimension(18, 18));

        UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, settings.isUseSystemFonts());
        Options.setGlobalFontSizeHints(settings.getFontSizeHints());
        Options.setUseNarrowButtons(settings.isUseNarrowButtons());

        Options.setTabIconsEnabled(settings.isTabIconsEnabled());
        ClearLookManager.setMode(settings.getClearLookMode());
        ClearLookManager.setPolicy(settings.getClearLookPolicyName());
        UIManager.put(Options.POPUP_DROP_SHADOW_ENABLED_KEY, settings.isPopupDropShadowEnabled());

        LookAndFeel selectedLaf = settings.getSelectedLookAndFeel();
        if (selectedLaf instanceof PlasticLookAndFeel) {
            PlasticLookAndFeel.setMyCurrentTheme(settings.getSelectedTheme());
            PlasticLookAndFeel.setTabStyle(settings.getPlasticTabStyle());
            PlasticLookAndFeel.setHighContrastFocusColorsEnabled(
                settings.isPlasticHighContrastFocusEnabled());
        } else if (selectedLaf.getClass() == MetalLookAndFeel.class) {
            MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
        }

        JRadioButton radio = new JRadioButton();
        radio.getUI().uninstallUI(radio);
        JCheckBox checkBox = new JCheckBox();
        checkBox.getUI().uninstallUI(checkBox);

        try {
            UIManager.setLookAndFeel(selectedLaf);
        } catch (Exception e) {
            System.out.println("Can't change L&F: " + e);
        }
    }

    /**
     * Ajoute un écouteur de fin de session.
     * 
     * @param listener Ecouteur à notifier de la fin de session
     */
    public void addQuitListener(QuitListener listener) {
        quitListeners.add(listener);
    }

    /**
     * Retire l'écouteur.
     * 
     * @param listener L'écouteur à retirer
     */
    public void removeQuitListener(QuitListener listener) {
        quitListeners.remove(listener);
    }

    /**
     * Termine l'application après demande de confirmation.
     */
    public boolean quit() {
        int confirm = JOptionPane.showConfirmDialog(this, "Voulez vous quitter l'application ?",
            "Service web", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION)
        {
        	
            for (Iterator it = quitListeners.iterator(); it.hasNext(); )
                ((QuitListener) it.next()).quit();
            quitListeners.clear();

            dispose();
            return true;
        }

        return false;
    }

    /**
     * Remplace le contenu du panneau de droite par le composant fourni.
     * 
     * @param content Le composant à placer dans le panneau de droite
     */
    private void switchManager(Component content) {
        Component c = managerFrame.getContent();
        if (c != null && c instanceof QuitListener) {
            QuitListener q = (QuitListener) c;
            q.quit();
            removeQuitListener(q);
        }

        managerFrame.setContent(content);
        validate();
        setCursor(null);
    }
    public void _validate(){
    	validate();
    }

   
   public class NewSaleAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    switchManager(buildNewSalePane());
                }
            });
        }
   
    }

   
    public class ViewSaleAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    switchManager(buildViewSalePane());
                }
            });
            
        }
   
    }

    /**
     * Action pour les éléments permettant de quitter l'application.
     * 
     * @author Romain Guy
     */
    public class QuitAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
        	
            parseur_Frame.this.quit();
        }

    }
    

}

