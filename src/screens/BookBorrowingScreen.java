package screens;

import components.GoBackButtonFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BookBorrowingScreen extends JPanel {
    public BookBorrowingScreen(CardLayout cardLayout, JPanel container) {
        setLayout(new BorderLayout());

        // 1. 전체 화면을 좌우 2분할하는 스플릿 패널
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(leftView(cardLayout, container)); // 왼쪽: 이미지
        splitPane.setRightComponent(rightView(cardLayout, container)); // 오른쪽: 로그인 UI
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);

        // 💡 화면 크기에 따라 50:50 자동 분할되도록 설정
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                splitPane.setDividerLocation(width / 4);
            }
        });

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel leftView(CardLayout cardLayout, JPanel container) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(80, 170, 48));

        // 1-1. 뒤로 가기 프레임
        JPanel goBackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        goBackPanel.setOpaque(false);
        JButton goBackButton = GoBackButtonFactory.createGoBackButton(true);
        goBackPanel.add(goBackButton);
        panel.add(goBackPanel, BorderLayout.NORTH);

        // 1-2. 뒤로 가기 리스너
        goBackButton.addActionListener(e -> {
            cardLayout.show(container, "MenuScreen");
            System.out.println("버튼 클릭됨 - MenuScreen으로 이동");
        });

        // 그 외 컴포넌트들 담는 컨테이너
        JPanel anotherPanel = new JPanel();
        anotherPanel.setLayout(new BoxLayout(anotherPanel, BoxLayout.Y_AXIS));
        anotherPanel.setBackground(new Color(80, 170, 48));

        // 프로필 사진 (디폴트 부엉이 고정)
        ImageIcon icon = new ImageIcon("src/assets/profile.png");
        Image img = icon.getImage().getScaledInstance(117, 167, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(img));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        anotherPanel.add(label);

        // 유저 이름 표시
        anotherPanel.add(Box.createVerticalStrut(20)); // 공간 여백
        JLabel name = new JLabel("안명근"); // DB에서 연결해서 변경 필요 (디폴트는 안명근으로 설정)
        name.setFont(new Font("SansSerif", Font.BOLD, 35));
        name.setForeground(Color.WHITE);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        anotherPanel.add(name);
        anotherPanel.add(Box.createVerticalStrut(35)); // 공간 여백

        // 첫번째 버튼
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(200, 240, 200)); // 배경색 RGB(80, 170, 48)
        panel1.setOpaque(true);
        panel1.setPreferredSize(new Dimension(330, 60)); // 가로 200px, 세로 50px
        panel1.setMinimumSize(new Dimension(330, 60));
        panel1.setMaximumSize(new Dimension(330, 60));
        panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel1.setLayout(new BorderLayout()); // JLabel 중앙 배치를 위해

        JLabel labelInside1 = new JLabel("\uD83D\uDCD6\n 도서 대출", SwingConstants.LEFT);
        labelInside1.setFont(new Font("SansSerif", Font.BOLD, 25));
        labelInside1.setForeground(Color.BLACK);
        labelInside1.setOpaque(false); // JLabel 배경 투명
        labelInside1.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // 상, 좌, 하, 우

        panel1.add(labelInside1, BorderLayout.CENTER);
        anotherPanel.add(panel1);

        // 두번째 버튼
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.WHITE); // 배경색 RGB(80, 170, 48)
        panel2.setOpaque(true);
        panel2.setPreferredSize(new Dimension(330, 60)); // 가로 200px, 세로 50px
        panel2.setMinimumSize(new Dimension(330, 60));
        panel2.setMaximumSize(new Dimension(330, 60));
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setLayout(new BorderLayout()); // JLabel 중앙 배치를 위해

        JLabel labelInside2 = new JLabel("\uD83D\uDCDA\n 도서 반납", SwingConstants.LEFT);
        labelInside2.setFont(new Font("SansSerif", Font.BOLD, 25));
        labelInside2.setForeground(Color.BLACK);
        labelInside2.setOpaque(false); // JLabel 배경 투명
        labelInside2.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // 상, 좌, 하, 우

        panel2.add(labelInside2, BorderLayout.CENTER);
        anotherPanel.add(panel2);

        // 세번째 버튼
        JPanel panel3 = new JPanel();
        panel3.setBackground(new Color(200, 240, 200)); // 배경색 RGB(80, 170, 48)
        panel3.setOpaque(true);
        panel3.setPreferredSize(new Dimension(330, 60)); // 가로 200px, 세로 50px
        panel3.setMinimumSize(new Dimension(330, 60));
        panel3.setMaximumSize(new Dimension(330, 60));
        panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel3.setLayout(new BorderLayout()); // JLabel 중앙 배치를 위해

        JLabel labelInside3 = new JLabel("\uD83D\uDED2\n 도서 대출 관리", SwingConstants.LEFT);
        labelInside3.setFont(new Font("SansSerif", Font.BOLD, 25));
        labelInside3.setForeground(Color.BLACK);
        labelInside3.setOpaque(false); // JLabel 배경 투명
        labelInside3.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // 상, 좌, 하, 우

        panel3.add(labelInside3, BorderLayout.CENTER);
        anotherPanel.add(panel3);

        // 네번째 버튼
        JPanel panel4 = new JPanel();
        panel4.setBackground(new Color(200, 240, 200)); // 배경색 RGB(80, 170, 48)
        panel4.setOpaque(true);
        panel4.setPreferredSize(new Dimension(330, 60)); // 가로 200px, 세로 50px
        panel4.setMinimumSize(new Dimension(330, 60));
        panel4.setMaximumSize(new Dimension(330, 60));
        panel4.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel4.setLayout(new BorderLayout()); // JLabel 중앙 배치를 위해

        JLabel labelInside4 = new JLabel("\uD83D\uDCE5\n 도서 반납 관리", SwingConstants.LEFT);
        labelInside4.setFont(new Font("SansSerif", Font.BOLD, 25));
        labelInside4.setForeground(Color.BLACK);
        labelInside4.setOpaque(false); // JLabel 배경 투명
        labelInside4.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // 상, 좌, 하, 우

        panel4.add(labelInside4, BorderLayout.CENTER);
        anotherPanel.add(panel4);

        // 다섯번째 버튼
        JPanel panel5 = new JPanel();
        panel5.setBackground(new Color(200, 240, 200)); // 배경색 RGB(80, 170, 48)
        panel5.setOpaque(true);
        panel5.setPreferredSize(new Dimension(330, 60)); // 가로 200px, 세로 50px
        panel5.setMinimumSize(new Dimension(330, 60));
        panel5.setMaximumSize(new Dimension(330, 60));
        panel5.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel5.setLayout(new BorderLayout()); // JLabel 중앙 배치를 위해

        JLabel labelInside5 = new JLabel("\uD83D\uDD0D  도서 관리", SwingConstants.LEFT);
        labelInside5.setFont(new Font("SansSerif", Font.BOLD, 25));
        labelInside5.setForeground(Color.BLACK);
        labelInside5.setOpaque(false); // JLabel 배경 투명
        labelInside5.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // 상, 좌, 하, 우

        panel5.add(labelInside5, BorderLayout.CENTER);
        anotherPanel.add(panel5);



        panel.add(anotherPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel rightView(CardLayout cardLayout, JPanel container) {

        return new JPanel();
    }
}
