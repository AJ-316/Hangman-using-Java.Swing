package CustomComponents;

import Utility.AudioClip;
import WindowPackage.Window;
import com.sun.java.accessibility.util.SwingEventMonitor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * <u>Custom Button class:</u><br/>
 * Used to create a button for custom graphical representation.
 */

public class CButton extends JButton {


    public static final float ICON_SCALE = 0.15f;
    private static final ImageIcon ROLLOVER_CHECKED = WindowPackage.Window.loadImage("Icons/check_rollover", ICON_SCALE);
    private static final ImageIcon ROLLOVER_UNCHECKED = WindowPackage.Window.loadImage("Icons/uncheck_rollover", ICON_SCALE);
    private static final ImageIcon CHECKED = WindowPackage.Window.loadImage("Icons/check", ICON_SCALE);
    private static final ImageIcon UNCHECKED = Window.loadImage("Icons/uncheck", ICON_SCALE);
    private static final ImageIcon RADIO_PRESSED = WindowPackage.Window.loadImage("Icons/radio_pressed", CButton.ICON_SCALE);
    private static final ImageIcon RADIO_ROLLOVER_UNCHECKED = WindowPackage.Window.loadImage("Icons/radio_uncheck_rollover", CButton.ICON_SCALE);
    private static final ImageIcon RADIO_CHECKED = WindowPackage.Window.loadImage("Icons/radio_check", CButton.ICON_SCALE);
    private static final ImageIcon RADIO_UNCHECKED = Window.loadImage("Icons/radio_uncheck", CButton.ICON_SCALE);

    private Color disabledColor = Color.gray;
    private Color normalColor = Color.white;
    private Color pressedColor = Color.lightGray;
    private Color rolloverColor = CLabel.YELLOW;

    private Font normalFont = UIManager.getFont("large");
    private Font disabledFont = UIManager.getFont("strikethrough");

    private boolean isRadio;
    private boolean isChecked;

    private final static CButtonAudioListener audioListener = new CButtonAudioListener();
    private CheckButtonEvent buttonEvent;

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

        setCursor(Window.PRESSED_CURSOR);

        addMouseMotionListener(audioListener);
        addMouseListener(audioListener);
    }

    public void silentClicks() {
        removeMouseListener(audioListener);
        removeMouseMotionListener(audioListener);
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
     * Calls the constructor - {@link CButton#CButton(String, Color, String)}.
     * Used when the button is used as checkbox.
     * @param defaultValue Is button checked or unchecked, initially
     */
    public CButton(String text, Color foreground, String fontSize, boolean defaultValue) {
        this(text, foreground, fontSize);

        buttonEvent = new CheckButtonEvent();

        addActionListener(buttonEvent);

        setChecked(defaultValue);

        setHorizontalTextPosition(SwingConstants.LEFT);
        setVerticalTextPosition(SwingConstants.BOTTOM);
    }

    /**
     * Calls the constructor - {@link CButton#CButton(String, boolean)}.<br/>
     * Sets Default value for button actions, foreground and fontSize
     */
    public CButton(String text, boolean defaultValue) {
        this(text, CLabel.LIGHT_GRAY, "large", defaultValue);
    }

    public CheckButtonEvent getButtonEvent() {
        if(buttonEvent == null) {
            buttonEvent = new CheckButtonEvent();
            addActionListener(buttonEvent);
        }
        return buttonEvent;
    }

    /**
     * Action Listener to check and uncheck button
     */
    public static class CheckButtonEvent implements ActionListener {

        private final ArrayList<Runnable> enabledAction;
        private final ArrayList<Runnable> disabledAction;

        public CheckButtonEvent() {
            this.enabledAction = new ArrayList<>();
            this.disabledAction = new ArrayList<>();
        }

        public void addEnableAction(Runnable action) {
            enabledAction.add(action);
        }

        public void addDisabledAction(Runnable action) {
            disabledAction.add(action);
        }

        private void runEnabledAction() {
            if(enabledAction.size() == 0) return;

            for (Runnable runnable : enabledAction)
                runnable.run();
        }

        private void runDisabledAction() {
            if(disabledAction.size() == 0) return;

            for (Runnable runnable : disabledAction)
                runnable.run();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CButton btnClicked = (CButton) e.getSource();

            if (btnClicked.isChecked) {

                btnClicked.setChecked(false);
                runDisabledAction();

                return;
            }

            btnClicked.setChecked(true);
            runEnabledAction();
        }
    }

    public void setChecked(boolean value) {
        isChecked = value;
        if(isRadio) {
            setIcon(isChecked ? RADIO_CHECKED : RADIO_UNCHECKED);
            setRolloverIcon(isChecked ? RADIO_CHECKED : RADIO_ROLLOVER_UNCHECKED);
            setPressedIcon(RADIO_PRESSED);
            return;
        }
        setIcon(isChecked ? CHECKED : UNCHECKED);
        setRolloverIcon(isChecked ? ROLLOVER_CHECKED : ROLLOVER_UNCHECKED);
        setPressedIcon(getIcon());
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

    private static class CButtonAudioListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getButton() != MouseEvent.BUTTON1)
                return;
            AudioClip.getAudioClip("Press").play();
        }

        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            AudioClip.getAudioClip("Over").play();
        }
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }
}
