import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph extends JFrame {
    private int width;
    private int height;
    private int pointSize;
    HashMap<int[], Color> points;
    ArrayList<int[]> centroids;
    private JPanel panel;

    public Graph(int width, int height, int pointSize) {
        this.setTitle("KMeans");
        this.width = width;
        this.height = height;
        this.pointSize = pointSize;
        this.points = new HashMap<>();
        this.centroids = new ArrayList<>();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.panel = new JPanel(){

            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                for (int[] point : points.keySet()) {
                    g.setColor(points.get(point));
                    g.fillOval(point[0], point[1], pointSize, pointSize);
                }
                g.setColor(Color.red);
                for (int[] centroid : centroids) {
                    g.fillRect(centroid[0], centroid[1], pointSize, pointSize);
                }

            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

    public void addPoint(int x, int y, Color color) {
        points.put(new int[]{x, y}, color);
    }


    public void addCentroid(int x, int y) {centroids.add(new int[]{x, y});}


    public void clearAll() {
        points.clear();
        centroids.clear();
    }


    public int getGraphWidth(){
        return width;
    }
    public int getGraphHeight() {
        return height;
    }

    public int getPointSize() {
        return pointSize;
    }
}


