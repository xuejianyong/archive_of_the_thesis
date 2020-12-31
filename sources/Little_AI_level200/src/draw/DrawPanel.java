package draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class DrawPanel extends JPanel{

	private static final long serialVersionUID = -3110685580910865929L;
	
	@Override
    public void paint(Graphics g_t) {
        // TODO 自动生成的方法存根
        super.paint(g_t);
        //使用Graphics2D 来完成画图   Graphics 无法设置画图线条的粗细
        Graphics2D g2_t = (Graphics2D) g_t;
        
        Shape[]  shapes = new Shape[7];
        //画圆
        shapes[0] = new Ellipse2D.Double(5, 5, 100, 100);
        //画矩形
        shapes[1] = new Rectangle2D.Double(110,5,100,100);
        shapes[2] = new Rectangle2D.Double(15,15,80,80);
        shapes[3] = new Ellipse2D.Double(120, 15, 80, 80);
        //画弧度
        shapes[4] = new Arc2D.Double(215, 5, 100, 100, 0, 180, 2);
        //画圆弧的圆
        shapes[5] = new RoundRectangle2D.Double(320, 15, 100, 100, 30, 30);
        //画直线
        shapes[6] = new Line2D.Double(5, 120, 105, 120);
       
        for (Shape shape : shapes) {
            Rectangle2D bounds = shape.getBounds2D();
            if (bounds.getWidth() == 80) {
                g2_t.fill(shape);
            }
            else
                g2_t.draw(shape);
        }
        //画个多边形
        //多边行
        g2_t.setColor(Color.red);
        int[] x1 = {100,120,180,140,150,100,50,60,20,80};
        int[] y1 = {170,235,240,270,330,290,330,270,240,235};
        g2_t.fillPolygon(x1, y1, 10);   
    }


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DrawPanel paneadfasdfl = new DrawPanel();

	}

}
