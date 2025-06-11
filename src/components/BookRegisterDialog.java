package components;

import http.BookHttp;
import screens.BookInfoScreen;
import screens.BookMngScreen;
import vo.Book;

import javax.swing.*;
import java.awt.*;

public class BookRegisterDialog extends JDialog {
    private RoundedFieldLeft idField, titleField, writerField, publisherField, cnumField;
    private RoundedTextAreaLeft intrdField;

    public BookRegisterDialog(JFrame parent, Runnable onSuccess) {
        super(parent, "도서 등록", true);
        setLayout(new BorderLayout());
        setSize(500, 600);
        setLocationRelativeTo(parent);

        // 전체 세로 정렬 박스
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // 제목
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("도서 등록");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titlePanel.add(titleLabel);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        mainBox.add(titlePanel);

        mainBox.add(Box.createVerticalStrut(8));

        // 구분선
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

        mainBox.add(Box.createVerticalStrut(10));

        // 입력 폼
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));  // 세로 정렬

        idField = new RoundedFieldLeft("도서아이디");
        idField.setPreferredSize(new Dimension(300, 38));
        idField.setBorder(BorderFactory.createCompoundBorder(
                idField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)  // 왼쪽 8px 여백
        ));
        idField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        titleField = new RoundedFieldLeft("도서제목");
        titleField.setPreferredSize(new Dimension(300, 38));
        titleField.setBorder(BorderFactory.createCompoundBorder(
                titleField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)  // 왼쪽 8px 여백
        ));
        titleField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        writerField = new RoundedFieldLeft("도서저자");
        writerField.setPreferredSize(new Dimension(300, 38));
        writerField.setBorder(BorderFactory.createCompoundBorder(
                writerField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)  // 왼쪽 8px 여백
        ));
        writerField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        publisherField = new RoundedFieldLeft("도서출판사");
        publisherField.setPreferredSize(new Dimension(300, 38));
        publisherField.setBorder(BorderFactory.createCompoundBorder(
                publisherField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)  // 왼쪽 8px 여백
        ));
        publisherField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cnumField = new RoundedFieldLeft("도서청구기호");
        cnumField.setPreferredSize(new Dimension(300, 38));
        cnumField.setBorder(BorderFactory.createCompoundBorder(
                cnumField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)  // 왼쪽 8px 여백
        ));
        cnumField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        intrdField = new RoundedTextAreaLeft("도서소개");
        intrdField.setPreferredSize(new Dimension(300, 300));
        intrdField.setBorder(BorderFactory.createCompoundBorder(
                intrdField.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 0, 0)  // 왼쪽 8px 여백
        ));
        intrdField.setFont(new Font("SansSerif", Font.PLAIN, 16));

        Font labelFont = new Font("SansSerif", Font.BOLD, 16); // 원하는 크기로 조절

        formPanel.add(createFieldRow("<html><font color='red'>*</font> 고유번호 :</html>", idField, labelFont));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createFieldRow("<html><font color='red'>*</font> 제목 :</html>", titleField, labelFont));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createFieldRow("<html><font color='red'>*</font> 저자 :</html>", writerField, labelFont));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createFieldRow("<html><font color='red'>*</font> 출판사 :</html>", publisherField, labelFont));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createFieldRow("<html><font color='red'>*</font> 청구기호 :</html>", cnumField, labelFont));
        formPanel.add(Box.createVerticalStrut(15));
        // 소개 필드 (별도 구성)
        JPanel intrdRow = new JPanel(new BorderLayout());
        intrdRow.setOpaque(false);
        intrdRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        JLabel intrdLabel = new JLabel("소개 :");
        intrdLabel.setFont(labelFont);
        intrdLabel.setVerticalAlignment(SwingConstants.TOP); // ✅ 상단 정렬
        intrdLabel.setPreferredSize(new Dimension(100, 30));
        intrdLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        intrdRow.add(intrdLabel, BorderLayout.WEST);
        intrdRow.add(intrdField, BorderLayout.CENTER);


        formPanel.add(intrdRow);

        mainBox.add(formPanel);
        mainBox.add(Box.createVerticalStrut(20));

        // 버튼 수평 정렬을 위한 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 30));
        buttonPanel.setOpaque(false);

        // 취소 버튼
        RoundedButton cancelBtn = new RoundedButton("취소");
        cancelBtn.setFont(new Font("SansSerif", Font.PLAIN, 17));
        cancelBtn.setPreferredSize(new Dimension(120, 30));
        cancelBtn.setMaximumSize(cancelBtn.getPreferredSize());
        cancelBtn.setMinimumSize(cancelBtn.getPreferredSize());
        cancelBtn.enableGradient(new Color(0xFF, 0xFF, 0xFF), new Color(0xFF, 0xFF, 0xFF));
        cancelBtn.setBorderColor(Color.gray);
        cancelBtn.setTextColor(Color.black);
        cancelBtn.addActionListener(e -> dispose());


        // 등록 버튼
        RoundedButton submitBtn = new RoundedButton("등록");
        submitBtn.setFont(new Font("SansSerif", Font.PLAIN, 17));
        submitBtn.setPreferredSize(new Dimension(120, 30));
        submitBtn.setMaximumSize(submitBtn.getPreferredSize());
        submitBtn.setMinimumSize(submitBtn.getPreferredSize());
        submitBtn.setNewColor(new Color(10, 141, 254), new Color(50, 170, 255));
        submitBtn.enableGradient(new Color(0x3D, 0xA5, 0xFF), new Color(0x00, 0x89, 0xFF));
        submitBtn.setBorderColor(new Color(0x00, 0x6F, 0xFF));
        submitBtn.setTextColor(Color.WHITE);
        submitBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String title = titleField.getText().trim();
            String writer = writerField.getText().trim();
            String publisher = publisherField.getText().trim();
            String cnum = cnumField.getText().trim();
            String intrd = intrdField.getText().trim();

            // 유효성 검사
            if (id.isEmpty() || title.isEmpty() || writer.isEmpty() || publisher.isEmpty() || cnum.isEmpty()) {
                JOptionPane.showMessageDialog(this, "필수 항목을 모두 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 📦 Book 객체 생성
            Book book = new Book();
            book.setBookId(id);
            book.setBookTitle(title);
            book.setBookWriter(writer);
            book.setBookPublisher(publisher);
            book.setBookCNum(cnum);
            book.setBookIntrd(intrd);
            book.setLendNY(0);  // 초기 등록 시 기본값

            try {
                boolean success = BookHttp.addBook(book);
                if (success) {
                    JOptionPane.showMessageDialog(this, "도서 등록이 완료되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    if (onSuccess != null) onSuccess.run();
                } else {
                    JOptionPane.showMessageDialog(this, "도서 등록에 실패했습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RuntimeException ex) {
                String errorMessage = ex.getMessage().toLowerCase();  // 소문자 비교

                if (errorMessage.contains("500")) {
                    JOptionPane.showMessageDialog(this, "이미 등록된 도서 아이디입니다.", "중복 오류", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "서버 오류 발생: " + ex.getMessage(), "에러", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "알 수 없는 오류가 발생했습니다:\n" + ex.getMessage(), "에러", JOptionPane.ERROR_MESSAGE);
            }

        });

        // 버튼 수평으로 추가
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelBtn);
        buttonPanel.add(Box.createHorizontalStrut(12));
        buttonPanel.add(submitBtn);                     // 오른쪽

        add(mainBox, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createFieldRow(String labelText, JComponent field, Font font) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout());
        rowPanel.setOpaque(false);
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));  // 높이 조정

        JLabel label = new JLabel(labelText);
        label.setFont(font);
        label.setPreferredSize(new Dimension(100, 38)); // 라벨 폭 고정

        rowPanel.add(label, BorderLayout.WEST);
        rowPanel.add(field, BorderLayout.CENTER);

        return rowPanel;
    }

}
