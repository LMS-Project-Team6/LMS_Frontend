package screens;

import components.RoundedButton;
import components.RoundedField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginScreen extends JPanel {

    public LoginScreen(CardLayout cardLayout, JPanel container) {
        setLayout(new BorderLayout());

        // 1. 전체 화면을 좌우 2분할하는 스플릿 패널
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createImagePanel()); // 왼쪽: 이미지
        splitPane.setRightComponent(createRightJoinPanel(cardLayout, container)); // 오른쪽: 로그인 UI
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

    // 3. 오른쪽 로그인 UI 패널
    private JPanel createRightJoinPanel(CardLayout cardLayout, JPanel container) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        Dimension wideSize = new Dimension(500, 40);

        JLabel label1 = new JLabel("LMS와 함께하는 똑똑한 도서 생활");
        label1.setFont(new Font("SansSerif", Font.BOLD, 30));
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label2 = new JLabel("지금 시작하세요");
        label2.setFont(new Font("SansSerif", Font.BOLD, 22));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedField field1 = new RoundedField("👤", "아이디");
        field1.setMaximumSize(wideSize);
        field1.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedField field2 = new RoundedField("🔒", "비밀번호");
        field2.setMaximumSize(wideSize);
        field2.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedButton button = new RoundedButton("로그인");
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setMaximumSize(wideSize);
        button.setPreferredSize(wideSize);
        button.setMinimumSize(wideSize);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label3 = new JLabel("<html>아직 가입하신 적이 없으신가요? <b><u>회원가입</u></b></html>");
        label3.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label3.setForeground(Color.GRAY);

        // 클릭 이벤트 처리
        label3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(container, "JoinScreen");  // "JoinScreen" 카드로 전환
                System.out.println("회원가입 클릭됨 - JoinScreen으로 이동");
            }
        });

        // 내용 추가
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(label1);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(label2);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(field1);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(field2);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(button);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(label3);

        // 각각 왼쪽 정렬로 설정
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        field1.setAlignmentX(Component.LEFT_ALIGNMENT);
        field2.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        label3.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 가운데 정렬을 위한 래퍼 패널
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(centerPanel);

        return rightPanel;
    }

}
