package Application;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import Components.Canvas;
import Components.StreetGraph;

public class App {

	private final int HEIGHT = 500, WIDTH = 700;
	public static int off_set_render_x = 10, off_set_render_y = 60;
	
	public static void main(String[] args) {
		App app = new App();
		StreetGraph gps = new StreetGraph(); 
		Canvas canvas = new Canvas(app.getWidth(), app.getHeight(), gps);
		
		JFrame frame = new JFrame("GPS");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(app.getWidth(),app.getHeight());
		JMenuBar menu = new JMenuBar();
		JButton m1 = new JButton("Add Point");
		JButton m2 = new JButton("Connect Points");
		JButton m3 = new JButton("Find Path");
		menu.add(m1);
		menu.add(m2);
		menu.add(m3);
		
		m1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(canvas.getMode() == 1) canvas.setMode(0);
				else canvas.setMode(1);// 1 = AddPoint Mode
				frame.setFocusable(true);
				frame.requestFocus();
			}
		});
		
		m2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(canvas.getMode() == 2) canvas.setMode(0);
				else canvas.setMode(2);// 2 = ConnectPoints Mode
				frame.setFocusable(true);
				frame.requestFocus();
			}
		});
		
		m3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(canvas.getMode() == 3) canvas.setMode(0);
				else canvas.setMode(3);// 3 = FindPath Mode
				frame.setFocusable(true);
				frame.requestFocus();
			}
		});
		
		frame.addMouseListener(canvas);
		frame.addMouseWheelListener(canvas);
		frame.addKeyListener(canvas);
		frame.setFocusable(true);
		frame.requestFocus();
 
		frame.getContentPane().add(BorderLayout.NORTH, menu);
		frame.getContentPane().add(BorderLayout.CENTER, canvas);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
}