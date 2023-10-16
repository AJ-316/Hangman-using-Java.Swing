package CustomComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * <u>Custom Button class:</u><br/>
 * Used to create a button for custom graphical representation.
 */

public class CButton extends JButton {

    /**
     * Calls the parent constructor - {@link JButton#JButton(String)}.
     * Few settings like border and font are set
     * @param text The text to display.
     */
    public CButton(String text) {
        super(text);
        setFont(UIManager.getFont("large"));
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setFocusPainted(false);
    }

    /**
     * Calls the constructor - {@link CButton#CButton(String)}.
     * Used when the button is used as an icon display.
     * Manually set {@code foreground} color and {@code fontSize}.
     */
    public CButton(String text, Color foreground, String fontSize) {
        this(text);
        setForeground(foreground);
        setFont(UIManager.getFont(fontSize));
    }

    /**
     * General {@code paintComponent} method with modification of graphics
     * for each button state.
     * @param g Graphics object (Not used in the overridden method)
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(getIcon() != null)
            return;

        if(!isEnabled()) {
            setForeground(Color.red);
            setFont(UIManager.getFont("strikethrough"));
        } else
            setFont(UIManager.getFont("large"));

        if(model.isPressed())
            setForeground(Color.lightGray);
        else
            setForeground(Color.white);

        if(model.isRollover())
            setFont(UIManager.getFont("underlineLarge"));
    }

}
