package screens;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JPanel {

    public MainFrame(CardLayout cardLayout, JPanel container) {
        setLayout(new BorderLayout());

        // LoginScreen과 JoinScreen을 container에 추가
        container.add(new LoginScreen(cardLayout, container), "LoginScreen");  // LoginScreen을 "LoginScreen" 카드로 추가
        container.add(new JoinScreen(cardLayout, container), "JoinScreen");
        container.add(new MenuScreen(), "MenuScreen"); // JoinScreen을 "JoinScreen" 카드로 추가
    }
}
