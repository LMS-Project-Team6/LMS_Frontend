package screens;

import components.*;
import http.MemHttp;
import vo.Mem;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BookLendingScreen1 extends JPanel {
    private DefaultTableModel model; // 전역 선언
    private JTable memTable;        // 전역 선언
    public static String selectedMemId = "";
    public static String selectedMemName = "";

    public BookLendingScreen1(CardLayout cardLayout, JPanel container) {
        setLayout(new BorderLayout());

        // 1. 전체 화면을 좌우 2분할하는 스플릿 패널
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(new LeftSidePanel(cardLayout, container)); // 왼쪽: 이미지
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

        SwingUtilities.invokeLater(this::loadAllMems);
    }

    private void loadAllMems() {
        try {
            List<Mem> mems = MemHttp.findAll();
            updateTable(mems);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "회원 목록을 불러올 수 없습니다.");
        }
    }

    private void searchMems(String category, String keyword) {
        try {
            List<Mem> mems = MemHttp.searchMems(category, keyword);
            updateTable(mems);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다.");
        }
    }

    private void updateTable(List<Mem> mems) {
        if (model == null) return;

        model.setRowCount(0);  // 항상 테이블 초기화

        for (Mem m : mems) {
            model.addRow(new Object[]{
                    false, // 체크박스 (Boolean)
                    m.getMemId(),
                    m.getMemName(),
                    m.getMemEmail(),
                    m.getCreatedDate()
            });
        }
        System.out.println("테이블 행 수: " + model.getRowCount());
        memTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickedRow = memTable.rowAtPoint(e.getPoint());
                int clickedCol = memTable.columnAtPoint(e.getPoint());

                if (clickedCol == 0) { // 체크박스 컬럼
                    for (int i = 0; i < model.getRowCount(); i++) {
                        model.setValueAt(false, i, 0); // 모두 체크 해제
                    }
                    model.setValueAt(true, clickedRow, 0); // 현재 행만 체크

                    // ✅ 선택된 ID, 이름 저장
                    BookLendingScreen1.selectedMemId = (String) model.getValueAt(clickedRow, 1);
                    BookLendingScreen1.selectedMemName = (String) model.getValueAt(clickedRow, 2);

                    System.out.println("🔹 선택된 이름: " + BookLendingScreen1.selectedMemName);
                }
            }
        });
    }

    private JPanel rightView(CardLayout cardLayout, JPanel container) {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.getColor(String.valueOf(new Color(200,200,200))));

        // 전체 세로 정렬 박스
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(BorderFactory.createEmptyBorder(60, 50, 50, 60)); // 전체 여백

        // 1. 제목
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false); // 배경 투명
        JLabel titleLabel = new JLabel("도서 대출");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        mainBox.add(titlePanel);

        // 2. 제목 소개
        JPanel indicatorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        indicatorPanel.setOpaque(false);

        JLabel indicatorLabel = new JLabel("1. 대출할 회원을 조회하세요");
        indicatorLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));

        indicatorPanel.add(indicatorLabel);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(indicatorPanel);

        // 3. 구분선 (커스텀 굵은 회색 선으로 직접 그림)
        mainBox.add(Box.createVerticalStrut(10));

        JPanel thickLine = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(192, 192, 192));  // 선 색 (연회색)
                g.fillRect(0, 0, getWidth(), 4);       // 💡 두께: 3px
            }
        };
        thickLine.setPreferredSize(new Dimension(Integer.MAX_VALUE, 4));
        thickLine.setMaximumSize(new Dimension(Integer.MAX_VALUE, 4));
        thickLine.setOpaque(false);

        mainBox.add(thickLine);
        mainBox.add(Box.createVerticalStrut(40));

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
        String[] categories = {"카테고리 선택", "아이디", "이름", "이메일"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        categoryCombo.setPreferredSize(new Dimension(130, 38));
        categoryCombo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        categoryCombo.setBackground(Color.WHITE);
        categoryCombo.setFocusable(false);

        searchPanel.add(categoryCombo);

        // 입력 필드
        RoundedFieldLeft searchField = new RoundedFieldLeft("검색어를 입력하세요");
        searchField.setPreferredSize(new Dimension(300, 38));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                searchField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)  // 왼쪽 8px 여백
        ));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 16));

        searchPanel.add(searchField);

        ImageIcon icon2 = new ImageIcon("src/assets/search_icon.png");
        Image img = icon2.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(img);

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
        searchBtn.addActionListener(e -> {
            if (memTable.isEditing()) {
                memTable.getCellEditor().stopCellEditing(); // 편집 종료
            }

            String category = switch ((String) categoryCombo.getSelectedItem()) {
                case "아이디" -> "memId";
                case "이름" -> "memName";
                case "이메일" -> "memEmail";
                default -> "";
            };
            String keyword = searchField.getText().trim();

            if (category.isEmpty() || keyword.isEmpty()) {
                Component parent = SwingUtilities.getRoot(BookLendingScreen1.this);
                JOptionPane.showMessageDialog(
                        parent,
                        "카테고리와 검색어를 모두 입력하세요.",
                        "입력 오류",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            searchMems(category, keyword);
        });

        searchPanel.add(searchBtn);

        searchAndListPanel.add(searchPanel);
        searchAndListPanel.add(Box.createVerticalStrut(10));

        // 테이블 코드 시작 🥲
        String[] columnNames = {" ", "아이디", "이름", "이메일", "등록일자"};
        Class<?>[] columnTypes = {Boolean.class, String.class, String.class, String.class, String.class};

        if (model == null) {
            model = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // 체크박스(0번 컬럼)와 마지막 컬럼만 편집 가능
                    return column == 0;
                }

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }
            };
        }
        memTable = new JTable(model);
        memTable.setRowHeight(35);
        memTable.setSelectionForeground(memTable.getForeground());
        memTable.setSelectionBackground(memTable.getBackground());
        memTable.setRowSelectionAllowed(false);
        memTable.setFocusable(false);
        memTable.getTableHeader().setPreferredSize(new Dimension(0, 35));
        memTable.getTableHeader().setReorderingAllowed(false);

        memTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(new Font("SansSerif", Font.BOLD, 16));
                label.setBackground(new Color(230, 230, 230));
                label.setForeground(Color.BLACK);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                label.setOpaque(true);
                return label;
            }
        });

        memTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(isSelected ? new Color(220, 240, 255) : Color.WHITE);
                label.setFont(new Font("SansSerif", column == 1 ? Font.BOLD : Font.PLAIN, 16));
                label.setForeground(Color.BLACK);
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

                return label;
            }
        });

        memTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        memTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        memTable.getColumnModel().getColumn(2).setPreferredWidth(140);
        memTable.getColumnModel().getColumn(3).setPreferredWidth(140);
        memTable.getColumnModel().getColumn(4).setPreferredWidth(250);

        JScrollPane scrollPane = new JScrollPane(memTable);
        scrollPane.setPreferredSize(new Dimension(700, 340));

        searchAndListPanel.add(scrollPane);

        // 페이지 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton prevButton = new JButton("<");
        prevButton.setPreferredSize(new Dimension(40, 35));
        prevButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        buttonPanel.add(prevButton);

        JButton[] pageButtons = new JButton[5];
        for (int i = 0; i < 5; i++) {
            pageButtons[i] = new JButton(String.valueOf(i + 1));
            pageButtons[i].setFont(new Font("SansSerif", Font.BOLD, 14));
            if (i == 0) pageButtons[i].setForeground(new Color(80, 170, 48));
            pageButtons[i].setPreferredSize(new Dimension(40, 35));
            int index = i;
            pageButtons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (int j = 0; j < 5; j++) {
                        pageButtons[j].setForeground(Color.BLACK);
                    }
                    pageButtons[index].setForeground(new Color(80, 170, 48));
                    System.out.println("페이지 이동(BookLendingScreen)");
                }
            });
            buttonPanel.add(pageButtons[i]);
        }

        JButton nextButton = new JButton(">");
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(40, 35));
        buttonPanel.add(nextButton);

        // 리스트와 페이징 컨테이너에 추가
        searchAndListPanel.add(Box.createVerticalStrut(10));
        searchAndListPanel.add(buttonPanel);

        // 전체 콘텐츠 영역에 추가
        contentPanel.add(searchAndListPanel, BorderLayout.CENTER);
        mainBox.add(contentPanel);

        mainBox.add(Box.createVerticalStrut(20));

        // 버튼 수평 정렬을 위한 패널
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.setOpaque(false);

        // 다음 버튼
        RoundedButton nextBtn = new RoundedButton("다음");
        nextBtn.setFont(new Font("SansSerif", Font.PLAIN, 19));
        nextBtn.setPreferredSize(new Dimension(120, 38));
        nextBtn.setMaximumSize(nextBtn.getPreferredSize());
        nextBtn.setMinimumSize(nextBtn.getPreferredSize());
        nextBtn.setNewColor(new Color(10, 141, 254), new Color(50, 170, 255));
        nextBtn.enableGradient(new Color(0x3D, 0xA5, 0xFF), new Color(0x00, 0x89, 0xFF));
        nextBtn.setBorderColor(new Color(0x00, 0x6F, 0xFF));
        nextBtn.setTextColor(Color.WHITE);

        nextBtn.addActionListener(e -> {
            int checkedCount = 0;
            String memId = null;
            String memName = null;

            for (int i = 0; i < model.getRowCount(); i++) {
                Boolean checked = (Boolean) model.getValueAt(i, 0);
                if (checked != null && checked) {
                    checkedCount++;
                    memId = (String) model.getValueAt(i, 1);   // 아이디
                    memName = (String) model.getValueAt(i, 2); // 이름 ← 이 줄 추가!
                }
            }

            if (checkedCount == 0) {
                JOptionPane.showMessageDialog(BookLendingScreen1.this, "회원을 선택해주세요.");
                return;
            }

            if (checkedCount > 1) {
                JOptionPane.showMessageDialog(BookLendingScreen1.this, "한 명의 회원만 선택 가능합니다.");
                return;
            }

            // 선택된 memId, memName 저장
            BookLendingScreen1.selectedMemId = memId;
            BookLendingScreen1.selectedMemName = memName;

            // 화면 전환
            cardLayout.show(container, "BookLendingScreen2");
            System.out.println("선택된 memId: " + memId);
        });

        // 버튼 수평으로 추가
        buttons.add(nextBtn);

        // 오른쪽 정렬을 위한 외부 Box
        Box outerBox = Box.createHorizontalBox();
        outerBox.add(Box.createHorizontalGlue()); // 왼쪽 여백 밀기
        outerBox.add(buttons);

        mainBox.add(outerBox);

        rightPanel.add(mainBox, BorderLayout.NORTH);

        return rightPanel;
    }

    public void refresh() {
        loadAllMems(); // 이미 있는 메서드 활용
    }

    public class SelectedMemHolder {
        private static String selectedMemId;

        public static void setSelectedMemId(String memId) {
            selectedMemId = memId;
        }

        public static String getSelectedMemId() {
            return selectedMemId;
        }
    }
}
