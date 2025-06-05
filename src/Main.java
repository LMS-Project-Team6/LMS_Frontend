import javax.swing.*;
import java.awt.*;
import screens.MainFrame;
import screens.MenuScreen;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createWindow);
    }

    public static void createWindow() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 👉 전체화면 기능 제거
        frame.setUndecorated(false); // 제목줄 및 창 테두리 표시
        frame.setSize(1500, 800);    // 적당한 창 크기
        frame.setLocationRelativeTo(null); // 화면 중앙 정렬

        // 👉 CardLayout으로 화면 구성
        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        // 👉 메인 프레임 화면 추가
        container.add(new MenuScreen(cardLayout, container), "MenuScreen");
        // container.add(new MainFrame(cardLayout, container), "MainFrame");

        frame.setContentPane(container);
        frame.setVisible(true);
    }
}
