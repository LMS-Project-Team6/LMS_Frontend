package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {
    private Color normalColor = new Color(0x007BFF);     // 기본 파란색
    private Color hoverColor = new Color(0x339AFF);      // 호버 시 밝은 파란색
    private boolean isLockedColor = false;               // ✅ 색상 고정 여부

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);
        setBackground(normalColor);
        setBorder(null);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isLockedColor) {
                    setBackground(hoverColor);
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isLockedColor) {
                    setBackground(normalColor);
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        g2.setColor(getForeground());
        g2.drawString(
                text,
                (getWidth() - textWidth) / 2,
                (getHeight() + textHeight) / 2 - 4
        );

        g2.dispose();
    }

    @Override
    public void setContentAreaFilled(boolean b) {
        // 비워둠: 직접 그리기 사용
    }

    // ✅ 색상 고정 기능 추가 메서드
    public void lockColor(Color fixedColor) {
        this.isLockedColor = true;
        this.setBackground(fixedColor);
        repaint();
    }

    public void unlockColor() {
        this.isLockedColor = false;
        this.setBackground(normalColor);
        repaint();
    }

    public boolean isColorLocked() {
        return isLockedColor;
    }
}
