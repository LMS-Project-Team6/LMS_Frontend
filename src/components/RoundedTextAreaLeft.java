package components;

import javax.swing.*;
import java.awt.*;

public class RoundedTextAreaLeft extends JPanel {
    private final JTextArea textArea;
    private final String hint;
    private boolean showingHint = true;
    private Color borderColor;

    public RoundedTextAreaLeft(String hint) {
        this.hint = hint;
        this.borderColor = new Color(200, 200, 200);
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(300, 80)); // 기본 높이를 더 줌

        textArea = new JTextArea(hint, 4, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(null);
        textArea.setOpaque(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setForeground(Color.GRAY);
        textArea.setMargin(new Insets(8, 10, 8, 10));

        textArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (showingHint) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                    showingHint = false;
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (textArea.getText().trim().isEmpty()) {
                    textArea.setText(hint);
                    textArea.setForeground(Color.GRAY);
                    showingHint = true;
                }
            }
        });

        add(textArea, BorderLayout.CENTER);
    }

    public String getText() {
        return showingHint ? "" : textArea.getText();
    }

    public JTextArea getTextArea() {
        return textArea;
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
