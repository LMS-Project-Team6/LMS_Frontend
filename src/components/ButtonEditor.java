// ButtonEditor.java
package components;

import http.BookHttp;
import screens.BookInfoScreen;
import vo.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;
    private CardLayout cardLayout;
    private JPanel container;

    public ButtonEditor(JCheckBox checkBox, JTable table, CardLayout cardLayout, JPanel container) {
        super(checkBox);
        this.table = table;
        this.cardLayout = cardLayout;
        this.container = container;

        button = new JButton();
        button.setOpaque(true);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));       // ✅ 글씨 크기만 조정
        button.setPreferredSize(new Dimension(55, 30));             // ✅ 크기만 유지

        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }

        button.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            String bookId = table.getValueAt(row, 0).toString(); // 0번째 열이 bookId

            try {
                Book book = BookHttp.getBookDetails(bookId);

                // 새 화면 생성 전 기존 BookInfoScreen 제거
                for (Component comp : container.getComponents()) {
                    if (comp instanceof BookInfoScreen) {
                        container.remove(comp);
                        break;
                    }
                }

                BookInfoScreen infoScreen = new BookInfoScreen(cardLayout, container, book);
                container.add(infoScreen, "BookInfoScreen");
                cardLayout.show(container, "BookInfoScreen");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(container, "도서 정보를 불러올 수 없습니다.");
            }
        });

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        fireEditingStopped();
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
