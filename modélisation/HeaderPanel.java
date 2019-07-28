package modélisation;


import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.jgoodies.plaf.LookUtils;


public class HeaderPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color blender;
    private ImageIcon icon;
    private static AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);


    public HeaderPanel(String title, ImageIcon icon, float alpha) {
        super(new BorderLayout());

        this.icon = icon;
        this.blender = new Color(255, 255, 255, (int) (255 * alpha));

        JLabel headerTitle = new JLabel(title);
        Font font = headerTitle.getFont().deriveFont(Font.BOLD, 25.0f);
        headerTitle.setFont(font);
        headerTitle.setBorder(new EmptyBorder(0, 0, 0, 4));
        headerTitle.setForeground(getHeaderBackground());
        add(BorderLayout.EAST, headerTitle);

        /*setPreferredSize(new Dimension(this.icon.getIconWidth(), this.icon.getIconHeight()));*/
    }


    protected Color getHeaderBackground() {
        Color c =
            UIManager.getColor("SimpleInternalFrame.activeTitleBackground");
        if (c != null)
            return c;
        if (LookUtils.IS_LAF_WINDOWS_XP_ENABLED)
            c = UIManager.getColor("InternalFrame.activeTitleGradient");
        return c != null ? c : UIManager.getColor("InternalFrame.activeTitleBackground");
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isOpaque()) {
            return;
        }

        Color control = UIManager.getColor("control");
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        Paint storedPaint = g2.getPaint();
        g2.setPaint(new GradientPaint(this.icon.getIconWidth(), 0, Color.white, width, 0, control));
        g2.fillRect(0, 0, width, height);
        g2.setPaint(storedPaint);

        g2.drawImage(this.icon.getImage(), 0, 0, this);
        g2.setColor(blender);
        g2.setComposite(composite);
        g2.fillRect(0, 0, this.icon.getIconWidth(), this.icon.getIconHeight());
    }
}
