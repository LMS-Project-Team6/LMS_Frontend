package screens;

import components.GoBackButtonFactory;
import components.RoundedButton;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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

        panel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(container, "BookLendingScreen");
                System.out.println("버튼 클릭됨 - BookLendingScreen으로 이동");
            }
        });

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
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(200, 200, 200));

        //전체 세로 정렬 박스
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(BorderFactory.createEmptyBorder(60, 50, 50, 60));

        //1. 제목
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("도서 반납");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));

        titlePanel.add(titleLabel);
        mainBox.add(titlePanel);

        //2. 제목 소개
        JPanel indicatorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        indicatorPanel.setOpaque(false);

        JLabel indicatorLabel = new JLabel("대출한 도서를 반납하세요.");
        indicatorLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));

        indicatorPanel.add(indicatorLabel);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(indicatorPanel);

        //3. 구분선
        mainBox.add(Box.createVerticalStrut(10));

        JPanel thickLine = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(192, 192, 192));
                g.fillRect(0, 0, getWidth(), 4);
            }
        };
        thickLine.setPreferredSize(new Dimension(Integer.MAX_VALUE, 4));
        thickLine.setMaximumSize(new Dimension(Integer.MAX_VALUE, 4));
        thickLine.setOpaque(false);

        mainBox.add(thickLine);
        mainBox.add(Box.createVerticalStrut(20));

        //전체 컨테이너
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setOpaque(false);

        //검색 + 리스트를 나란히 배치할 박스 컨테이너
        JPanel searchAndListPanel = new JPanel();
        searchAndListPanel.setLayout(new BoxLayout(searchAndListPanel, BoxLayout.Y_AXIS));
        searchAndListPanel.setOpaque(false);

        //검색
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);

        //카테고리
        String[] categories = {"카테고리 선택", "제목", "저자", "청구기호"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        categoryCombo.setPreferredSize(new Dimension(130, 30));
        categoryCombo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        categoryCombo.setBackground(Color.WHITE);
        categoryCombo.setFocusable(false);

        searchPanel.add(categoryCombo);

        //검색 입력창
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setForeground(new Color(150, 150, 150));
        searchField.setText("검색어를 입력하세요.");

        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("검색어를 입력하세요.")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(new Color(150, 150, 150));
                    searchField.setText("검색어를 입력하세요.");
                }
            }
        });

        searchField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        searchPanel.add(searchField);

        //돋보기 아이콘
        ImageIcon icon2 = new ImageIcon("src/assets/search_icon.png");
        Image image = icon2.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(image);

        //검색 버튼
        RoundedButton searchBtn = new RoundedButton("");
        searchBtn.setCustomIcon(icon2);
        searchBtn.setHorizontalAlignment(SwingConstants.CENTER);
        searchBtn.setPreferredSize(new Dimension(38, 38));
        searchBtn.setMaximumSize(searchBtn.getPreferredSize());
        searchBtn.setMinimumSize(searchBtn.getPreferredSize());
        searchBtn.enableGradient(new Color(0x8C, 0xF2, 0x7F), new Color(0x14, 0xAD, 0x00));
        searchBtn.setBorderColor(new Color(0x0E, 0x7B, 0x00));
        searchBtn.setTextColor(Color.WHITE);

        searchPanel.add(searchBtn);

        searchAndListPanel.add(searchPanel);
        searchAndListPanel.add(Box.createVerticalStrut(10));

        //리스트
        String[] columnNames = {"", "고유번호", "도서제목", "저자", "청구기호","반납 예정일", "연체여부"};
        Object[][] data = {{false, "A000000001", "어린왕자", "", "", "", "N"}}; //임시 데이터

        //테이블 모델 생성
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) return Boolean.class; //체크 박스 컬럼
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; //체크 박스만 편집 가능
            }
        };

        JTable bookTable = new JTable(model);

        bookTable.getTableHeader().setReorderingAllowed(false);

        //헤더 재정의
        bookTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(label.getFont().deriveFont(Font.BOLD));
                label.setBackground(new Color(230, 230, 230));
                label.setForeground(Color.BLACK);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
                label.setOpaque(true);
                return label;
            }
        });

        //행 렌더러 커스텀
        bookTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                //선택된 행 배경색
                if (isSelected) {
                    label.setBackground(new Color(220, 240, 255));
                    label.setForeground(Color.BLACK);
                }
                else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                }

                //행 구분선 (아래쪽)
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

                //도서 제목 볼드체
                if (column == 2) {
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                }

                //연체 여부 컬럼 색상
                if (column == 6 && value != null) {
                    String val = value.toString();
                    if ("Y".equals(val)) {
                        label.setForeground(Color.RED);
                    }
                    else if ("N".equals(val)) {
                        label.setForeground(Color.BLUE);
                    }
                    else {
                        label.setForeground(Color.BLACK);
                    }
                }
                return label;
            }
        });

        //컬럼 너비 조절
        TableColumn checkColumn = bookTable.getColumnModel().getColumn(0); //체크 박스
        checkColumn.setMinWidth(25);
        checkColumn.setMaxWidth(25);
        checkColumn.setPreferredWidth(25);
        checkColumn.setResizable(false);
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(80); //고유 번호
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(250); //도서 제목
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(100); //저자
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(120); //청구 기호
        bookTable.getColumnModel().getColumn(5).setPreferredWidth(80); //반납 예정일
        TableColumn statusColumn = bookTable.getColumnModel().getColumn(6); //연체 여부
        statusColumn.setMinWidth(60);
        statusColumn.setMaxWidth(60);
        statusColumn.setPreferredWidth(60);
        statusColumn.setResizable(false);

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setPreferredSize(new Dimension(700, 300));

        searchAndListPanel.add(scrollPane);

        //페이지 버튼
        JPanel pagingPanel = new JPanel();
        pagingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 0));
        pagingPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        pagingPanel.setOpaque(false);

        //이전 버튼
        JButton prevBtn = new JButton("<");
        prevBtn.setPreferredSize(new Dimension(40, 30));
        prevBtn.setBackground(new Color(220, 220, 220));
        prevBtn.setForeground(Color.DARK_GRAY);
        prevBtn.setFocusPainted(false);
        prevBtn.setBorder(BorderFactory.createEmptyBorder());
        pagingPanel.add(prevBtn);

        //페이지 번호
        int totalPages = 5;
        List<JButton> pageButtons = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            JButton pageBtn = new JButton(String.valueOf(i));
            pageBtn.setPreferredSize(new Dimension(40, 30));
            pageBtn.setFocusPainted(false);
            pageBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            pageBtn.setBackground(Color.WHITE);
            pageBtn.setForeground(Color.BLACK);
            if (i == 1) {
                pageBtn.setBackground(new Color(0, 180, 0));
                pageBtn.setForeground(Color.WHITE);
            }
            pageButtons.add(pageBtn);
            pagingPanel.add(pageBtn);
        }

        //다음 버튼
        JButton nextBtn = new JButton(">");
        nextBtn.setPreferredSize(new Dimension(40, 30));
        nextBtn.setBackground(new Color(220, 220, 220));
        nextBtn.setForeground(Color.DARK_GRAY);
        nextBtn.setFocusPainted(false);
        nextBtn.setBorder(BorderFactory.createEmptyBorder());
        pagingPanel.add(nextBtn);

        searchAndListPanel.add(pagingPanel);

        contentPanel.add(searchAndListPanel, BorderLayout.CENTER);
        mainBox.add(contentPanel);

        //버튼
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.setOpaque(false);

        //반납 버튼
        RoundedButton returnBtn = new RoundedButton("반납");
        returnBtn.setFont(new Font("SansSerif", Font.PLAIN, 19));
        returnBtn.setPreferredSize(new Dimension(120, 38));
        returnBtn.setMaximumSize(returnBtn.getPreferredSize());
        returnBtn.setMinimumSize(returnBtn.getPreferredSize());

        returnBtn.setNewColor(new Color(10, 141, 254), new Color(50, 170, 255));
        returnBtn.enableGradient(new Color(0x3D, 0xA5, 0xFF), new Color(0x00, 0x89, 0xFF));
        returnBtn.setBorderColor(new Color(0x00, 0x6F, 0xFF));
        returnBtn.setTextColor(Color.WHITE);

        buttonPanel.add(returnBtn);

        //오른쪽 정렬
        Box outerBox = Box.createHorizontalBox();
        outerBox.add(Box.createHorizontalGlue());
        outerBox.add(buttonPanel);

        mainBox.add(outerBox);

        panel.add(mainBox,  BorderLayout.NORTH);

        return panel;
    }
}
