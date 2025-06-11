package components;

import model.LibModel;
import screens.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LeftSidePanel extends JPanel {

    private void clearScreens(JPanel container) {
        Component[] components = container.getComponents();
        for (Component comp : components) {
            if (comp instanceof BookLendingScreen1
                    || comp instanceof BookReturnScreen
                    || comp instanceof BookMngScreen) {
                container.remove(comp);
            }
        }
    }

    public LeftSidePanel(CardLayout cardLayout, JPanel container) {
        setLayout(new BorderLayout());
        setBackground(new Color(80, 170, 48));

        // 1. 상단 뒤로가기
        JPanel goBackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        goBackPanel.setOpaque(false);
        JButton goBackButton = GoBackButtonFactory.createGoBackButton(true);
        goBackPanel.add(goBackButton);
        add(goBackPanel, BorderLayout.NORTH);

        goBackButton.addActionListener(e -> {
            // 현재 열려있는 화면 삭제
            clearScreens(container);

            // 뒤로 이동
            cardLayout.show(container, "MenuScreen");
            System.out.println("🔙 MenuScreen으로 이동 및 이전 화면 초기화");
        });

        // 2. 가운데 컴포넌트
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(80, 170, 48));

        // 프로필 사진
        ImageIcon icon = new ImageIcon("src/assets/profile.png");
        Image img = icon.getImage().getScaledInstance(117, 167, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(img));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(label);

        content.add(Box.createVerticalStrut(20));
        String libName = LibModel.getInstance().getLoggedInLib().getLibName();
        JLabel name = new JLabel(libName);
        name.setFont(new Font("SansSerif", Font.BOLD, 35));
        name.setForeground(Color.WHITE);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(name);
        content.add(Box.createVerticalStrut(35));

        // 버튼 공통 생성 메서드
        content.add(createButton("📖  도서 대출", container, cardLayout, "BookLendingScreen1", BookLendingScreen1.class));
        content.add(createButton("📚  도서 반납", container, cardLayout, "BookReturnScreen", BookReturnScreen.class));
        content.add(createDialogButton("🛒  도서 대출 관리", "서비스 준비 중입니다."));
        content.add(createDialogButton("📥  도서 반납 관리", "서비스 준비 중입니다."));
        content.add(createButton("🔍  도서 관리", container, cardLayout, "BookMngScreen", BookMngScreen.class, true));

        add(content, BorderLayout.CENTER);
    }

    private JPanel createButton(String text, JPanel container, CardLayout cardLayout, String screenName, Class<? extends JPanel> screenClass) {
        return createButton(text, container, cardLayout, screenName, screenClass, false);
    }

    private JPanel createButton(String text, JPanel container, CardLayout cardLayout, String screenName, Class<? extends JPanel> screenClass, boolean forceReload) {
        JPanel panel = makePanelWithLabel(text);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 무조건 해당 화면 제거 (있든 없든)
                Component[] components = container.getComponents();
                for (Component comp : components) {
                    if (screenClass.isInstance(comp)) {
                        container.remove(comp);
                        break;
                    }
                }

                try {
                    JPanel screen = screenClass.getDeclaredConstructor(CardLayout.class, JPanel.class).newInstance(cardLayout, container);
                    container.add(screen, screenName);
                    cardLayout.show(container, screenName);
                    System.out.println("🔄 " + screenName + "으로 이동 및 새로고침");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return panel;
    }

    private JPanel createDialogButton(String text, String message) {
        JPanel panel = makePanelWithLabel(text);
        JLabel label = (JLabel) panel.getComponent(0); // 내부 label 직접 가져옴

        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 커서 모양도 손으로 바꾸기
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(panel, message, "서비스 준비 중", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel makePanelWithLabel(String text) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(200, 240, 200));
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(330, 60));
        panel.setMaximumSize(new Dimension(330, 60));
        panel.setMinimumSize(new Dimension(330, 60));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel(text, SwingConstants.LEFT);
        label.setFont(new Font("SansSerif", Font.BOLD, 25));
        label.setForeground(Color.BLACK);
        label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }
}
