package screens;

import components.GoBackButtonFactory;
import components.RoundedButton;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class BookLendingScreen2 extends JPanel {
    public BookLendingScreen2(CardLayout cardLayout, JPanel container) {
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
            cardLayout.show(container, "BookLendingScreen1");
            System.out.println("버튼 클릭됨 - BookLendingScreen1으로 이동");
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
                cardLayout.show(container, "BookReturnScreen");
                System.out.println("버튼 클릭됨 - BookReturnScreen으로 이동");
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
        JLabel subtitle = new JLabel("2. 대출할 도서를 조회하세요");
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

        String[] categories = { "카테고리 선택   ", "고유번호", "도서 제목", "청구 기호" };
        JComboBox<String> categoryBox = new JComboBox<String>(categories);
        categoryBox.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(6),
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

        ImageIcon icon2 = new ImageIcon("src/assets/plus_icon.png");
        Image img2 = icon2.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(img2);

        RoundedButton plusButton = new RoundedButton("");
        plusButton.setCustomIcon(icon2);
        plusButton.setHorizontalAlignment(SwingConstants.CENTER);
        plusButton.setPreferredSize(new Dimension(38, 38));
        plusButton.setMaximumSize(plusButton.getPreferredSize());
        plusButton.setMinimumSize(plusButton.getPreferredSize());
        plusButton.enableGradient(new Color(0x8C, 0xF2, 0x7F), new Color(0x14, 0xAD, 0x00));
        plusButton.setBorderColor(new Color(0x0E, 0x7B, 0x00));
        plusButton.setTextColor(Color.WHITE);

        searchPanel.add(categoryBox);
        searchPanel.add(Box.createHorizontalStrut(5)); // 가로 방향으로 50px 띄우기
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5)); // 가로 방향으로 50px 띄우기
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(5)); // 가로 방향으로 50px 띄우기
        searchPanel.add(plusButton);
        panel1.add(Box.createVerticalStrut(30));
        panel1.add(searchPanel);

        panel1.add(Box.createVerticalStrut(15));

        // 테이블 + 장바구니 코드 시작 🥲
        JSplitPane bookSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        bookSplitPane.setOpaque(false);
        bookSplitPane.setDividerSize(20); // 간격 10px 생성
        bookSplitPane.setEnabled(false);  // 사용자 드래그 못 하게 비활성화
//        bookSplitPane.setDividerLocation(0.7); // 비율로 나눌 수도 있음
        bookSplitPane.setBorder(BorderFactory.createEmptyBorder()); // remove split‑pane border
        bookSplitPane.setDividerLocation(585);
//        bookSplitPane.setResizeWeight(0.8);

        String[] columnNames = { " ", "고유번호", "도서 제목", "청구 기호" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // 컬럼 길이 조절
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setMinWidth(40);
        columnModel.getColumn(0).setMaxWidth(40);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        bookSplitPane.setLeftComponent(scrollPane);

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // 전체 테두리

        // 상단 헤더
        JLabel headerLabel = new JLabel("도서 장바구니");
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(240, 240, 240)); // 연회색
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // 아래 경계선

        outerPanel.add(headerLabel, BorderLayout.NORTH);

        // 내용 패널 (비워두거나 테이블 추가 가능)
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        outerPanel.add(contentPanel, BorderLayout.CENTER);

        // 오른쪽에 장바구니 테이블 연결
        bookSplitPane.setRightComponent(outerPanel);

        panel1.add(bookSplitPane);
        // 테이블 + 장바구니 코드 끝 😀

        panel1.add(Box.createVerticalStrut(10));

        // 페이지 패널
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20)); // 최대 높이 제한
//        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flowPanel.setBorder(BorderFactory.createEmptyBorder(0, 130, 0, 0));  // 상, 좌, 하, 우
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
            int index = i;
            pageButtons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 페이지 API 동작 코드 여기에
                    for (int i = 0; i < 5; i++) {
                        pageButtons[i].setForeground(Color.BLACK);
                    }
                    pageButtons[index].setForeground(Color.GREEN);
                    System.out.println("페이지 이동(BookLendingScreen)");
                }
            });
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
        RoundedButton nextCardButton = new RoundedButton("대출");
        Dimension size = new Dimension(100, 32);
        nextCardButton.setMaximumSize(size);
        nextCardButton.setPreferredSize(size);
        nextCardButton.setMinimumSize(size);
        nextCardButtonPanel.add(nextCardButton);
        panel1.add(nextCardButtonPanel);

        nextCardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(container, "MenuScreen");
                System.out.println("버튼 클릭됨 - MenuScreen으로 이동");
            }
        });

        panel1.add(Box.createVerticalStrut(80));

        return panel1;
    }
}