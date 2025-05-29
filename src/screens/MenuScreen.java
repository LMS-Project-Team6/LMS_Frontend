package screens;

import components.GoBackButtonFactory;
import components.RoundedButton;

import javax.swing.*;
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
        JButton goBackButton = GoBackButtonFactory.createGoBackButton();
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
        textLabelPanel.add(Box.createVerticalStrut(10));
        textLabelPanel.add(label2);
        textLabelPanel.add(Box.createVerticalStrut(10));
        textLabelPanel.add(separatorPanel);
        LabelPanel.add(textLabelPanel, BorderLayout.SOUTH);

        return LabelPanel;
    }

    private JPanel MenuPart(CardLayout cardLayout, JPanel container) {

        // 버튼 사이즈 선언
        Dimension size = new Dimension(500, 40);

        // 1. 전체 프레임
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setOpaque(false);

        // 1-2. 안에 담길 프레임
        JPanel basketPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        basketPanel.setOpaque(false);

        // 2-1. 버튼 레이어1
        JPanel layerPanel1 = new JPanel();
        layerPanel1.setLayout(new BoxLayout(layerPanel1, BoxLayout.Y_AXIS));
        layerPanel1.setOpaque(false);
        layerPanel1.setBorder(BorderFactory.createLineBorder(Color.RED)); // 빨간 테두리 temp

        // 2-1-1. 버튼 레이어1의 버튼1
        JButton button1_1 = new RoundedButton("도서 대출");
        button1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
        button1_1.setMaximumSize(size);
        button1_1.setPreferredSize(size);
        button1_1.setMinimumSize(size);
        button1_1.setAlignmentX(Component.LEFT_ALIGNMENT);
        layerPanel1.add(button1_1);

        // 2-1-2. 버튼 레이어1의 버튼2
        JButton button1_2 = new RoundedButton("도서 반납");
        button1_2.setFont(new Font("SansSerif", Font.BOLD, 16));
        button1_2.setMaximumSize(size);
        button1_2.setPreferredSize(size);
        button1_2.setMinimumSize(size);
        button1_2.setAlignmentX(Component.LEFT_ALIGNMENT);
        layerPanel1.add(button1_2);

        // 2-1-3. 버튼 레이어1의 버튼3
        JButton button1_3 = new RoundedButton("도서 대출 관리");
        button1_3.setFont(new Font("SansSerif", Font.BOLD, 16));
        button1_3.setMaximumSize(size);
        button1_3.setPreferredSize(size);
        button1_3.setMinimumSize(size);
        button1_3.setAlignmentX(Component.LEFT_ALIGNMENT);
        layerPanel1.add(button1_3);

        // 2-1-4. 버튼 레이어1의 버튼4
        JButton button1_4 = new RoundedButton("도서 반납 관리");
        button1_4.setFont(new Font("SansSerif", Font.BOLD, 16));
        button1_4.setMaximumSize(size);
        button1_4.setPreferredSize(size);
        button1_4.setMinimumSize(size);
        button1_4.setAlignmentX(Component.LEFT_ALIGNMENT);
        layerPanel1.add(button1_4);

        // 2-1-5. 버튼 레이어1의 버튼5
        JButton button1_5 = new RoundedButton("도서 관리");
        button1_5.setFont(new Font("SansSerif", Font.BOLD, 16));
        button1_5.setMaximumSize(size);
        button1_5.setPreferredSize(size);
        button1_5.setMinimumSize(size);
        button1_5.setAlignmentX(Component.LEFT_ALIGNMENT);
        layerPanel1.add(button1_5);

        // 2-2. 버튼 레이어2
        JPanel layerPanel2 = new JPanel();
        layerPanel2.setLayout(new BoxLayout(layerPanel2, BoxLayout.Y_AXIS));
        layerPanel2.setOpaque(false);
        layerPanel1.setBorder(BorderFactory.createLineBorder(Color.RED)); // 빨간 테두리 temp

        // 2-2-1 버튼 레이어2의 버튼1
        JButton button2_1 = new RoundedButton("도서 관리");
        button2_1.setFont(new Font("SansSerif", Font.BOLD, 16));
        button2_1.setMaximumSize(size);
        button2_1.setPreferredSize(size);
        button2_1.setMinimumSize(size);
        button2_1.setAlignmentX(Component.LEFT_ALIGNMENT);
        layerPanel1.add(button2_1);

        // 2-3. 버튼 레이어3
        JPanel layerPanel3 = new JPanel();
        layerPanel3.setLayout(new BoxLayout(layerPanel3, BoxLayout.Y_AXIS));
        layerPanel3.setOpaque(false);
        layerPanel1.setBorder(BorderFactory.createLineBorder(Color.RED)); // 빨간 테두리 temp

        // 2-3-1 버튼 레이어3의 버튼1
        JButton button3_1 = new RoundedButton("도서 관리");
        button3_1.setFont(new Font("SansSerif", Font.BOLD, 16));
        button3_1.setMaximumSize(size);
        button3_1.setPreferredSize(size);
        button3_1.setMinimumSize(size);
        button3_1.setAlignmentX(Component.LEFT_ALIGNMENT);
        layerPanel1.add(button3_1);

        // 3. 1-2와 2번들 합체
        basketPanel.add(layerPanel1);
        basketPanel.add(layerPanel2);
        basketPanel.add(layerPanel3);

        // 4. 1-2와 1 합체
        menuPanel.add(basketPanel);

        return menuPanel;
    }
}

