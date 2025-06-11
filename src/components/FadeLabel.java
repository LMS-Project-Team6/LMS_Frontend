package components;

import javax.swing.*;
import java.awt.*;

public class FadeLabel extends JLabel {
    private float alpha = 0f; // 투명도 (0.0f ~ 1.0f)
    private Timer timer;

    public FadeLabel(String text, int delay) {
        super(text);
        setFont(new Font("SansSerif", Font.BOLD, 24));
        setForeground(Color.BLACK);
        setHorizontalAlignment(SwingConstants.CENTER);

        // 애니메이션 타이머 설정
        timer = new Timer(delay, e -> {
            alpha += 0.05f;
            if (alpha >= 1f) {
                alpha = 1f;
                timer.stop();
            }
            repaint();
        });
    }

    public void startFadeIn() {
        alpha = 0f;
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paintComponent(g2);

        g2.dispose();
    }
}
