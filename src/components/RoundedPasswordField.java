package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoundedPasswordField extends JPanel {
    private final JPasswordField passwordField;
    private final String hint;
    private boolean showingHint = true;
    private boolean showPassword = false;
    private JLabel iconLabel;
    private JButton toggleButton;
    private Color borderColor;

    public RoundedPasswordField(String iconText, String hint) {
        this.hint = hint;
        this.borderColor = new Color(200, 200, 200);

        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(500, 40));

        // 좌측 아이콘
        iconLabel = new JLabel(iconText);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        iconLabel.setForeground(Color.GRAY);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 8));
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);

        // 비밀번호 필드
        passwordField = new JPasswordField(hint);
        passwordField.setEchoChar((char) 0); // 힌트 상태에서는 ● 비활성화
        passwordField.setBorder(null);
        passwordField.setOpaque(false);
        passwordField.setForeground(Color.GRAY);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setMargin(new Insets(10, 10, 10, 10));

        // 힌트 포커스 처리
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingHint) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('●');
                    showingHint = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText(hint);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                    showingHint = true;
                }
            }
        });

        // 눈 아이콘 버튼
        ImageIcon eyeIcon = resizeIcon(new ImageIcon("src/assets/eye_on.png"), 18, 18);
        ImageIcon eyeClosedIcon = resizeIcon(new ImageIcon("src/assets/eye_off.png"), 18, 18);

        toggleButton = new JButton(eyeIcon);
        toggleButton.setBorderPainted(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setOpaque(false);
        toggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggleButton.setToolTipText("비밀번호 표시");

        toggleButton.addActionListener(e -> {
            if (showingHint) return;
            showPassword = !showPassword;
            if (showPassword) {
                passwordField.setEchoChar((char) 0);
                toggleButton.setIcon(eyeClosedIcon);
                toggleButton.setToolTipText("비밀번호 숨기기");
            } else {
                passwordField.setEchoChar('●');
                toggleButton.setIcon(eyeIcon);
                toggleButton.setToolTipText("비밀번호 표시");
            }
        });

        add(iconLabel, BorderLayout.WEST);
        add(passwordField, BorderLayout.CENTER);
        add(toggleButton, BorderLayout.EAST);
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public void clearVar() {
        passwordField.setText("");
        borderColor = new Color(200, 200, 200);
        showingHint = true;
        showPassword = false;

        passwordField.setEchoChar((char) 0);
        toggleButton.setIcon(resizeIcon(new ImageIcon("src/assets/eye_on.png"), 18, 18));

        for (FocusListener listener : passwordField.getFocusListeners()) {
            listener.focusLost(new FocusEvent(passwordField, FocusEvent.FOCUS_LOST));
        }
    }

    public String getText() {
        return showingHint ? "" : new String(passwordField.getPassword());
    }

    public JPasswordField getField() {
        return passwordField;
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
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        g2.dispose();
    }
}
