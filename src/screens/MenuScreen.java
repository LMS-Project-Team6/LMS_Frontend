package screens;

import model.LibModel;
import vo.Lib;

import javax.swing.*;
import java.awt.*;

public class MenuScreen extends JPanel {
    public MenuScreen() {
        setLayout(new BorderLayout());

        Lib lib = LibModel.getInstance().getLoggedInLib();

        String welcomeText = "<html><div style='text-align:center;'>"
                + "<h2>" + lib.getLibName() + "님 환영합니다!</h2>"
                + "<p>이메일: " + lib.getLibEmail() + "</p>"
                + "<p>생년월일: " + lib.getLibBirth() + "</p>"
                + "</div></html>";

        JLabel infoLabel = new JLabel(welcomeText, SwingConstants.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        add(infoLabel, BorderLayout.CENTER);
    }
}

