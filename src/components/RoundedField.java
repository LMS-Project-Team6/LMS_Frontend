package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RoundedField extends JPanel {
    private final JTextField textField;
    private final String hint;
    private boolean showingHint = true;
    private JLabel iconLabel;
    private Color borderColor;

    public RoundedField(String iconText, String hint) {
        borderColor = new Color(200, 200, 200);
        this.hint = hint;
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(500, 40));

        // 👤 or 🔒 아이콘
        this.iconLabel = new JLabel(iconText);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        iconLabel.setForeground(Color.GRAY);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 8));
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);

        // 텍스트 필드 + 힌트 기능
        textField = new JTextField(hint);
        textField.setBorder(null);
        textField.setOpaque(false);
        textField.setForeground(Color.GRAY);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textField.setMargin(new Insets(10, 10, 10, 10));

        // 힌트 사라지게 하는 포커스 이벤트
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingHint) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    showingHint = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(hint);
                    textField.setForeground(Color.GRAY);
                    showingHint = true;
                }
            }
        });

        add(iconLabel, BorderLayout.WEST);
        add(textField, BorderLayout.CENTER);
    }

    public void clearVar() {
        this.textField.setText("");
        this.borderColor = new Color(200, 200, 200);
        this.showingHint = true;

        // focusLost 강제 실행
        for (FocusListener listener : textField.getFocusListeners()) {
            listener.focusLost(new FocusEvent(textField, FocusEvent.FOCUS_LOST));
        }
    }

    public String getText() {
        return showingHint ? "" : textField.getText();
    }

    public JTextField getField() {
        return textField;
    }

    public void setIconText(String text) {
        iconLabel.setText(text);
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 둥근 배경 그리기
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // 테두리
        g2.setColor(this.borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        g2.dispose();
    }
}