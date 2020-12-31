package draw;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
public class JFrame001 extends JFrame {
    private static final long serialVersionUID = -8298152118685661613L;
    private JPanel contentPane;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame001 frame = new JFrame001();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Create the frame.
     */
    public JFrame001() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override public void paint(Graphics g) {
                super.paint(g);
                // g绘制圆弧
                g.drawArc(10, 10, 100, 50, 270, 200);
                // g绘制线段
                g.drawLine(10, 10, 50, 50);
                // g绘制圆形
                g.drawOval(10, 80, 50, 30);
                // g绘制矩形
                g.setColor(Color.RED);
                g.drawRect(80, 80, 100, 50);
                // g绘制字符串
                g.drawString("hello", 190, 80);
                Graphics2D g2 = (Graphics2D) g;
                // g2绘制Ellipse2D
                Shape s01 = new Ellipse2D.Float(50, 110, 20, 20);
                g2.draw(s01);
                // g2绘制Line2D
                Shape s02 = new Line2D.Float(10, 150, 50, 150);
                g2.setColor(Color.BLACK);
                Stroke stroke = new BasicStroke(5);
                g2.setStroke(stroke);
                g2.draw(s02);
                // g2绘制Line2D, 使用BasicStroke
                Shape s03 = new Line2D.Float(10, 180, 150, 180);
                g2.setColor(Color.BLACK);
                Stroke stroke02 = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] { 20, 5 }, 10);
                g2.setStroke(stroke02);
                g2.draw(s03);
                // g2绘制Arc2D
                Stroke stroke03 = new BasicStroke(1);
                g2.setStroke(stroke03);
                Shape s04 = new Arc2D.Float(10, 200, 40, 40, 0, 90, Arc2D.OPEN);
                g2.draw(s04);
                s04 = new Arc2D.Float(60, 200, 40, 40, -30, 90, Arc2D.CHORD);
                g2.draw(s04);
                s04 = new Arc2D.Float(110, 200, 40, 40, 0, 90, Arc2D.PIE);
                g2.draw(s04);
                // g2不能绘制Point2D
                // new Point2D.Float(10,210);
                // g2绘制Rectangle2D
                Shape s05 = new Rectangle2D.Float(10, 250, 130, 30);
                g2.draw(s05);
                // g2绘制CubicCurve2D
                Shape s06 = new CubicCurve2D.Float(10, 310, 35, 280, 85, 340, 100, 310);
                g2.draw(s06);
                s06 = new Line2D.Float(10, 310, 35, 280);
                g2.draw(s06);
                s06 = new Line2D.Float(35, 280, 85, 340);
                g2.draw(s06);
                s06 = new Line2D.Float(85, 340, 100, 310);
                g2.draw(s06);
                // g2绘制QuadCurve2D
                Shape s07 = new QuadCurve2D.Float(10, 350, 55, 370, 100, 350);
                g2.draw(s07);
                s07 = new Line2D.Float(10, 350, 55, 370);
                g2.draw(s07);
                s07 = new Line2D.Float(55, 370, 100, 350);
                g2.draw(s07);
                // 设置Font, drawString;
                Font font = new Font("黑体", Font.BOLD, 16);
                g2.setFont(font);
                g2.drawString("中文字符串", 10, 390);
            }
        };
        panel.setPreferredSize(new Dimension(2000, 1000));
        scrollPane.setViewportView(panel);
        JPanel panelColumn = new JPanel();
        scrollPane.setColumnHeaderView(panelColumn);
        JLabel lblNewLabel = new JLabel("New label");
        panelColumn.add(lblNewLabel);;
        JPanel panelRow = new JPanel();
        scrollPane.setRowHeaderView(panelRow);
        JLabel lblNewLabel_1 = new JLabel("New label");
        panelRow.add(lblNewLabel_1);
    }
}