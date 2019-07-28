package modélisation;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;





public final class UIHelper {

 
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(true);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }


    public static JButton createButton(String text, String icon) {
        return createButton(text, icon, false);
    }

    public static JButton createButton(String text, String icon, boolean flat) {
        ImageIcon iconNormal = readImageIcon(icon + ".png");
        ImageIcon iconHighlight = readImageIcon(icon + "_highlight.png");
        ImageIcon iconPressed = readImageIcon(icon + "_pressed.png");

        JButton button = new JButton(text, iconNormal);
        button.setFocusPainted(!flat);
        button.setBorderPainted(!flat);
        button.setContentAreaFilled(!flat);
        if (iconHighlight != null) {
            button.setRolloverEnabled(true);
           button.setRolloverIcon(iconHighlight);
        }
        if (iconPressed != null)
          button.setPressedIcon(iconPressed);
        return button;
    }


    public static void centerOnScreen(Component component) {
        Dimension paneSize = component.getSize();
        Dimension screenSize = component.getToolkit().getScreenSize();
        component.setLocation(
            (screenSize.width - paneSize.width) / 2,
            (screenSize.height - paneSize.height) / 2);
    }

    protected static ImageIcon readImageIcon(String filename) {
        URL url = parseur_main.class.getResource("images/" + filename);
        if (url == null)
            return null;

        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
    }}



