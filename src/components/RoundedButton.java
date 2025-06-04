package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {
    private Color normalColor = new Color(0x007BFF);     // 기본 파란색
    private Color hoverColor = new Color(0x339AFF);      // 호버 시 밝은 파란색
    private boolean isLockedColor = false;               // 색상 고정 여부

    private boolean useShadow = false;                   // 그림자 사용 여부
    private Color borderColor = null;                    // 테두리 색상
    private boolean isLeft = false;                      // 텍스트 왼쪽 정렬 여부

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

    // 🔵 A: 색상 고정 기능
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

    // 🔵 B: 색상, 그림자, 테두리, 정렬 설정
    public void setNewColor(Color normalColor, Color hoverColor) {
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        if (!isLockedColor) {
            setBackground(normalColor);
        }
        repaint();
    }

    public void useShadow() {
        this.useShadow = true;
        repaint();
    }

    public void setTextColor(Color color) {
        setForeground(color);
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void changeLeft() {
        this.isLeft = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (useShadow) {
            g2.setColor(new Color(0, 0, 0, 30));  // 그림자 (살짝 회색)
            g2.fillRoundRect(4, 4, getWidth() - 4, getHeight() - 4, 15, 15);
        }

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        if (borderColor != null) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 15, 15);
        }

        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        g2.setColor(getForeground());
        if (!isLeft) {
            g2.drawString(
                    text,
                    (getWidth() - textWidth) / 2,
                    (getHeight() + textHeight) / 2 - 4
            );
        } else {
            g2.drawString(
                    text,
                    30,
                    (getHeight() + textHeight) / 2 - 4
            );
        }

        g2.dispose();
    }

    @Override
    public void setContentAreaFilled(boolean b) {
        // 무시: 직접 그리기 사용
    }
}
