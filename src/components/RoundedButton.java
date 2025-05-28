package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {
    private Color normalColor = new Color(0x007BFF);     // 기본 파란색
    private Color hoverColor = new Color(0x339AFF);      // 호버 시 밝은 파란색

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);
        setBackground(new Color(0x007BFF));
        setBorder(null);
        setOpaque(false);

        // ✅ 마우스 호버 효과 추가
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // 둥글고 부드러운 외곽선 처리
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 배경 그리기 (둥근 모양)
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // 텍스트 중앙 정렬 그리기
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
        // 내용 배경 채우기 막기 (paintComponent에서 직접 그림)
    }
}
