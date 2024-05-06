public class Centroid {
    private double[] attributes;
    private int id;

    public Centroid(double[] attributes, int id) {
        this.attributes = attributes;
        this.id = id;
    }

    public double[] getAttributes() {
        return attributes;
    }

    public void clearAttributes() {
        attributes = new double[attributes.length];
    }
    public int getId() {
        return id;
    }
    public void setAttributes(double[] attributes) {
        this.attributes = attributes;
    }
}
