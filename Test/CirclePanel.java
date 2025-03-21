import javax.swing.*;
import java.awt.*;

public class CirclePanel extends JPanel {
    public CirclePanel() {
        setOpaque(false); // ทำให้พื้นหลังโปร่งใส
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLUE); // กำหนดสี
        g2d.fillOval(0, 0, getWidth(), getHeight()); // วาดวงกลมเต็มพื้นที่
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200); // ขนาดเริ่มต้น
    }
}
