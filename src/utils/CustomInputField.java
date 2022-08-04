package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomInputField extends JTextField {
    public CustomInputField(String text) {
        super(text);
        setForeground(Color.GRAY);
        setPreferredSize(new Dimension(100, 20));
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setText("");
                setForeground(Color.BLACK);
            }

            public void focusLost(FocusEvent e) {
                if (getText().equals("")) {
                    setText(text);
                    setForeground(Color.GRAY);
                }
            }
        });
    }

}
