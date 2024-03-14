package Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;

public class Canvas extends JComponent implements MouseListener, MouseWheelListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int height, width, gap_x = 0, gap_y = 0;
	private int mode = 0;
	private float zoom;
	private StreetGraph street_graph;
	
	public Canvas(int width, int height, StreetGraph street_graph) {
		this.width = width;
		this.height = height;
		this.gap_x = width/2;
		this.gap_y = height/2;
		this.street_graph = street_graph;
	}
	
	public void paint(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
		street_graph.render(g, gap_x, gap_y, zoom);
    }
	
	public void update() {
		this.repaint();
	}

	public int getScreenX(int coord) {
		return (int)((coord - gap_x)/(zoom + 1));
	}
	
	public int getScreenY(int coord) {
		return (int)((coord - gap_y)/(zoom + 1));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e){
		int key = e.getButton();
		if(key == MouseEvent.BUTTON1) {
			if(mode == 1) 
				street_graph.addPoint(getScreenX(e.getX()), getScreenY(e.getY()));
			else if(mode == 2) {
				street_graph.selectPoint(getScreenX(e.getX()), getScreenY(e.getY()));
				street_graph.connectSelecteds();
			}else if(mode == 3) {
				street_graph.selectPoint(getScreenX(e.getX()), getScreenY(e.getY()));
				street_graph.findSelectedsPath();
			}
 			update();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0) {
			zoom+=0.02;
		}else {
			if(zoom > 0) zoom-=0.02;
		}
		update();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		System.out.println("XD");
		if(key == KeyEvent.VK_W) {
			gap_y -= 10;
		}else if(key == KeyEvent.VK_S) {
			gap_y += 10;
		}if(key == KeyEvent.VK_D){
			gap_x += 10;
		}else if(key == KeyEvent.VK_A) {
			gap_x -= 10;
		}
		update();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	public int getMode() {
		return mode;
	}
	
	public void setMode(int mode) {
		street_graph.clearSelecteds();
		this.mode = mode;
	}
}
