package components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setFont(new Font("SansSerif", Font.BOLD, 14));       // ✅ 글씨 크기만 조정
        setPreferredSize(new Dimension(55, 30));             // ✅ 크기만 유지
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        setText((value == null) ? "" : value.toString());

        // ✅ 배경색 일치 처리
        if (isSelected) {
            setBackground(new Color(220, 240, 255)); // 선택 시 배경
        } else {
            setBackground(Color.WHITE); // 평상시 배경
        }

        return this;
    }
}
