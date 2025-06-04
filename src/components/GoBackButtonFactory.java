package components;

import javax.swing.*;
import java.awt.*;

public class GoBackButtonFactory {
    public static JButton createGoBackButton(boolean isWhite) {
        String imagePath = "src/assets/arrow_back_icon.png";
        if (isWhite) {
            imagePath = "src/assets/arrow_back_icon_white.png";
        }
        ImageIcon originalIcon = new ImageIcon(imagePath); // 원본 이미지 아이콘 생성

        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // 원하는 크기로 조정 (30x30으로 예시)
        ImageIcon icon = new ImageIcon(scaledImage); // 축소된 이미지 아이콘 생성

        // JButton에 JLabel 담기
        JButton goBackButton = new JButton(icon);
        goBackButton.setContentAreaFilled(false); // 배경 제거
        goBackButton.setBorderPainted(false); // 테두리 제거
        goBackButton.setFocusPainted(false); // 포커스 테두리 제거
        goBackButton.setOpaque(false); // 투명 처리

        return goBackButton;
    }
}
