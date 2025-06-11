package screens;

import components.*;
import http.BookHttp;
import model.BookModel;
import vo.Book;

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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BookMngScreen extends JPanel {

    private DefaultTableModel model; // 전역 선언
    private JTable bookTable;        // 전역 선언

    public BookMngScreen(CardLayout cardLayout, JPanel container) {
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

        // BookMngScreen 생성자 마지막에 호출
        SwingUtilities.invokeLater(this::loadAllBooks);
    }

    private void loadAllBooks() {
        try {
            List<Book> books = BookHttp.findAll();
            updateTable(books);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "도서 목록을 불러올 수 없습니다.");
        }
    }

    private void searchBooks(String category, String keyword) {
        try {
            List<Book> books = BookHttp.searchBooks(category, keyword);
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
            String writer = (b.getBookWriter() != null && !b.getBookWriter().isEmpty()) ? b.getBookWriter() : "-";
            String publisher = (b.getBookPublisher() != null && !b.getBookPublisher().isEmpty()) ? b.getBookPublisher() : "-";

            // ✅ 수정된 부분
            String lendStatus = b.getLendNY() == 0 ? "N" : "Y";

            model.addRow(new Object[]{
                    b.getBookId(),
                    b.getBookTitle(),
                    writer,
                    publisher,
                    b.getBookCNum(),
                    lendStatus,
                    "더보기"
            });
        }
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
                cardLayout.show(container, "BookLendingScreen1");
                System.out.println("버튼 클릭됨 - BookLendingScreen1으로 이동");
            }
        });

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
        panel5.setBackground(Color.WHITE); // 배경색 RGB(80, 170, 48)
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
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.getColor(String.valueOf(new Color(200,200,200))));

        // 전체 세로 정렬 박스
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(BorderFactory.createEmptyBorder(60, 50, 50, 60)); // 전체 여백

        // 1. 제목
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false); // 배경 투명
        JLabel titleLabel = new JLabel("도서 관리");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        mainBox.add(titlePanel);

        // 2. 제목 소개
        JPanel indicatorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        indicatorPanel.setOpaque(false);

        JLabel indicatorLabel = new JLabel("모든 도서정보를 조회하세요");
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
                Component parent = SwingUtilities.getRoot(BookMngScreen.this);
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


        searchPanel.add(searchBtn);

        searchAndListPanel.add(searchPanel);
        searchAndListPanel.add(Box.createVerticalStrut(10));

        //리스트
        String[] columnNames = {"고유번호", "도서제목", "저자", "출판사", "청구기호", "대출여부", ""};
        if (model == null) {
            model = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 6;
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

                if (column == 0) {
                    label.setText("<html><u>" + value.toString() + "</u></html>");
                } else if (column == 5 && value != null) {
                    String val = value.toString();
                    if ("Y".equals(val)) {
                        label.setForeground(Color.RED);
                        label.setText("<html><u>Y</u></html>");
                    } else if ("N".equals(val)) {
                        label.setForeground(Color.BLUE);
                        label.setText("<html><u>N</u></html>");
                    }
                }
                return label;
            }
        });

        bookTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(140);

        TableColumn statusColumn = bookTable.getColumnModel().getColumn(5);
        statusColumn.setPreferredWidth(80);
        statusColumn.setMinWidth(80);
        statusColumn.setMaxWidth(80);
        statusColumn.setResizable(false);

        TableColumn buttonColumn = bookTable.getColumnModel().getColumn(6);
        buttonColumn.setCellRenderer(new ButtonRenderer());
        buttonColumn.setCellEditor(new ButtonEditor(new JCheckBox(), bookTable, cardLayout, container));

        JScrollPane scrollPane = new JScrollPane(bookTable);
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

        // 등록 버튼
        RoundedButton addBtn = new RoundedButton("도서 등록");
        addBtn.setFont(new Font("SansSerif", Font.PLAIN, 19));
        addBtn.setPreferredSize(new Dimension(120, 38));
        addBtn.setMaximumSize(addBtn.getPreferredSize());
        addBtn.setMinimumSize(addBtn.getPreferredSize());
        addBtn.setNewColor(new Color(10, 141, 254), new Color(50, 170, 255));
        addBtn.enableGradient(new Color(0x3D, 0xA5, 0xFF), new Color(0x00, 0x89, 0xFF));
        addBtn.setBorderColor(new Color(0x00, 0x6F, 0xFF));
        addBtn.setTextColor(Color.WHITE);

        addBtn.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            BookRegisterDialog dialog = new BookRegisterDialog(topFrame, this::refresh);
            dialog.setVisible(true);
        });


        // 버튼 수평으로 추가
        buttons.add(addBtn);

        // 오른쪽 정렬을 위한 외부 Box
        Box outerBox = Box.createHorizontalBox();
        outerBox.add(Box.createHorizontalGlue()); // 왼쪽 여백 밀기
        outerBox.add(buttons);

        mainBox.add(outerBox);

        rightPanel.add(mainBox, BorderLayout.NORTH);

        return rightPanel;
    }

    public void refresh() {
        loadAllBooks(); // 이미 있는 메서드 활용
    }
}