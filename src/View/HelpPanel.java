package View;
import Controller.MenuController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class HelpPanel extends View implements ActionListener {
    private JScrollPane helpScrollPane;
    private JTextArea helpTextArea;
    private JButton backButton;

    public HelpPanel() {
        helpScrollPane = new JScrollPane();
        helpTextArea = new JTextArea();
        backButton = new JButton();
        backButton.addActionListener(this);
        helpTextArea.setColumns(20);
        helpTextArea.setFont(new java.awt.Font("Arial", 0, 18));
        helpTextArea.setRows(5);
        helpTextArea.setFocusable(false);
        helpScrollPane.setViewportView(helpTextArea);
        helpTextArea.setText("\t       Ứng dụng luyện nghe Tiếng Anh :)\n");
        helpTextArea.append("Hệ thống có 3 cấp độ nghe: 01, 02, 03:\n");
        helpTextArea.append("*Level 01: \n");
        helpTextArea.append("      - Gợi ý cho người dùng khi phải nhập tên riêng, địa danh.\n");
        helpTextArea.append("*Level 02: \n");
        helpTextArea.append("      - Người dùng phải nhập tên riêng mà không có gợi ý.\n");
        helpTextArea.append("      - Thời lượng của phần nghe ít hơn hoặc bằng 15 giây.\n");
        helpTextArea.append("*Level 03: \n");
        helpTextArea.append("      - Người dùng phải nhập tên riêng mà không có gợi ý.\n");
        helpTextArea.append("      - Thời lượng của phần nghe dài hơn 20 giây.\n");
        helpTextArea.append("    Khi người nghe  chọn được bài nghe. Chương trình sẽ phát lần lượt từng phần nghe một cho người nghe. Người nghe sau khi hoàn thành phần nghe thứ N thì mới được nghe tiếp phần nghe thứ (N+1).\n"
                                +"    Khi phát phần nghe thứ N, người dùng phải nhập lần lượt từng từ một của phần nghe. Chương trình sẽ kiểm tra xem với phím mà người dùng vừa nhấn, có phải sẽ tạo thành một từ hợp lệ hay không? Nếu đúng thì sẽ xóa từ đó đi và cho phép người dùng nhập từ mới."
                                +" Nếu không thì chương trình sẽ xóa đi chữ cái mà người dùng vừa nhấn.\n");
        helpTextArea.append("    Chương trình sẽ chỉ tính điểm theo công thức sau: 10* (11 – x/y). Trong đó x là tổng thời gian người dùng từ lúc bắt đầu nghe đến lúc kết thúc. Còn y là tổng thời lượng của bài nghe.");
        helpTextArea.setLineWrap(true);
        helpTextArea.setWrapStyleWord(true);
        backButton.setText("Back");
        backButton.setFont(new java.awt.Font("Arial", 0, 14));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(helpScrollPane, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE)
                        .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup().addContainerGap()
                                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(helpScrollPane,
                                        GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(60, Short.MAX_VALUE)));
    }

    public JButton getBackButton() {
        return backButton;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        this.setVisible(false);
        MainFrame.getInstance().add(MenuController.getInstance().getView());
    }

}