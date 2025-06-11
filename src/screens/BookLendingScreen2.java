package screens;

import components.*;
import http.BookHttp;
import http.LendReturnHttp;
import vo.Book;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BookLendingScreen2 extends JPanel {
    private DefaultTableModel model; // 전역 선언
    private JTable bookTable;        // 전역 선언
    private JPanel cartPanel;
    private JLabel indicatorLabel;

    public BookLendingScreen2(CardLayout cardLayout, JPanel container) {
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
            @Override
            public void componentShown(ComponentEvent e) {
                refresh();
                updateMemNameLabel(); // 화면이 보일 때마다 최신 이름으로 업데이트
            }
        });

        add(splitPane, BorderLayout.CENTER);

        SwingUtilities.invokeLater(this::loadAllBooks);
    }

    private void updateMemNameLabel() {
        String selectedName = BookLendingScreen1.selectedMemName;
        String nameToShow = (selectedName != null && !selectedName.isEmpty()) ? selectedName : "회원";
        indicatorLabel.setText("2. " + nameToShow + " 님 대출할 도서를 조회하세요");
    }

    private void printCartContents() {
        System.out.println("🛒 현재 장바구니 목록:");

        for (Component comp : cartPanel.getComponents()) {
            if (comp instanceof JPanel itemPanel) {
                for (Component inner : itemPanel.getComponents()) {
                    if (inner instanceof JLabel label) {
                        System.out.println("- " + label.getText());
                        break; // 첫 번째 JLabel이 제목
                    }
                }
            }
        }
        System.out.println("──────────────");
    }

    private void loadAllBooks() {
        try {
            java.util.List<Book> books = BookHttp.findAllAvailability();
            updateTable(books);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "도서 목록을 불러올 수 없습니다.");
        }
        String selectedMemId = BookLendingScreen1.selectedMemId;
        System.out.println("전달된 회원 ID: " + selectedMemId);
    }

    private void searchBooks(String category, String keyword) {
        try {
            java.util.List<Book> books = BookHttp.searchBooks(category, keyword);
            updateTable(books);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다.");
        }
    }

    private void updateTable(List<Book> books) {
        if (model == null) return;

        model.setRowCount(0);  // 항상 테이블 초기화

        for (Book b : books) {
            model.addRow(new Object[]{
                    false,
                    b.getBookId(),
                    b.getBookTitle(),
                    b.getBookWriter(),
                    b.getBookCNum()
            });
        }
    }

    private JPanel rightView(CardLayout cardLayout, JPanel container) {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.getColor(String.valueOf(new Color(200,200,200))));

        // 전체 세로 정렬 박스
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(BorderFactory.createEmptyBorder(60, 60, 10, 60)); // 전체 여백

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

        indicatorLabel = new JLabel(); // 전역 변수에 저장
        updateMemNameLabel(); // 초기 값도 세팅
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
        String[] categories = {"카테고리 선택", "고유번호", "제목", "청구기호"};
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

        ImageIcon icon = new ImageIcon("src/assets/search_icon.png");
        Image img = icon.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        //검색 버튼
        RoundedButton searchBtn = new RoundedButton("");
        searchBtn.setCustomIcon(icon);
        searchBtn.setHorizontalAlignment(SwingConstants.CENTER);
        searchBtn.setPreferredSize(new Dimension(38, 38));
        searchBtn.setMaximumSize(searchBtn.getPreferredSize());
        searchBtn.setMinimumSize(searchBtn.getPreferredSize());
        searchBtn.enableGradient(new Color(0x8C, 0xF2, 0x7F), new Color(0x14, 0xAD, 0x00));
        searchBtn.setBorderColor(new Color(0x0E, 0x7B, 0x00));
        searchBtn.setTextColor(Color.WHITE);
        searchBtn.addActionListener(e -> {
            if (bookTable.isEditing()) {
                bookTable.getCellEditor().stopCellEditing(); // 편집 종료
            }

            String category = switch ((String) categoryCombo.getSelectedItem()) {
                case "고유번호" -> "bookId";
                case "제목" -> "bookTitle";
                case "청구기호" -> "bookCNum";
                default -> "";
            };
            String keyword = searchField.getText().trim();

            if (category.isEmpty() || keyword.isEmpty()) {
                Component parent = SwingUtilities.getRoot(BookLendingScreen2.this);
                JOptionPane.showMessageDialog(
                        parent,
                        "카테고리와 검색어를 모두 입력하세요.",
                        "입력 오류",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            searchBooks(category, keyword);
        });

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
        plusButton.addActionListener(e -> {
            for (int i = 0; i < bookTable.getRowCount(); i++) {
                Boolean isChecked = (Boolean) bookTable.getValueAt(i, 0);
                if (isChecked != null && isChecked) {
                    String title = (String) bookTable.getValueAt(i, 2); // 제목

                    // 중복 방지
                    boolean exists = false;
                    for (Component comp : cartPanel.getComponents()) {
                        if (comp instanceof JPanel panel) {
                            for (Component inner : panel.getComponents()) {
                                if (inner instanceof JLabel label && label.getText().equals(title)) {
                                    exists = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (exists) continue;

                    JPanel bookItem = new JPanel(new BorderLayout());
                    bookItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                    bookItem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    bookItem.setBackground(Color.WHITE);

                    JLabel bookLabel = new JLabel(title);
                    bookLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
                    bookLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    bookLabel.setVerticalAlignment(SwingConstants.TOP);

                    JButton deleteButton = new JButton("X");
                    deleteButton.setBorderPainted(false);
                    deleteButton.setContentAreaFilled(false);
                    deleteButton.setFocusPainted(false);
                    deleteButton.setFont(new Font("SansSerif", Font.BOLD, 16));
                    deleteButton.setForeground(Color.BLACK);
                    deleteButton.setHorizontalAlignment(SwingConstants.RIGHT);
                    bookLabel.setVerticalAlignment(SwingConstants.TOP);

                    deleteButton.addActionListener(event -> {
                        // 🟡 1. 테이블에서 해당 제목의 행 찾기
                        for (int row = 0; row < bookTable.getRowCount(); row++) {
                            String tableTitle = (String) bookTable.getValueAt(row, 2); // 제목 컬럼
                            if (tableTitle.equals(title)) {
                                bookTable.setValueAt(false, row, 0); // 0번 컬럼 = 체크박스
                                break;
                            }
                        }

                        // 🟢 2. 장바구니에서 UI 삭제
                        cartPanel.remove(bookItem);
                        cartPanel.revalidate();
                        cartPanel.repaint();
                        printCartContents();
                    });

                    bookItem.add(bookLabel, BorderLayout.WEST);
                    bookItem.add(deleteButton, BorderLayout.EAST);

                    cartPanel.add(bookItem); // ★ 순서대로 추가 (맨 아래)
                }
            }

            cartPanel.revalidate();
            cartPanel.repaint();
            printCartContents();
        });

        searchPanel.add(searchBtn);
        searchPanel.add(plusButton);

        searchAndListPanel.add(searchPanel);

        JSplitPane bookSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        bookSplitPane.setOpaque(false);
        bookSplitPane.setDividerSize(20); // 간격 10px 생성
        bookSplitPane.setEnabled(false);  // 사용자 드래그 못 하게 비활성화
//        bookSplitPane.setDividerLocation(0.7); // 비율로 나눌 수도 있음
        bookSplitPane.setBorder(BorderFactory.createEmptyBorder()); // remove split‑pane border
        bookSplitPane.setDividerLocation(585);
//        bookSplitPane.setResizeWeight(0.8);

        // 테이블 코드 시작 🥲
        String[] columnNames = {" ", "고유번호", "제목", "저자", "청구기호"};
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
        bookTable = new JTable(model);
        bookTable.setRowHeight(35);
        bookTable.setSelectionForeground(bookTable.getForeground());
        bookTable.setSelectionBackground(bookTable.getBackground());
        bookTable.setRowSelectionAllowed(false);
        bookTable.setFocusable(false);
        bookTable.getTableHeader().setPreferredSize(new Dimension(0, 35));
        bookTable.getTableHeader().setReorderingAllowed(false);

        bookTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
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

        bookTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        bookTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(170);

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        bookSplitPane.setLeftComponent(scrollPane);

        // 장바구니 만들기
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // 상단 헤더
        JLabel headerLabel = new JLabel("도서 장바구니");
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(240, 240, 240)); // 연회색
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // 아래 경계선

        outerPanel.add(headerLabel, BorderLayout.NORTH);

        // 내용 패널 (비워두거나 테이블 추가 가능)
        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBackground(Color.WHITE);
        outerPanel.add(cartPanel, BorderLayout.CENTER);

        // 오른쪽에 장바구니 테이블 연결
        bookSplitPane.setRightComponent(outerPanel);

        bookSplitPane.setPreferredSize(new Dimension(800, 300));
        bookSplitPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));  // 중요!
        bookSplitPane.setMinimumSize(new Dimension(800, 200)); // 최소 크기도 명시
        bookSplitPane.setResizeWeight(0.75);

        // bookSplitPane 생성 아래쪽에 추가
        Box bookSplitBox = Box.createHorizontalBox();
        bookSplitBox.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // 양 옆 여백
        bookSplitBox.add(bookSplitPane);

        // 그 다음에 panel에 넣기
        searchAndListPanel.add(bookSplitBox);
        searchAndListPanel.add(Box.createVerticalStrut(10));

        Box paginationBox = Box.createHorizontalBox();
        paginationBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // 여백 없앰

        // 페이지 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        paginationBox.add(buttonPanel);

        // 전체 콘텐츠 영역에 추가
        contentPanel.add(searchAndListPanel, BorderLayout.CENTER);
        mainBox.add(contentPanel);

        Box buttonBox = Box.createHorizontalBox();
        buttonBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        buttonBox.add(Box.createHorizontalGlue());

        // 버튼 수평 정렬을 위한 패널
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.setOpaque(false);

        // 대출 버튼
        RoundedButton lendBtn = new RoundedButton("대출");
        lendBtn.setFont(new Font("SansSerif", Font.PLAIN, 19));
        lendBtn.setPreferredSize(new Dimension(120, 38));
        lendBtn.setMaximumSize(lendBtn.getPreferredSize());
        lendBtn.setMinimumSize(lendBtn.getPreferredSize());
        lendBtn.setNewColor(new Color(10, 141, 254), new Color(50, 170, 255));
        lendBtn.enableGradient(new Color(0x3D, 0xA5, 0xFF), new Color(0x00, 0x89, 0xFF));
        lendBtn.setBorderColor(new Color(0x00, 0x6F, 0xFF));
        lendBtn.setTextColor(Color.WHITE);

        lendBtn.addActionListener(e -> {
            String memId = BookLendingScreen1.selectedMemId;
            String memName = BookLendingScreen1.selectedMemName;

            if (memId == null || memId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "회원이 선택되지 않았습니다.");
                return;
            }

            // 장바구니에서 도서 제목과 bookId 리스트 추출
            List<String> bookIds = new ArrayList<>();
            List<String> bookTitles = new ArrayList<>();

            for (Component comp : cartPanel.getComponents()) {
                if (comp instanceof JPanel itemPanel) {
                    for (Component inner : itemPanel.getComponents()) {
                        if (inner instanceof JLabel label) {
                            String title = label.getText();
                            bookTitles.add(title);

                            // 테이블에서 제목과 일치하는 bookId 찾기
                            for (int i = 0; i < bookTable.getRowCount(); i++) {
                                if (bookTable.getValueAt(i, 2).equals(title)) {
                                    bookIds.add((String) bookTable.getValueAt(i, 1)); // bookId
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }

            if (bookIds.isEmpty()) {
                JOptionPane.showMessageDialog(this, "장바구니에 도서가 없습니다.");
                return;
            }

            // 확인 다이얼로그
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "회원: " + memName + "\n도서: " + String.join(", ", bookTitles) + "\n\n해당 도서를 대출하시겠습니까?",
                    "도서 대출 확인",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                try {
                    boolean success = LendReturnHttp.lendBooks(memId, bookIds);
                    if (success) {
                        int result2 = JOptionPane.showConfirmDialog(
                                BookLendingScreen2.this,
                                "📚 대출이 완료되었습니다!",
                                "완료",
                                JOptionPane.DEFAULT_OPTION
                        );
                        cartPanel.removeAll(); // 장바구니 초기화
                        cartPanel.revalidate();
                        cartPanel.repaint();
                        if (result2 == JOptionPane.OK_OPTION) {
                            // 1. BookLendingScreen1로 전환
                            cardLayout.show(container, "BookLendingScreen1");

                            // 2. 새로고침을 위해 BookLendingScreen1을 다시 생성 or 갱신 메서드 호출
                            Component[] components = container.getComponents();
                            for (Component comp : components) {
                                if (comp instanceof BookLendingScreen1) {
                                    ((BookLendingScreen1) comp).refresh();
                                    break;
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "대출이 실패했습니다.\n회원당 최대 5권까지만 대출 가능합니다.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "대출 중 오류가 발생했습니다.");
                }
            }
        });

        buttonBox.add(lendBtn);

        Box bottomBox = Box.createVerticalBox();
        bottomBox.setBorder(BorderFactory.createEmptyBorder(0, 10, 30, 10)); // 상단 여백
        bottomBox.add(paginationBox);
        bottomBox.add(Box.createHorizontalGlue());
        bottomBox.add(buttonBox);

        // 오른쪽 정렬을 위한 외부 Box
        Box outerBox = Box.createHorizontalBox();
        outerBox.add(Box.createHorizontalGlue()); // 왼쪽 여백 밀기
        outerBox.add(buttons);

        mainBox.add(outerBox);

        rightPanel.add(mainBox, BorderLayout.NORTH);
        mainBox.add(Box.createVerticalStrut(20));       // 간격 조정
        mainBox.add(bottomBox);                         // ✔ bottomBox를 mainBox에 포함
        rightPanel.add(mainBox, BorderLayout.NORTH);

        return rightPanel;
    }

    public void refresh() {
        System.out.println("🔄 BookLendingScreen2 새로고침 실행됨");

        // 예시: 테이블 초기화 및 대출 가능 도서 다시 불러오기
        model.setRowCount(0); // 테이블 초기화

        try {
            List<Book> availableBooks = BookHttp.findAllAvailability(); // ← 실제 API에 맞게 수정
            for (Book book : availableBooks) {
                model.addRow(new Object[]{
                        false,                     // 체크박스
                        book.getBookId(),
                        book.getBookTitle(),
                        book.getBookWriter(),
                        book.getBookCNum()
                });
            }

            // 장바구니도 초기화
            cartPanel.removeAll();
            cartPanel.revalidate();
            cartPanel.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "도서 목록 로딩 중 오류 발생!");
        }
    }
}