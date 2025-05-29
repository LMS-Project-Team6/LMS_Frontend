package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RoundedPasswordField extends JPanel {
    private final JPasswordField passwordField;
    private final String hint;
    private final JLabel iconLabel;
    private boolean showingHint = true;
    private Color borderColor = new Color(200, 200, 200);

    public RoundedPasswordField(String iconText, String hint) {
        this.hint = hint;
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(500, 40));

        iconLabel = new JLabel(iconText);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        iconLabel.setForeground(Color.GRAY);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 8));
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);

        passwordField = new JPasswordField();
        passwordField.setBorder(null);
        passwordField.setOpaque(false);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setForeground(Color.GRAY);
        passwordField.setText(hint);
        passwordField.setEchoChar((char) 0); // 평문 보이게

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingHint) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('●'); // 마스킹 처리
                    showingHint = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText(hint);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0); // 다시 평문
                    showingHint = true;
                }
            }
        });

        add(iconLabel, BorderLayout.WEST);
        add(passwordField, BorderLayout.CENTER);
    }

    public String getText() {
        return showingHint ? "" : new String(passwordField.getPassword());
    }

    public void clear() {
        passwordField.setText(hint);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);
        showingHint = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

        g2.dispose();
    }
}
