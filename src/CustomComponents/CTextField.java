package CustomComponents;

import WindowPackage.Window;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class CTextField extends JPasswordField {

    private static final BasicStroke BORDER_STROKE = new BasicStroke(1.4f);
    private static final int DEFAULT_COLUMNS = 14;
//    private static final Insets DEFAULT_MARGIN = new Insets(40, 15, 40, 15);

    private String hintText;
    private Color hintColor;
    private String errorText;
    private Color errorColor;

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

        setBorder(new EmptyBorder(32, 15, 32, 15));
//        setBorder(new CBorder(getBorder(), 1));
//        setMargin(DEFAULT_MARGIN);
        setColumns(columns);
        setCursor(Window.TEXT_CURSOR);
        setEchoChar((char)0);
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
        Graphics2D g2d = (Graphics2D) g;

        // Draw Hint
        if (getDocument().getLength() == 0) {
            int height = getHeight();

            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            FontMetrics fontMetrics = g2d.getFontMetrics();

            int bkgColor = getBackground().getRGB();
            int mask = hintColor.getRGB();
            int newColor = ((bkgColor & mask) >>> 1) + ((bkgColor & mask) >>> 1);

            g2d.setColor(new Color(newColor, true));
            g2d.drawString(hintText, getInsets().left, height/2 + fontMetrics.getAscent()/2 - 2);
        }

        // Draw Border
        g2d.setColor(Color.white);
        g2d.setStroke(BORDER_STROKE);
        int top = getBorder().getBorderInsets(this).top + 14;
        g2d.drawRect(1, top/2 - 4, getWidth() - 2, getHeight() - top);

        // Draw Alert
        g2d.setFont(UIManager.getFont("small_alert"));
        g2d.setColor(Color.black);
        g2d.drawString("", 10, getHeight() - 6);
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

    public String getInputText() {
        try {
            return getDocument().getText(0, getDocument().getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
}
