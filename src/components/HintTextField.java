package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintTextField extends JTextField implements FocusListener {
    private final String hint;
    private boolean showingHint = true;

    public HintTextField(String hint) {
        super(hint);
        this.hint = hint;
        setForeground(Color.GRAY);
        addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        // 🔥 포커스 들어오는 순간 힌트는 무조건 제거!
        if (showingHint) {
            setText("");
            setForeground(Color.BLACK);
            showingHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        // 🔥 포커스 잃었을 때 아무 것도 안 쓰여 있으면 힌트 복원
        if (getText().isEmpty()) {
            setText(hint);
            setForeground(Color.GRAY);
            showingHint = true;
        }
    }

    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }
}
