package DocumentListeners;

import CustomComponents.CLabel;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextLengthUpdateListener implements DocumentListener {

    private final CLabel label;
    private final String labelText;

    public TextLengthUpdateListener(CLabel label) {
        this.label = label;
        labelText = label.getText();
        label.setText("");
    }

    private void updateLabelText(int length) {
        label.setText(length == 0 ? "" : labelText + length);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateLabelText(e.getDocument().getLength());
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateLabelText(e.getDocument().getLength());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateLabelText(e.getDocument().getLength());
    }
}