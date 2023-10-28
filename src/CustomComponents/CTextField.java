package CustomComponents;

import WindowPackage.Window;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CTextField extends JTextField {

    private static final float BORDER_STROKE = 2.5f;
    private static final int DEFAULT_COLUMNS = 14;
    private static final Insets DEFAULT_MARGIN = new Insets(15, 15, 15, 15);

    private String hintText;
    private Color hintColor;

    public CTextField(String hintText, String font, int columns, Color textColor, Color selectionColor, Color hintColor) {
        super();
        this.hintText = hintText;
        this.hintColor = hintColor;

        setOpaque(false);
        setFont(UIManager.getFont(font));
        setForeground(textColor);
        setCaretColor(textColor);

        setSelectedTextColor(selectionColor);
        setSelectionColor(new Color(selectionColor.getRed(),
                selectionColor.getGreen(), selectionColor.getBlue(), 50));

        setBorder(new CBorder(getBorder(), BORDER_STROKE));
        setMargin(DEFAULT_MARGIN);
        setColumns(columns);
        setCursor(Window.TEXT_CURSOR);
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public void setHintColor(Color hintColor) {
        this.hintColor = hintColor;
    }

    public CTextField(String text) {
        this(text, "regular", DEFAULT_COLUMNS, Color.white, CLabel.YELLOW, CLabel.LIGHT_GRAY);
    }

    public CTextField() {
        this("Type...", "regular", DEFAULT_COLUMNS, Color.white, CLabel.YELLOW, CLabel.LIGHT_GRAY);
    }

    public void setFont(String font) {
        setFont(UIManager.getFont(font));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getText().length() == 0) {
            int height = getHeight();

            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            FontMetrics fontMetrics = g.getFontMetrics();

            int bkgColor = getBackground().getRGB();
            int mask = hintColor.getRGB();
            int newColor = ((bkgColor & mask) >>> 1) + ((bkgColor & mask) >>> 1);

            g.setColor(new Color(newColor, true));
            g.drawString(hintText, getInsets().left, height/2 + fontMetrics.getAscent()/2 - 2);
        }
    }

    private static class CBorder implements Border {

        private final Border border;
        private final BasicStroke stroke;

        public CBorder(Border border, float stroke) {
            this.border = border;
            this.stroke = new BasicStroke(stroke);
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(stroke);
            border.paintBorder(c, g2d, x, y, width, height);
        }

        public Insets getBorderInsets(Component c) {
            return border.getBorderInsets(c);
        }

        public boolean isBorderOpaque() {
            return border.isBorderOpaque();
        }
    }
}
