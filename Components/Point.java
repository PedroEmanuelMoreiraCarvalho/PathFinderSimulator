package Components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Application.App;

public class Point {
	
	private int x,y;
	private boolean selected;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		this.selected = false;
	}
	
	public void drawStreetTo(Graphics g, Point point2, int gap_x, int gap_y, float zoom) {
		g.setColor(Color.gray);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5.0F));
		g2d.drawLine(((int)((zoom + 1) * x)) - App.off_set_render_x + gap_x + 105,
					((int)((zoom + 1) * y)) - App.off_set_render_y + gap_y + 6,
					((int)((zoom + 1) * point2.getX())) - App.off_set_render_x + gap_x + 105, 
					((int)((zoom + 1) * point2.getY())) - App.off_set_render_y + gap_y + 6);
	}
	
	public void drawPathTo(Graphics g, Point point2, int gap_x, int gap_y, float zoom) {
		g.setColor(Color.red);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5.0F));
		g2d.drawLine(((int)((zoom + 1) * x)) - App.off_set_render_x + gap_x + 105,
					((int)((zoom + 1) * y)) - App.off_set_render_y + gap_y + 6,
					((int)((zoom + 1) * point2.getX())) - App.off_set_render_x + gap_x + 105, 
					((int)((zoom + 1) * point2.getY())) - App.off_set_render_y + gap_y + 6);
	}
	
	public void render(Graphics g, int gap_x, int gap_y, float zoom) {
		if(selected) g.setColor(Color.red);
		else g.setColor(Color.black);
		g.fillOval(((int)((zoom + 1) * x)) - App.off_set_render_x + gap_x, ((int)((zoom + 1) * y)) - App.off_set_render_y + gap_y, 12, 12);
		g.setColor(Color.white);
		g.fillOval(((int)((zoom + 1) * x)) + 2 - App.off_set_render_x + gap_x, ((int)((zoom + 1) * y)) + 2 - App.off_set_render_y + gap_y, 8, 8);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void select() {
		selected = true;
	}

	public void deselect() {
		selected = false;
	}
	
}
