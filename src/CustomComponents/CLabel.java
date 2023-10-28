package CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * <u>Custom Label class:</u><p>
 * Basically used to set the size as per the text/icon to be displayed
 */

public class CLabel extends JLabel {

    public static final Color YELLOW = new Color(241, 206, 1);
    public static final Color RED = new Color(216, 66, 66);
    public static final Color GREEN = new Color(81, 199, 90);
    public static final Color LIGHT_GRAY = new Color(167, 167, 167);

    private RolloverListener rolloverListener;

    public CLabel() {
        this("");
    }

    /**
     * Calls self constructor - {@link CLabel#CLabel(String)}<p>
     *     And sets the icon.
     * @param icon Icon to be displayed.
     */
    public CLabel(Icon icon) {
        this("");
        setIcon(icon);
    }

    /**
     * Calls self constructor - {@link CLabel#CLabel(String, Color)}
     * with default color as white.
     */
    public CLabel(String text) {
        this(text, Color.white);
    }

    /**
     * Calls the Parent Constructor - {@link JLabel#JLabel(String)}<p>
     *     And sets the custom foreground.
     * @param text Text to be displayed.
     * @param color Color of foreground.
     */
    public CLabel(String text, Color color) {
        super(text);
        setForeground(color);
    }

    /**
     * Sets the Width and Height of this component as per the Size(pixels) of the text.
     */
    @Override
    public void setText(String text) {
        super.setText(text);

        Font font = getFont();

        if(font == null) {
            setFont(UIManager.getFont("large"));
            font = getFont();
        }

        // FontMetrics class is used to get the size of current text.
        FontMetrics metrics = getFontMetrics(font);
        setSize(metrics.stringWidth(text), metrics.getHeight());
    }

    /**
     * Sets the icon and resizes as per the size of it.
     */
    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
        setSize(getPreferredSize());
    }

    private static class RolloverListener extends MouseAdapter {

        private Color rollover;
        private Color color;

        public RolloverListener(Color rollover, Color color) {
            setColor(color);
            setRollover(rollover);
        }

        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            ((CLabel) e.getSource()).changeForeground(rollover);
        }

        public void mouseExited(MouseEvent e) {
            super.mouseEntered(e);
            ((CLabel) e.getSource()).changeForeground(color);
        }

        public void setRollover(Color rollover) {
            if(rollover == null) {
                setRollover(0.7f);
                return;
            }
            this.rollover = rollover;
        }

        public void setRollover(float scale) {
            this.rollover = new Color(
                    Math.max(Math.min((int) (color.getRed()*scale), 255), 0),
                    Math.max(Math.min((int) (color.getGreen()*scale), 255), 0),
                    Math.max(Math.min((int) (color.getBlue()*scale), 255), 0));
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);

        if(rolloverListener != null)
            rolloverListener.color = fg;
    }

    private void changeForeground(Color color) {
        super.setForeground(color);
    }

    public void setRollover(Color color) {
        if(rolloverListener == null) {
            rolloverListener = new RolloverListener(color, getForeground());
            addMouseListener(rolloverListener);
            return;
        }

        rolloverListener.setRollover(color);
    }
}