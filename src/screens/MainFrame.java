package screens;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JPanel {

    public MainFrame(CardLayout cardLayout, JPanel container) {
        setLayout(new BorderLayout());

        // LoginScreen과 JoinScreen을 container에 추가
//        container.add(new LoginScreen(cardLayout, container), "LoginScreen");  // LoginScreen을 "LoginScreen" 카드로 추가
//        container.add(new JoinScreen(cardLayout, container), "JoinScreen");
        container.add(new MenuScreen(cardLayout, container), "MenuScreen"); // JoinScreen을 "JoinScreen" 카드로 추가
        container.add(new BookMngScreen(cardLayout, container), "BookMngScreen"); // JoinScreen을 "JoinScreen" 카드로 추가
        container.add(new BookLendingScreen(cardLayout, container), "BookLendingScreen"); // JoinScreen을 "JoinScreen" 카드로 추가
        container.add(new BookReturnScreen(cardLayout, container), "BookReturnScreen"); // JoinScreen을 "JoinScreen" 카드로 추가
        container.add(new BookInfoScreen(cardLayout, container), "BookInfoScreen");
    }
}
