package CustomComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * <u>Custom Button class:</u><br/>
 * Used to create a button for custom graphical representation.
 */

public class CButton extends JButton {

    private Color disabledColor = Color.gray;
    private Color normalColor = Color.white;
    private Color pressedColor = Color.lightGray;
    private Color rolloverColor = CLabel.YELLOW;

    private Font normalFont = UIManager.getFont("large");
    private Font disabledFont = UIManager.getFont("strikethrough");

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

        UIManager.put("Button.disabledText", disabledColor);

        setForeground(model.isPressed() ? pressedColor : model.isRollover() ? rolloverColor : normalColor);

        Font font = isEnabled() ? normalFont : disabledFont;
        setFont(model.isRollover() || model.isPressed() ? UIManager.getFont("underlineLarge") : font);
    }

    public void setDisabledColor(Color disabledColor) {
        this.disabledColor = disabledColor;
    }

    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    public void setRolloverColor(Color rolloverColor) {
        this.rolloverColor = rolloverColor;
    }

    public void setNormalFont(Font normalFont) {
        this.normalFont = normalFont;
    }

    public void setDisabledFont(Font disabledFont) {
        this.disabledFont = disabledFont;
    }
}
