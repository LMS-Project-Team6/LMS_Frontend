package screens;

import components.GoBackButtonFactory;
import components.RoundedButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MenuScreen extends JPanel {
    public MenuScreen(CardLayout cardLayout, JPanel container) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); // 제약사항 표현

        // 1-1. LabelPart 조건
        gbc.gridx = 0;        // 열 인덱스
        gbc.gridy = 0;        // 행 인덱스
        gbc.gridwidth = 1;    // 가로로 몇 칸 차지할지
        gbc.gridheight = 1;   // 세로로 몇 칸 차지할지
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;  // 셀을 채우는 방식
        gbc.insets = new Insets(5, 5, 5, 5); // 여백 (top, left, bottom, right)

        // 1-2. LabelPart 생성
        add(LabelPart(cardLayout, container), gbc);

        // 2-1. MenuPart 조건
        gbc.gridx = 0;        // 열 인덱스
        gbc.gridy = 1;        // 행 인덱스
        gbc.gridwidth = 1;    // 가로로 몇 칸 차지할지
        gbc.gridheight = 1;   // 세로로 몇 칸 차지할지
        gbc.weightx = 1.0;
        gbc.weighty = 10.0; // LabelPart를 최소로 만들기 위함. 실제로 11분등의 10이 아님
        gbc.fill = GridBagConstraints.BOTH;  // 셀을 채우는 방식
        gbc.insets = new Insets(5, 5, 5, 5); // 여백 (top, left, bottom, right)

        // 2-2. MenuPart 생성
        add(MenuPart(cardLayout, container), gbc);
    }

    private JPanel LabelPart(CardLayout cardLayout, JPanel container) {
        // 1. 전체 프레임
        JPanel LabelPanel = new JPanel(new BorderLayout());
        LabelPanel.setOpaque(false);

        // 2-1. 뒤로 가기 프레임
        JPanel goBackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        goBackPanel.setOpaque(false);
        JButton goBackButton = GoBackButtonFactory.createGoBackButton(false);
        goBackPanel.add(goBackButton);
        LabelPanel.add(goBackPanel, BorderLayout.NORTH);

        // 2-2. 뒤로 가기 리스너
        goBackButton.addActionListener(e -> {
            cardLayout.show(container, "LoginScreen");
            System.out.println("버튼 클릭됨 - LoginScreen으로 이동");
        });

        // 3. 텍스트 프레임
        JPanel textLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textLabelPanel.setLayout(new BoxLayout(textLabelPanel, BoxLayout.Y_AXIS));
        textLabelPanel.setOpaque(false);

        JLabel label1 = new JLabel("전체 메뉴");
        label1.setFont(new Font("SansSerif", Font.BOLD, 47));
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label2 = new JLabel("도서관리시스템(LMS)의 전체 메뉴입니다");
        label2.setFont(new Font("SansSerif", Font.PLAIN, 22));
        label2.setForeground(Color.GRAY);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel separatorPanel = new JPanel(new BorderLayout());
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Adjust width and thickness
        separatorPanel.setOpaque(false);
        separatorPanel.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 70)); // 좌우 여백만 주기
        separatorPanel.add(separator);

        textLabelPanel.add(label1);
        textLabelPanel.add(Box.createVerticalStrut(5));
        textLabelPanel.add(label2);
        textLabelPanel.add(Box.createVerticalStrut(10));
        textLabelPanel.add(separatorPanel);
        LabelPanel.add(textLabelPanel, BorderLayout.SOUTH);

        return LabelPanel;
    }

    private JPanel MenuPart(CardLayout cardLayout, JPanel container) {

        // 1. 전체 프레임
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setOpaque(false);

        // 1-2. 안에 담길 프레임
        JPanel basketPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 40));
        basketPanel.setOpaque(false);

        // 2-1. 버튼 레이어1
        JPanel layerPanel1 = new JPanel();
        layerPanel1.setLayout(new BoxLayout(layerPanel1, BoxLayout.Y_AXIS));
        layerPanel1.setOpaque(false);

        // 버튼 사이즈 선언
        Dimension size = new Dimension(300, 70);

        // 버튼 레이어1의 라벨1
        JLabel label1 = new JLabel("BOOK");
        label1.setFont(new Font("SansSerif", Font.BOLD, 38));
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        layerPanel1.add(label1);
        layerPanel1.add(Box.createVerticalStrut(20));

        // 버튼 레이어1의 버튼1
        RoundedButton button1_1 = new RoundedButton("\uD83D\uDCD6\n 도서 대출");
        button1_1.setHorizontalAlignment(SwingConstants.LEFT);
        button1_1.setFont(new Font("SansSerif", Font.BOLD, 25));
        button1_1.setMaximumSize(size);
        button1_1.setPreferredSize(size);
        button1_1.setMinimumSize(size);
        button1_1.setAlignmentX(Component.LEFT_ALIGNMENT);
        button1_1.useShadow();
        button1_1.changeLeft();
        button1_1.setTextColor(Color.BLACK);
        button1_1.setBorderColor(Color.GRAY);
        button1_1.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel1.add(button1_1);
        layerPanel1.add(Box.createVerticalStrut(20));

        button1_1.addActionListener(e -> {
            // 기존 화면 제거
            for (Component comp : container.getComponents()) {
                if (comp instanceof BookLendingScreen1) {
                    container.remove(comp);
                    break;
                }
            }

            // 새로 생성 및 추가
            JPanel screen = new BookLendingScreen1(cardLayout, container);
            container.add(screen, "BookLendingScreen1");

            // 화면 전환
            cardLayout.show(container, "BookLendingScreen1");
            System.out.println("🔄 BookLendingScreen1으로 이동 (다시 생성)");
        });

        // 버튼 레이어1의 버튼2
        RoundedButton button1_2 = new RoundedButton("\uD83D\uDCDA\n 도서 반납");
        button1_2.setFont(new Font("SansSerif", Font.BOLD, 25));
        button1_2.setMaximumSize(size);
        button1_2.setPreferredSize(size);
        button1_2.setMinimumSize(size);
        button1_2.setAlignmentX(Component.LEFT_ALIGNMENT);
        button1_2.useShadow();
        button1_2.changeLeft();
        button1_2.setTextColor(Color.BLACK);
        button1_2.setBorderColor(Color.GRAY);
        button1_2.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel1.add(button1_2);
        layerPanel1.add(Box.createVerticalStrut(20));

        button1_2.addActionListener(e -> {
            // 기존 화면 제거
            for (Component comp : container.getComponents()) {
                if (comp instanceof BookLendingScreen1) {
                    container.remove(comp);
                    break;
                }
            }

            // 새로 생성 및 추가
            JPanel screen = new BookReturnScreen(cardLayout, container);
            container.add(screen, "BookReturnScreen");

            // 화면 전환
            cardLayout.show(container, "BookReturnScreen");
            System.out.println("🔄 BookReturnScreen으로 이동 (다시 생성)");
        });

        // 버튼 레이어1의 버튼3
        RoundedButton button1_3 = new RoundedButton("\uD83D\uDED2\n 도서 대출 관리");
        button1_3.setFont(new Font("SansSerif", Font.BOLD, 25));
        button1_3.setMaximumSize(size);
        button1_3.setPreferredSize(size);
        button1_3.setMinimumSize(size);
        button1_3.setAlignmentX(Component.LEFT_ALIGNMENT);
        button1_3.useShadow();
        button1_3.changeLeft();
        button1_3.setTextColor(Color.BLACK);
        button1_3.setBorderColor(Color.GRAY);
        button1_3.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel1.add(button1_3);
        layerPanel1.add(Box.createVerticalStrut(20));
        button1_3.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "해당 서비스는 현재 준비 중입니다.", "서비스 준비 중", JOptionPane.INFORMATION_MESSAGE);
        });

        // 버튼 레이어1의 버튼4
        RoundedButton button1_4 = new RoundedButton("\uD83D\uDCE5\n 도서 반납 관리");
        button1_4.setFont(new Font("SansSerif", Font.BOLD, 25));
        button1_4.setMaximumSize(size);
        button1_4.setPreferredSize(size);
        button1_4.setMinimumSize(size);
        button1_4.setAlignmentX(Component.LEFT_ALIGNMENT);
        button1_4.useShadow();
        button1_4.changeLeft();
        button1_4.setTextColor(Color.BLACK);
        button1_4.setBorderColor(Color.GRAY);
        button1_4.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel1.add(button1_4);
        layerPanel1.add(Box.createVerticalStrut(20));
        button1_4.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "해당 서비스는 현재 준비 중입니다.", "서비스 준비 중", JOptionPane.INFORMATION_MESSAGE);
        });

        // 버튼 레이어1의 버튼5
        RoundedButton button1_5 = new RoundedButton("\uD83D\uDD0D  도서 관리");
        button1_5.setFont(new Font("SansSerif", Font.BOLD, 25));
        button1_5.setMaximumSize(size);
        button1_5.setPreferredSize(size);
        button1_5.setMinimumSize(size);
        button1_5.setAlignmentX(Component.LEFT_ALIGNMENT);
        button1_5.useShadow();
        button1_5.changeLeft();
        button1_5.setTextColor(Color.BLACK);
        button1_5.setBorderColor(Color.GRAY);
        button1_5.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel1.add(button1_5);
        layerPanel1.add(Box.createVerticalStrut(20));

        button1_5.addActionListener(e -> {
            // 기존 화면 제거
            for (Component comp : container.getComponents()) {
                if (comp instanceof BookLendingScreen1) {
                    container.remove(comp);
                    break;
                }
            }

            // 새로 생성 및 추가
            JPanel screen = new BookMngScreen(cardLayout, container);
            container.add(screen, "BookMngScreen");

            // 화면 전환
            cardLayout.show(container, "BookMngScreen");
            System.out.println("🔄 BookMngScreen으로 이동 (다시 생성)");
        });

        // 2-2. 버튼 레이어2
        JPanel layerPanel2 = new JPanel();
        layerPanel2.setLayout(new BoxLayout(layerPanel2, BoxLayout.Y_AXIS));
        layerPanel2.setOpaque(false);

        // 버튼 사이즈 선언
        size = new Dimension(400, 70);

        // 버튼 레이어2의 라벨1
        JLabel label2 = new JLabel("GROUP STUDY");
        label2.setFont(new Font("SansSerif", Font.BOLD, 38));
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        layerPanel2.add(label2);
        layerPanel2.add(Box.createVerticalStrut(20));

        // 버튼 레이어2의 버튼1
        RoundedButton button2_1 = new RoundedButton("\uD83D\uDCCC\n 그룹 스터디룸 예약 관리");
        button2_1.setFont(new Font("SansSerif", Font.BOLD, 25));
        button2_1.setMaximumSize(size);
        button2_1.setPreferredSize(size);
        button2_1.setMinimumSize(size);
        button2_1.setAlignmentX(Component.LEFT_ALIGNMENT);
        button2_1.useShadow();
        button2_1.changeLeft();
        button2_1.setTextColor(Color.BLACK);
        button2_1.setBorderColor(Color.GRAY);
        button2_1.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel2.add(button2_1);
        layerPanel2.add(Box.createVerticalStrut(20));
        button2_1.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "해당 서비스는 현재 준비 중입니다.", "서비스 준비 중", JOptionPane.INFORMATION_MESSAGE);
        });

        // 버튼 레이어2의 버튼2
        RoundedButton button2_2 = new RoundedButton("\uD83C\uDFE0\n 그룹 스터디룸 관리");
        button2_2.setFont(new Font("SansSerif", Font.BOLD, 25));
        button2_2.setMaximumSize(size);
        button2_2.setPreferredSize(size);
        button2_2.setMinimumSize(size);
        button2_2.setAlignmentX(Component.LEFT_ALIGNMENT);
        button2_2.useShadow();
        button2_2.changeLeft();
        button2_2.setTextColor(Color.BLACK);
        button2_2.setBorderColor(Color.GRAY);
        button2_2.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel2.add(button2_2);
        layerPanel2.add(Box.createVerticalStrut(20));
        button2_2.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "해당 서비스는 현재 준비 중입니다.", "서비스 준비 중", JOptionPane.INFORMATION_MESSAGE);
        });

        // 버튼 레이어2의 버튼3
        RoundedButton button2_3 = new RoundedButton("\uD83D\uDD53\n 그룹 스터디룸 사용 이력 관리");
        button2_3.setFont(new Font("SansSerif", Font.BOLD, 25));
        button2_3.setMaximumSize(size);
        button2_3.setPreferredSize(size);
        button2_3.setMinimumSize(size);
        button2_3.setAlignmentX(Component.LEFT_ALIGNMENT);
        button2_3.useShadow();
        button2_3.changeLeft();
        button2_3.setTextColor(Color.BLACK);
        button2_3.setBorderColor(Color.GRAY);
        button2_3.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel2.add(button2_3);
        layerPanel2.add(Box.createVerticalStrut(200));
        button2_3.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "해당 서비스는 현재 준비 중입니다.", "서비스 준비 중", JOptionPane.INFORMATION_MESSAGE);
        });

        // 2-3. 버튼 레이어3
        JPanel layerPanel3 = new JPanel();
        layerPanel3.setLayout(new BoxLayout(layerPanel3, BoxLayout.Y_AXIS));
        layerPanel3.setOpaque(false);

        // 버튼 사이즈 선언
        size = new Dimension(300, 70);

        // 버튼 레이어3의 라벨1
        JLabel label3 = new JLabel("USER");
        label3.setFont(new Font("SansSerif", Font.BOLD, 38));
        label3.setAlignmentX(Component.LEFT_ALIGNMENT);
        layerPanel3.add(label3);
        layerPanel3.add(Box.createVerticalStrut(20));

        // 버튼 레이어3의 버튼1
        RoundedButton button3_1 = new RoundedButton("\uD83D\uDC65\n 회원 관리");
        button3_1.setFont(new Font("SansSerif", Font.BOLD, 25));
        button3_1.setMaximumSize(size);
        button3_1.setPreferredSize(size);
        button3_1.setMinimumSize(size);
        button3_1.setAlignmentX(Component.LEFT_ALIGNMENT);
        button3_1.useShadow();
        button3_1.changeLeft();
        button3_1.setTextColor(Color.BLACK);
        button3_1.setBorderColor(Color.GRAY);
        button3_1.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel3.add(button3_1);
        layerPanel3.add(Box.createVerticalStrut(20));
        button3_1.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "해당 서비스는 현재 준비 중입니다.", "서비스 준비 중", JOptionPane.INFORMATION_MESSAGE);
        });

        // 버튼 레이어3의 버튼2
        RoundedButton button3_2 = new RoundedButton("💳  사서 관리");
        button3_2.setFont(new Font("SansSerif", Font.BOLD, 25));
        button3_2.setMaximumSize(size);
        button3_2.setPreferredSize(size);
        button3_2.setMinimumSize(size);
        button3_2.setAlignmentX(Component.LEFT_ALIGNMENT);
        button3_2.useShadow();
        button3_2.changeLeft();
        button3_2.setTextColor(Color.BLACK);
        button3_2.setBorderColor(Color.GRAY);
        button3_2.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel3.add(button3_2);
        layerPanel3.add(Box.createVerticalStrut(50));
        button3_2.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "해당 서비스는 현재 준비 중입니다.", "서비스 준비 중", JOptionPane.INFORMATION_MESSAGE);
        });

        // 버튼 레이어3의 라벨1
        JLabel label4 = new JLabel("ETC");
        label4.setFont(new Font("SansSerif", Font.BOLD, 38));
        label4.setAlignmentX(Component.LEFT_ALIGNMENT);
        layerPanel3.add(label4);
        layerPanel3.add(Box.createVerticalStrut(20));

        // 버튼 레이어3의 버튼3
        RoundedButton button3_3 = new RoundedButton("\uD83D\uDCCA\n 분석");
        button3_3.setFont(new Font("SansSerif", Font.BOLD, 25));
        button3_3.setMaximumSize(size);
        button3_3.setPreferredSize(size);
        button3_3.setMinimumSize(size);
        button3_3.setAlignmentX(Component.LEFT_ALIGNMENT);
        button3_3.useShadow();
        button3_3.changeLeft();
        button3_3.setTextColor(Color.BLACK);
        button3_3.setBorderColor(Color.GRAY);
        button3_3.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel3.add(button3_3);
        layerPanel3.add(Box.createVerticalStrut(20));
        button3_3.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "해당 서비스는 현재 준비 중입니다.", "서비스 준비 중", JOptionPane.INFORMATION_MESSAGE);
        });

        // 버튼 레이어3의 버튼4
        RoundedButton button3_4 = new RoundedButton("\uD83D\uDCE2\n 공지사항");
        button3_4.setFont(new Font("SansSerif", Font.BOLD, 25));
        button3_4.setMaximumSize(size);
        button3_4.setPreferredSize(size);
        button3_4.setMinimumSize(size);
        button3_4.setAlignmentX(Component.LEFT_ALIGNMENT);
        button3_4.useShadow();
        button3_4.changeLeft();
        button3_4.setTextColor(Color.BLACK);
        button3_4.setBorderColor(Color.GRAY);
        button3_4.setNewColor(new Color(255, 255, 255), new Color(150, 150, 150));
        layerPanel3.add(button3_4);
        layerPanel3.add(Box.createVerticalStrut(20));
        button3_4.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "해당 서비스는 현재 준비 중입니다.", "서비스 준비 중", JOptionPane.INFORMATION_MESSAGE);
        });

        // 3. 1-2와 2번들 합체
        basketPanel.add(layerPanel1);
        basketPanel.add(layerPanel2);
        basketPanel.add(layerPanel3);

        // 4. 1-2와 1 합체
        menuPanel.add(basketPanel, BorderLayout.CENTER);

        return menuPanel;
    }
}

