package draw;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class DrawPolygon extends JFrame {

	public int width = 100; 
	public int rate = 8;
	public DrawPolygon() {
		setSize(1366,768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//setBackground(Color.white);
	}
	
	public void drawFeelLeft(int x,int y,Graphics g) {
		int rate = 8;
		int x1 = x;
		int y1 = y;
		int x2 = x+width;
		int y2 = y+width/rate;
		int x3 = x+width;
		int y3 = y+width-width/rate;
		int x4 = x;
		int y4 = y+width;
		int x5 = x;
		int y5 = y;
		int x_array[] = {x1,x2,x3,x4,x5};
		int y_array[] = {y1,y2,y3,y4,y5};
		int p = y_array.length;
		g.setColor(Color.blue);
		g.fillPolygon(x_array, y_array, p);
		g.setColor(Color.BLACK);
		g.drawPolygon(x_array, y_array, p);
	}

	public void drawFlipleft(int x, int y,Graphics g) {
		g.setColor(Color.BLUE);
		int bias = width/2;
		int x_rect = x+bias;
		g.fillArc(x, y, 100, 100, 90, 180);
		g.fillRect(x_rect, y, width/2, width);
		g.setColor(Color.BLACK);
		g.drawArc(x, y, 100, 100, 90, 180);
		g.drawLine(x_rect, y, x_rect+bias, y);
		g.drawLine(x_rect+bias, y, x_rect+bias, y+width);
		g.drawLine(x_rect, y+width, x_rect+bias, y+width);
		
	}
	
	public void drawFeelright(int x,int y,Graphics g) {
		int x1 = x;
		int y1 = y+width/rate;
		int x2 = x+width;
		int y2 = y;
		int x3 = x+width;
		int y3 = y+width;
		int x4 = x;
		int y4 = y+width-width/rate;
		int x5 = x;
		int y5 = y+width/rate;
		int x_array[] = {x1,x2,x3,x4,x5};
		int y_array[] = {y1,y2,y3,y4,y5};
		int p = y_array.length;
		g.setColor(Color.blue);
		g.fillPolygon(x_array, y_array, p);
		//g.setColor(Color.BLACK);
		g.setColor(Color.BLACK);
		g.drawPolygon(x_array, y_array, p);
	}

	public void drawFlipRight(int x, int y,Graphics g) {
		g.setColor(Color.BLUE);
		int bias = width/2;
		int x_rect = x-bias;
		g.fillRect(x, y, width/2, width);
		g.fillArc(x, y, 100, 100, -90, 180);
		g.setColor(Color.BLACK);
		g.drawArc(x, y, 100, 100, -90, 180);
		g.drawLine(x, y, x+bias, y);
		g.drawLine(x, y, x, y+width);
		g.drawLine(x, y+width, x+bias, y+width);
	}
	
	public void drawFeelBoth(int x, int y, Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, width, width);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, width);
	}
	
	public void drawPointRect(int x, int y, Graphics g) {
		int x1 = x;
		int y1 = y+width/2;
		int x2 = x+width/2;
		int y2 = y+width/4;
		int x3 = x+width;
		int y3 = y+width/2;
		int x_array[] = {x1,x2,x3,x1};
		int y_array[] = {y1,y2,y3,y1};
		int p = y_array.length;
		g.setColor(Color.blue);
		g.fillPolygon(x_array, y_array, p);
		g.fillRect(x1, y1, width, width/4);
		g.setColor(Color.BLACK);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y2, x3, y3);
		g.drawLine(x1, y1, x1, y1+width/4);
		g.drawLine(x1, y+width*3/4, x+width, y+width*3/4);
		g.drawLine(x+width, y+width/2, x+width, y+width*3/4);
	}
	
	public void paint(Graphics g) {
		//BufferedImage targetImg = new BufferedImage(150000, 60, BufferedImage.TYPE_INT_ARGB);//获得一个image对象
		//g = targetImg.createGraphics();//获得一个图形类
		//drawFeelLeft(200,100, g);
		//drawFlipleft(300,200,g);
		//drawFeelright(400,300,g);
		//drawFlipRight(500,400,g);
		//drawFeelBoth(600,500,g);
		drawPointRect(600,500,g);
		//g.dispose();
		//try {
		//	ImageIO.write(targetImg, "PNG", new File("D:\\\\1.PNG"));
		//} catch (IOException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		System.out.println("get the informations from the system");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DrawPolygon drawPolygon = new DrawPolygon();
	}
	
	

}
