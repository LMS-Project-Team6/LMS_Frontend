package screens;

import components.RoundedPasswordField;
import components.RoundedButton;
import components.RoundedField;
import model.LibModel;
import vo.Lib;
import http.LibHttp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginScreen extends JPanel {
    private RoundedField fieldId;
    private RoundedField fieldPw;
    private RoundedButton loginButton;

    public LoginScreen(CardLayout cardLayout, JPanel container) {
        setLayout(new BorderLayout());

        // 화면을 좌우로 나누는 스플릿 패널
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createImagePanel()); // 왼쪽 이미지
        splitPane.setRightComponent(createRightLoginPanel(cardLayout, container)); // 오른쪽 로그인 UI
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);

        // 화면 크기 변경 시 50:50 분할 유지
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                splitPane.setDividerLocation(width / 2);
            }
        });

        add(splitPane, BorderLayout.CENTER);
    }

    // 왼쪽 이미지 영역
    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        ImageIcon icon = new ImageIcon("src/assets/login_image.jpg");
        Image img = icon.getImage().getScaledInstance(1000, 1500, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(img));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // 오른쪽 로그인 UI 영역
    private JPanel createRightLoginPanel(CardLayout cardLayout, JPanel container) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        Dimension wideSize = new Dimension(500, 40);

        JLabel label1 = new JLabel("LMS와 함께하는 똑똑한 도서 생활");
        label1.setFont(new Font("SansSerif", Font.BOLD, 30));

        JLabel label2 = new JLabel("지금 시작하세요");
        label2.setFont(new Font("SansSerif", Font.BOLD, 22));

        fieldId = new RoundedField("👤", "아이디");
        fieldId.setMaximumSize(wideSize);

        RoundedPasswordField fieldPw = new RoundedPasswordField("🔒", "비밀번호");
        fieldPw.setMaximumSize(wideSize);

        loginButton = new RoundedButton("로그인");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setMaximumSize(wideSize);
        loginButton.setPreferredSize(wideSize);
        loginButton.setMinimumSize(wideSize);
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ✅ 로그인 버튼 이벤트 처리
        loginButton.addActionListener(e -> {
            String libId = fieldId.getText().trim();
            String libPw = fieldPw.getText().trim();

            if (libId.isEmpty() || libPw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 모두 입력해주세요.");
                return;
            }

            try {
                Lib lib = LibHttp.login(libId, libPw);
                if (lib != null && lib.getLibId() != null) {
                    LibModel.getInstance().setLoggedInLib(lib);

                    // ✅ MenuScreen을 새로 생성해서 container에 등록
                    MenuScreen menuScreen = new MenuScreen(cardLayout, container);
                    container.add(menuScreen, "MenuScreen");

                    // ✅ 새로 생성한 MenuScreen으로 화면 전환
                    cardLayout.show(container, "MenuScreen");
                } else {
                    JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 잘못되었습니다.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "로그인 중 오류 발생: " + ex.getMessage());
            }

        });

        JLabel label3 = new JLabel("<html>아직 가입하신 적이 없으신가요? <b><u>회원가입</u></b></html>");
        label3.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label3.setForeground(Color.GRAY);
        label3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(container, "JoinScreen");
            }
        });

        // 요소 배치
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(label1);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(label2);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(fieldId);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(fieldPw);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(loginButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(label3);

        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldId.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPw.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        label3.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(centerPanel);

        return rightPanel;
    }
}