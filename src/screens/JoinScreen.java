package screens;

import components.GoBackButtonFactory;
import components.RoundedButton;
import components.RoundedField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JoinScreen extends JPanel {

    public JoinScreen(CardLayout cardLayout, JPanel container) {
        setLayout(new BorderLayout());

        // 1. 전체 화면을 좌우 2분할하는 스플릿 패널
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createImagePanel()); // 왼쪽: 이미지
        splitPane.setRightComponent(createRightLoginPanel(cardLayout, container)); // 오른쪽: 로그인 UI
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);

        // 💡 화면 크기에 따라 50:50 자동 분할되도록 설정
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                splitPane.setDividerLocation(width / 2);
            }
        });

        add(splitPane, BorderLayout.CENTER);
    }

    // 2. 왼쪽 이미지 패널
    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        ImageIcon icon = new ImageIcon("src/assets/login_image.jpg"); // 이미지 경로 수정 필요
        Image img = icon.getImage().getScaledInstance(1000, 1500, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(img));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    // 3. 오른쪽 회원가입 UI 패널
    private JPanel createRightLoginPanel(CardLayout cardLayout, JPanel container) {
        // 1. 텍스트 필드 등 메인요소 담는 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // 1-1. 패널에 요소를 담음.
        Dimension wideSize = new Dimension(500, 40);

        JLabel label1 = new JLabel("사서 가입");
        label1.setFont(new Font("SansSerif", Font.BOLD, 30));
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label2 = new JLabel("가입하는 사서 정보를 입력해주세요");
        label2.setFont(new Font("SansSerif", Font.PLAIN, 17));
        label2.setForeground(Color.GRAY);
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Adjust width and thickness

        RoundedField field1 = new RoundedField("👤", "아이디");
        field1.setMaximumSize(wideSize);
        field1.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedField field2 = new RoundedField("🔒", "비밀번호");
        field2.setMaximumSize(wideSize);
        field2.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedField field3 = new RoundedField("😀", "이름");
        field3.setMaximumSize(wideSize);
        field3.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedField field4 = new RoundedField("📧", "이메일");
        field4.setMaximumSize(wideSize);
        field4.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedField field5 = new RoundedField("🎉", "생년월일 ex) 580102");
        field5.setMaximumSize(wideSize);
        field5.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedField field6 = new RoundedField("📞", "전화번호 ex) 01012345678");
        field6.setMaximumSize(wideSize);
        field6.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
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

        // 패널에 요소들 추가
        centerPanel.add(label1);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(label2);
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(separator);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(field1);
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


        // 2. 전체 프레임
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);

        // 3. 상단 좌측 뒤로가기 버튼
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        JButton goBackButton = GoBackButtonFactory.createGoBackButton();
        topPanel.add(goBackButton);
        rightPanel.add(topPanel, BorderLayout.NORTH);

        // 4. 가운데 회원가입 UI (centerPanel을 중앙에 위치)
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(centerPanel);
        rightPanel.add(centerWrapper, BorderLayout.CENTER);

        // 뒤로가기 버튼 기능
        goBackButton.addActionListener(e -> {
            cardLayout.show(container, "LoginScreen");
            System.out.println("버튼 클릭됨 - LoginScreen으로 이동");
        });

        // 중복 검사 리스너
        field1.getField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!field1.getText().isEmpty()) {
                    // 백엔드로 아이디 검사 생략
                    if (false) {
                        field1.setBorderColor(Color.RED);
                    }
                    else {
                        field1.setBorderColor(new Color(200, 200, 200));
                    }
                }
                else {
                    field1.setBorderColor(new Color(200, 200, 200));
                }
            }
        });

        // 입력했을 때 빨간줄 풀기
        field2.getField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!field2.getText().isEmpty()) {
                    field2.setBorderColor(new Color(200, 200, 200));
                }
            }
        });
        field3.getField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!field3.getText().isEmpty()) {
                    field3.setBorderColor(new Color(200, 200, 200));
                }
            }
        });
        field4.getField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!field4.getText().isEmpty()) {
                    field4.setBorderColor(new Color(200, 200, 200));
                }
            }
        });
        field5.getField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!field5.getText().isEmpty()) {
                    field5.setBorderColor(new Color(200, 200, 200));
                }
            }
        });
        field6.getField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!field6.getText().isEmpty()) {
                    field6.setBorderColor(new Color(200, 200, 200));
                }
            }
        });

        // 완료 버튼 리스너
        finishButton.addActionListener(e -> {
            // 오류검사 이상있음
            if (field1.getText().isEmpty() || field2.getText().isEmpty() || field3.getText().isEmpty() ||
                    field4.getText().isEmpty() || field5.getText().isEmpty() || field6.getText().isEmpty()) {
                System.out.println("오류검사 - 빈 내용 있음");
                if(field1.getText().isEmpty()) {
                    field1.setBorderColor(Color.RED);
                }
                else {
                    field1.setBorderColor(new Color(200, 200, 200));
                }
                if(field2.getText().isEmpty()) {
                    field2.setBorderColor(Color.RED);
                }
                else {
                    field2.setBorderColor(new Color(200, 200, 200));
                }
                if(field3.getText().isEmpty()) {
                    field3.setBorderColor(Color.RED);
                }
                else {
                    field3.setBorderColor(new Color(200, 200, 200));
                }
                if(field4.getText().isEmpty()) {
                    field4.setBorderColor(Color.RED);
                }
                else {
                    field4.setBorderColor(new Color(200, 200, 200));
                }
                if(field5.getText().isEmpty()) {
                    field5.setBorderColor(Color.RED);
                }
                else {
                    field5.setBorderColor(new Color(200, 200, 200));
                }
                if(field6.getText().isEmpty()) {
                    field6.setBorderColor(Color.RED);
                }
                else {
                    field6.setBorderColor(new Color(200, 200, 200));
                }
            }
            // 오류검사 이상없음
            else {
                // 아이디 중복 체크 백엔드 처리 생략
                field1.clearVar();
                field2.clearVar();
                field3.clearVar();
                field4.clearVar();
                field5.clearVar();
                field6.clearVar();

                cardLayout.show(container, "LoginScreen");
                System.out.println("버튼 클릭됨 - 오류검사 수행 중 이상없음 / LoginScreen으로 이동");
            }
        });

        return rightPanel;
    }
}
