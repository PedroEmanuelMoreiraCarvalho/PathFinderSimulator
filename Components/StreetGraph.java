package Components;

import java.awt.Graphics;
import java.util.ArrayList;

import Exceptions.NoPointSelected;

public class StreetGraph {
	
	public ArrayList<Point> points;
	private ArrayList<Street> streets;
	private ArrayList<Street> path;
	public int[] selecteds;

	public StreetGraph() {
		points = new ArrayList<Point>();
		streets = new ArrayList<Street>();
		path = new ArrayList<Street>();
		selecteds = new int[]{-1 , -1};
	}
	
	public void addPoint(int x, int y) {
		try {
			getPoint(x, y);
		}
		catch(NoPointSelected e) {
			points.add(new Point(x,y));
		}
	}
	
	public Point getPoint(int index) {
		return points.get(index);
	}

	public void render(Graphics g, int gap_x, int gap_y, float zoom) {
		for(Street street: streets) {
			points.get(street.objects_index[0]).drawStreetTo(g, points.get(street.objects_index[1]), gap_x, gap_y, zoom);
		}
		for(Street path_street: path) {
			points.get(path_street.objects_index[0]).drawPathTo(g, points.get(path_street.objects_index[1]), gap_x, gap_y, zoom);
		}
		for(Point point: points) {
			point.render(g, gap_x, gap_y, zoom);
		}
	}
	
	public void connectSelecteds() {
		if(selecteds[0] == -1) return;
		Point p_1 = points.get(selecteds[0]);
		Point p_2 = points.get(selecteds[1]);
		int coust = getDistanceBy(p_1.getX(),p_1.getY(),p_2.getX(),p_2.getY());
		p_1.deselect();
		p_2.deselect();
		streets.add(new Street(selecteds[0],selecteds[1],coust));
		streets.add(new Street(selecteds[1],selecteds[0],coust));
		clearSelecteds();
	}
	
	public void clearSelecteds() {
		if(selecteds[0] != -1) getPoint(selecteds[0]).deselect();
		if(selecteds[1] != -1) getPoint(selecteds[1]).deselect();
		selecteds = new int[]{-1,-1};
	}
	
	private void clearPath() {
		path.clear();
	}

	private void select(int point) {
		if(selecteds[0] != -1) points.get(selecteds[0]).deselect();
		selecteds[0] = selecteds[1];
		selecteds[1] = point;
		points.get(point).select();
	}

	public void selectPoint(int screen_x, int screen_y) {
		try {
			select(getPoint(screen_x,screen_y));
		}catch(Exception e) {}
	}
	
	public int getPoint(int screen_x, int screen_y) throws NoPointSelected {
		for(int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			if(getDistanceBy(screen_x, screen_y, point.getX(), point.getY()) <= 12) {
				return i;
			}
		}
		throw new NoPointSelected();
	}
	
	private int getDistanceBy(int x1, int y1, int x2, int y2) {
		return (int)(Math.sqrt((y2-y1)*(y2-y1)+(x2-x1)*(x2-x1)));
	}
	
	public void findSelectedsPath() {
		clearPath();
		Path better_path = getPath(selecteds[0],selecteds[1]);
		if(better_path != null) {
			for(Street path_street: better_path.path)
				path.add(path_street);
			clearSelecteds();
		}
		return;
	}
	
	private Path getPath(int point_1, int point_2) {
		ArrayList<Path> paths = new ArrayList<Path>();
		ArrayList<Integer> explorateds = new ArrayList<Integer>();
		Path minor_path = new Path(new ArrayList<Street>(), 0);
		
		for(Street street: getConnections(point_1)) {
			Path new_path = new Path(minor_path.path, minor_path.total_coust);
			new_path.add(street);
			paths.add(new_path);
		}
		
		while(!paths.isEmpty()) {
			minor_path = paths.get(paths.size()-1);
			for(Path path: paths)
				if(path.total_coust < minor_path.total_coust) minor_path = path;
			
			if(minor_path.getLast().objects_index[1] == point_2) return minor_path;
			
			paths.remove(minor_path);
			explorateds.add(minor_path.getLast().objects_index[0]);
			for(Street conn: getConnections(minor_path.getLast().objects_index[1])){
				if(explorateds.contains(conn.objects_index[1])) continue;
				Path new_path = new Path(minor_path.path, minor_path.total_coust);
				new_path.add(conn);
				paths.add(new_path);
			}
		}
		
		return null;//there's no path
	}
	
	private ArrayList<Street> getConnections(int point){
		ArrayList<Street> connections = new ArrayList<Street>();
		for(Street street: streets) {
			if(street.objects_index[0] == point)
			connections.add(street);
		}
		return connections;
	}
	
	private class Path {
		private ArrayList<Street> path = new ArrayList<Street>();
		private int total_coust;
		
		public Path(ArrayList<Street> path, int total_coust) {
			this.path.addAll(path);
			this.total_coust = total_coust;
		}
		
		public void add(Street new_street) {
			path.add(new_street);
			total_coust += new_street.coust;
		}
		
		public Street getLast() {
			return path.get(path.size()-1);
		}
	}
	
	private class Street {
		private int objects_index[] = new int[2];
		private int coust;
		public Street(int index_obj1, int index_obj2, int coust) {
			this.objects_index[0] = index_obj1;
			this.objects_index[1] = index_obj2;
			this.coust = coust; 
		}
	}
}
