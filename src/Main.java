import javax.swing.*;
import java.awt.*;

import screens.*;
import vo.Book;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createWindow);
    }

    public static void createWindow() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setUndecorated(false);
        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        Book dummyBook = new Book();
        dummyBook.setBookId("도서아이디");
        dummyBook.setBookTitle("도서제목");
        dummyBook.setBookWriter("도서저자");
        dummyBook.setBookPublisher("도서출판사");
        dummyBook.setBookCNum("도서청구기호");
        dummyBook.setLendNY(0);
        dummyBook.setBookIntrd("도서소개");

        // 👇 모든 화면을 직접 등록 (MainFrame.java 없이!)
        container.add(new LoginScreen(cardLayout, container), "LoginScreen");
        container.add(new JoinScreen(cardLayout, container), "JoinScreen");
        container.add(new MenuScreen(cardLayout, container), "MenuScreen");
        container.add(new BookMngScreen(cardLayout, container), "BookMngScreen");
        container.add(new BookLendingScreen1(cardLayout, container), "BookLendingScreen1");
        container.add(new BookLendingScreen2(cardLayout, container), "BookLendingScreen2");
        container.add(new BookReturnScreen(cardLayout, container), "BookReturnScreen");
        container.add(new BookInfoScreen(cardLayout, container, dummyBook), "BookInfoScreen");

        frame.setContentPane(container);
        frame.setVisible(true);

        // 초기화면 지정
        cardLayout.show(container, "LoginScreen");
    }
}
