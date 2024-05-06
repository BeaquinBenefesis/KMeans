import com.opencsv.CSVReader;

import java.awt.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Kmeans {
    private ArrayList<Point> data;
    private ArrayList<Centroid> centroids;
    private HashMap<Centroid, ArrayList<Point>> clusters;
    private Color[] colors = new Color[]{Color.BLUE, Color.BLACK, Color.GREEN};
    private Graph graph = new Graph(500, 500, 10);
    private int dataDimension;
    private int k;

    public Kmeans(int k, int dataDimension) {
        this.dataDimension = dataDimension;
        this.k = k;
        data = new ArrayList<>();
        centroids = new ArrayList<>();
        clusters = new HashMap<>();
    }

    public void run(){
        readData("src/main/java/data.csv");
        scaleData();
        initializeGraph();
        graph.repaint();
        int genCount = 0;
        while (nextGeneration()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            redraw();
            System.out.println("Generation:"  + genCount++);
        }
        System.out.println("Finished");
    }

    // returns true if point classification was modified
    public boolean nextGeneration() {
        boolean classificationChanged = false;
        for (Point point : data) {
            Centroid centroidWithMinDistance = centroids.get(point.getClassification()); //classification should represent index in centroid array
            double minDistance = 1000000; // big random distance idk
            double distance;
            for (Centroid centroid : centroids) {
                distance = point.calcDistance(centroid);
                if (distance < minDistance) {
                    minDistance = distance;
                    centroidWithMinDistance = centroid;
                }
            }
            if (centroidWithMinDistance.getId() != point.getClassification()) {
                clusters.get(centroids.get(point.getClassification())).remove(point); // prev centroid
                clusters.get(centroidWithMinDistance).add(point);
                point.setClassification(centroidWithMinDistance.getId());
                classificationChanged = true;
            }
        }
        if (classificationChanged) {
            // compute new values for centroids
            for (Centroid centroid : centroids) {
                double[] newAttributes = new double[dataDimension];
                for (Point point : clusters.get(centroid)) {
                    for (int i = 0; i < point.getAttributes().length; i++) {
                        newAttributes[i] += point.getAttributes()[i];
                    }
                }
                for (int i = 0; i < newAttributes.length; i++) {
                    newAttributes[i] /= clusters.get(centroid).size();
                }
                centroid.setAttributes(newAttributes);
            }
        }
        return classificationChanged;
    }

    public void readData(String file) {
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            int index = 0;
            while ((nextRecord = csvReader.readNext()) != null) {
                double[] pointAttributes = new double[dataDimension];
                for (String cell : nextRecord) {
                    pointAttributes[index] = Double.parseDouble(cell);
                    index++;
                }
                data.add(new Point(pointAttributes));
                index = 0;
            }
        }
        catch (Exception e) {
            System.err.println("FileReader failed");
            System.exit(1);
        }
    }

    public void initializeGraph() {
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            Centroid centroid = new Centroid(new double[dataDimension], i);
            centroids.add(centroid);
            clusters.put(centroid, new ArrayList<>());
            for (int j = 0; j < dataDimension; j++) {
                centroids.get(i).getAttributes()[j] = random.nextDouble(0, 1);
            }
        }
        clusters.get(centroids.get(0)).addAll(data); // initially put all point in cluster 0

        graph.repaint();
    }

    public void scaleData() {
        int dimensions = data.get(0).getAttributes().length;
        double[] max = new double[dimensions];
        double[] min = new double[dimensions];
        int tmp = 0;
        for (int i = 0; i < dimensions; i++) {
            for (Point point : data) {
                if (point.getAttributes()[i] > max[tmp]) {
                    max[tmp] = point.getAttributes()[i];
                }
                if (point.getAttributes()[i] < min[tmp]) {
                    min[tmp] = point.getAttributes()[i];
                }
            }
            tmp++;
        }
        for (Point point : data) {
            tmp = 0;
            for (int i = 0; i < dimensions; i++) {
                double newVal = (point.getAttributes()[i] - min[tmp]) / (max[tmp] - min[tmp]);
                point.getAttributes()[i] = newVal;
                tmp++;
            }
        }
    }

    private void redraw() {
        graph.clearAll();
        for (Point point : data) {
            graph.addPoint((int) (point.getAttributes()[0] * (graph.getGraphWidth() - graph.getPointSize())), (int) (point.getAttributes()[1] * (graph.getGraphHeight() - graph.getPointSize())), colors[point.getClassification()]);
        }
        for (Centroid centroid : centroids) {
            graph.addCentroid((int) (centroid.getAttributes()[0] * (graph.getGraphWidth() - graph.getPointSize())), (int) (centroid.getAttributes()[1] * (graph.getGraphHeight() - graph.getPointSize())));
        }
        graph.repaint();
    }

    public void setData(ArrayList<Point> newData) {
        data = newData;
    }

    public void setCentroids(ArrayList<Centroid> newCentroids) {
        centroids = newCentroids;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }
}
