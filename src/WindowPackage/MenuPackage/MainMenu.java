package WindowPackage.MenuPackage;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <u>Menu Class</u><p>
 * This class has the Initial Menu components.
 */
public class MainMenu extends JPanel {

    /**
     * Calls parent constructor - {@link JPanel#JPanel(LayoutManager)}
     * to set the layout to {@code GridBagLayout}.
     * Sets the Opacity to false and adds necessary components.
     */
    public MainMenu() {
        super(new GridBagLayout());
        setSize(Window.WIDTH, Window.HEIGHT);
        setOpaque(false);

        CLabel titleImage = new CLabel(Window.loadImage("Backgrounds/title", Window.IMAGE_SCALE));
        add(titleImage);

        addButton("Play", 1, 100, new StateChangeButtonEvent(MenuState.GAME));
        addButton("Settings", 2, 10, new StateChangeButtonEvent(MenuState.SETTINGS));
        addButton("Quit", 3, 10, new ExitWindowEvent());
    }

    /**
     * Adds a button with specified constraints and action listener.
     * @param text The text to be displayed.
     * @param gridY The row of the button on the panel.
     * @param top Top padding of the button.
     * @param actionListener Action Listener that is to be added to the button.
     */
    private void addButton(String text, int gridY, int top, ActionListener actionListener) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets.set(top, 0, 10, 0);
        constraints.gridy = gridY;

        CButton button = new CButton(text);
        button.addActionListener(actionListener);
        add(button, constraints);
    }

    /**
     * Action Listener for the menu buttons that will change the {@code MenuState} to the specified state.
     */
    private static record StateChangeButtonEvent(MenuState state) implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MenuContainer.instance.changeState(state);
        }
    }

    /**
     * Action Listener to exit the application.
     */
    private static class ExitWindowEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

}
