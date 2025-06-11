package screens;

import components.GoBackButtonFactory;
import components.RoundedButton;
import components.RoundedField;
import components.RoundedPasswordField;
import http.LibHttp;
import vo.Lib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

// ... (imports는 기존 그대로 유지)

public class JoinScreen extends JPanel {

    public JoinScreen(CardLayout cardLayout, JPanel container) {
        setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createImagePanel());
        splitPane.setRightComponent(createRightLoginPanel(cardLayout, container));
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                splitPane.setDividerLocation(width / 2);
            }
        });

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        ImageIcon icon = new ImageIcon("src/assets/login_image.jpg");
        Image img = icon.getImage().getScaledInstance(1000, 1500, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(img));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRightLoginPanel(CardLayout cardLayout, JPanel container) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        Dimension wideSize = new Dimension(500, 40);

        JLabel label1 = new JLabel("사서 가입");
        label1.setFont(new Font("SansSerif", Font.BOLD, 30));
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label2 = new JLabel("가입하는 사서 정보를 입력해주세요");
        label2.setFont(new Font("SansSerif", Font.PLAIN, 17));
        label2.setForeground(Color.GRAY);
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        RoundedField field1 = new RoundedField("\uD83D\uDC64", "아이디");
        RoundedPasswordField field2 = new RoundedPasswordField("\uD83D\uDD12", "비밀번호");
        RoundedField field3 = new RoundedField("\uD83D\uDE00", "이름");
        RoundedField field4 = new RoundedField("\uD83D\uDCE7", "이메일");
        RoundedField field5 = new RoundedField("\uD83C\uDF89", "생년월일 ex) 20000101");
        RoundedField field6 = new RoundedField("\uD83D\uDCDE", "전화번호 ex) 01012345678");

        RoundedField[] fields = { field1, field3, field4, field5, field6 };
        for (RoundedField field : fields) {
            field.setMaximumSize(wideSize);
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        field2.setMaximumSize(wideSize);
        field2.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 아이디 필드 + 중복 검사 버튼 패널
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
        idPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        idPanel.setMaximumSize(wideSize);
        idPanel.setOpaque(false);

        RoundedButton checkButton = new RoundedButton("중복 검사");
        checkButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        checkButton.setPreferredSize(new Dimension(120, 40));
        checkButton.setMaximumSize(new Dimension(120, 40));
        checkButton.setMinimumSize(new Dimension(120, 40));
        checkButton.setForeground(Color.WHITE);

        checkButton.addActionListener(e -> {
            if (!checkButton.isColorLocked()) {
                checkButton.setText("사용 가능");
                checkButton.lockColor(new Color(46, 204, 113)); // 초록색
            }
        });

        idPanel.add(field1);
        idPanel.add(Box.createHorizontalStrut(10));
        idPanel.add(checkButton);

        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        adminPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel adminLabel = new JLabel("관리자여부  ");
        adminLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        JRadioButton yes = new JRadioButton("네");
        JRadioButton no = new JRadioButton("아니요");
        no.setSelected(true);
        ButtonGroup adminGroup = new ButtonGroup();
        adminGroup.add(yes);
        adminGroup.add(no);
        adminPanel.add(adminLabel);
        adminPanel.add(yes);
        adminPanel.add(no);

        RoundedButton finishButton = new RoundedButton("완료");
        finishButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        finishButton.setMaximumSize(wideSize);
        finishButton.setPreferredSize(wideSize);
        finishButton.setMinimumSize(wideSize);
        finishButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(label1);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(label2);
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(separator);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(idPanel);  // 기존 field1 → idPanel 로 변경
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(field2);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(field3);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(field4);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(field5);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(field6);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(adminPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(finishButton);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        JButton goBackButton = GoBackButtonFactory.createGoBackButton(false);
        topPanel.add(goBackButton);
        rightPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(centerPanel);
        rightPanel.add(centerWrapper, BorderLayout.CENTER);

        goBackButton.addActionListener(e -> cardLayout.show(container, "LoginScreen"));

        for (RoundedField field : fields) {
            field.getField().addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    if (!field.getText().isEmpty()) {
                        field.setBorderColor(new Color(200, 200, 200));
                    }
                }
            });
        }
        field2.getField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!field2.getText().isEmpty()) {
                    field2.setBorderColor(new Color(200, 200, 200));
                }
            }
        });

        finishButton.addActionListener(e -> {
            boolean hasEmpty = false;
            if (field1.getText().isEmpty()) {
                field1.setBorderColor(Color.RED);
                hasEmpty = true;
            } else {
                field1.setBorderColor(new Color(200, 200, 200));
            }
            if (field2.getText().isEmpty()) {
                field2.setBorderColor(Color.RED);
                hasEmpty = true;
            } else {
                field2.setBorderColor(new Color(200, 200, 200));
            }
            for (RoundedField field : fields) {
                if (field.getText().isEmpty()) {
                    field.setBorderColor(Color.RED);
                    hasEmpty = true;
                } else {
                    field.setBorderColor(new Color(200, 200, 200));
                }
            }

            if (hasEmpty) {
                System.out.println("오류검사 - 빈 내용 있음");
                return;
            }

            Lib newLib = new Lib();
            newLib.setLibId(field1.getText());
            newLib.setLibPw(field2.getText());
            newLib.setLibName(field3.getText());
            newLib.setLibEmail(field4.getText());
            newLib.setLibBirth(field5.getText());
            newLib.setLibPNum(field6.getText());
            newLib.setAdminNY(yes.isSelected() ? 1 : 0);
            newLib.setApplyNY(0);

            try {
                boolean result = LibHttp.register(newLib);
                if (result) {
                    JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.");
                    field1.clearVar();
                    field2.clearVar();
                    for (RoundedField field : fields) field.clearVar();
                    cardLayout.show(container, "LoginScreen");
                    System.out.println("회원가입 성공 → LoginScreen으로 이동");
                } else {
                    JOptionPane.showMessageDialog(this, "회원가입에 실패했습니다. 서버 응답을 확인하세요.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "회원가입 중 오류가 발생했습니다.\n" + ex.getMessage());
            }
        });

        return rightPanel;
    }
}
