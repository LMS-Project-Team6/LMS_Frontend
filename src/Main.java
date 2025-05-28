import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import screens.MainFrame;

// test

public class Main {

    // 테스트 코드 작성
    // ABCDE

    private static boolean isFullScreen = true;
    private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createFullScreenWindow);
    }

    public static void createFullScreenWindow() {
        frame = new JFrame("Library Management System");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        container.add(new MainFrame(cardLayout, container), "MainFrame");

        frame.setContentPane(container);  // JPanel을 contentPane에 설정

        // 🔥 포커스 상관없이 키 입력을 전역으로 처리
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F11) {
                toggleFullScreen();
            }
            return false;
        });

        device.setFullScreenWindow(frame);
        frame.setVisible(true);
    }

    public static void toggleFullScreen() {
        frame.dispose();

        if (isFullScreen) {
            device.setFullScreenWindow(null);
            frame.setUndecorated(false);
            frame.setSize(1500, 800);
            frame.setLocationRelativeTo(null);
        } else {
            frame.setUndecorated(true);
            device.setFullScreenWindow(frame);
        }

        isFullScreen = !isFullScreen;
        frame.setVisible(true);
    }
}
