package screens;

import components.GoBackButtonFactory;
import components.RoundedButton;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class BookLendingScreen extends JPanel {
    public BookLendingScreen(CardLayout cardLayout, JPanel container) {
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
        panel1.setBackground(Color.WHITE); // 배경색 RGB(80, 170, 48)
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
        panel2.setBackground(new Color(200, 240, 200)); // 배경색 RGB(80, 170, 48)
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

        panel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(container, "BookBorrowingScreen");
                System.out.println("버튼 클릭됨 - BookBorrowingScreen으로 이동");
            }
        });

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

        panel5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(container, "BookMngScreen");
                System.out.println("버튼 클릭됨 - BookMngScreen으로 이동");
            }
        });



        panel.add(anotherPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel rightView(CardLayout cardLayout, JPanel container) {
        // 오른쪽만 화면 전환
        CardLayout innerCardLayout = new CardLayout();
        JPanel innerContainer = new JPanel(cardLayout);

        // 전환 패널 1
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50)); // 좌우 여백

        panel1.add(Box.createVerticalStrut(50));
        JLabel title = new JLabel("도서 대출");
        title.setFont(new Font("SansSerif", Font.BOLD, 40));
        title.setForeground(Color.BLACK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel1.add(title);

        panel1.add(Box.createVerticalStrut(8));
        JLabel subtitle = new JLabel("1. 대출할 회원을 조회하세요");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 17));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel1.add(subtitle);

        panel1.add(Box.createVerticalStrut(10));
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        panel1.add(separator);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.setOpaque(false);

        String[] categories = { "카테고리 선택   ", "아이디", "이름", "이메일" };
        JComboBox<String> categoryBox = new JComboBox<String>(categories);
        categoryBox.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        searchField.setText("검색어를 입력하세요.");
        searchField.setForeground(Color.GRAY);

        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("검색어를 입력하세요.")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("검색어를 입력하세요.");
                }
            }
        });

        ImageIcon icon = new ImageIcon("src/assets/search_icon.png");
        Image img = icon.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        RoundedButton searchButton = new RoundedButton("");
        searchButton.setCustomIcon(icon);
        searchButton.setHorizontalAlignment(SwingConstants.CENTER);
        searchButton.setPreferredSize(new Dimension(38, 38));
        searchButton.setMaximumSize(searchButton.getPreferredSize());
        searchButton.setMinimumSize(searchButton.getPreferredSize());
        searchButton.enableGradient(new Color(0x8C, 0xF2, 0x7F), new Color(0x14, 0xAD, 0x00));
        searchButton.setBorderColor(new Color(0x0E, 0x7B, 0x00));
        searchButton.setTextColor(Color.WHITE);

        searchPanel.add(categoryBox);
        searchPanel.add(Box.createHorizontalStrut(5)); // 가로 방향으로 50px 띄우기
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5)); // 가로 방향으로 50px 띄우기
        searchPanel.add(searchButton);
        panel1.add(Box.createVerticalStrut(30));
        panel1.add(searchPanel);

        panel1.add(Box.createVerticalStrut(15));

        // 테이블 코드 시작 🥲
        String[] columnNames = { " ", "아이디", "이름", "이메일", "등록일자", " " };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // 컬럼 길이 조절
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setMinWidth(40);
        columnModel.getColumn(0).setMaxWidth(40);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(2).setMinWidth(150);
        columnModel.getColumn(2).setMaxWidth(150);
        columnModel.getColumn(4).setPreferredWidth(150);
        columnModel.getColumn(4).setMinWidth(150);
        columnModel.getColumn(4).setMaxWidth(150);
        columnModel.getColumn(5).setPreferredWidth(150);
        columnModel.getColumn(5).setMinWidth(150);
        columnModel.getColumn(5).setMaxWidth(150);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel1.add(scrollPane);
        // 테이블 코드 끝 😀

        panel1.add(Box.createVerticalStrut(10));

        // 페이지 패널
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20)); // 최대 높이 제한
//        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        flowPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        JButton prevButton = new JButton("<");
        prevButton.setPreferredSize(new Dimension(40, 35)); // 또는 원하는 크기
        prevButton.setMinimumSize(new Dimension(40, 35));
        prevButton.setMaximumSize(new Dimension(40, 35));
        flowPanel.add(prevButton);

        JButton[] pageButtons = new JButton[5];
        for (int i = 0; i < 5; i++) {
            pageButtons[i] = new JButton(String.valueOf(i + 1));
            if (i == 0) pageButtons[i].setForeground(Color.GREEN); // 첫 번째 페이지 강조
            pageButtons[i].setOpaque(false);
            pageButtons[i].setPreferredSize(new Dimension(40, 35)); // 또는 원하는 크기
            pageButtons[i].setMinimumSize(new Dimension(40, 35));
            pageButtons[i].setMaximumSize(new Dimension(40, 35));
            flowPanel.add(pageButtons[i]);
        }

        JButton nextButton = new JButton(">");
        nextButton.setPreferredSize(new Dimension(40, 35)); // 또는 원하는 크기
        nextButton.setMinimumSize(new Dimension(40, 35));
        nextButton.setMaximumSize(new Dimension(40, 35));

        flowPanel.add(nextButton);

        buttonPanel.add(flowPanel, BorderLayout.NORTH);
        panel1.add(buttonPanel);

        // 다음 버튼
        JPanel nextCardButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        nextCardButtonPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        nextCardButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        RoundedButton nextCardButton = new RoundedButton("다음");
        Dimension size = new Dimension(100, 30);
        nextCardButton.setMaximumSize(size);
        nextCardButton.setPreferredSize(size);
        nextCardButton.setMinimumSize(size);
        nextCardButtonPanel.add(nextCardButton);
        panel1.add(nextCardButtonPanel);

        panel1.add(Box.createVerticalStrut(80));

        innerContainer.add(panel1, "panel1");

        return innerContainer;
    }
}

// Custom rounded border class
class RoundedBorder extends AbstractBorder {
    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.BLACK);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.top = insets.bottom = radius + 1;
        return insets;
    }
}