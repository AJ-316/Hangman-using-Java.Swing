package WindowPackage.MenuPackage;

import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;

/**
 * <u>Abstract class</u><p>
 * Used to create sub Panels within a menu panel.
 */
public abstract class AbstractSubPanel extends JPanel {

    protected GridBagConstraints constraints;

    /**
     * Sets - layout to GridBag and Opacity to false.
     */
    protected AbstractSubPanel() {
        super(new GridBagLayout());
        setOpaque(false);
        constraints = new GridBagConstraints();
    }

    /**
     * Adds a component to this panel with specified constraints.
     */
    protected void addComponent(JComponent component, int gridX, int gridY, int gridW, int top, int left, int bottom, int right) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridW;
        constraints.insets.set(top, left, bottom, right);
        add(component, constraints);
    }
}
