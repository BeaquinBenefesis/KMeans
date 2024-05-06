public class Point {
    private double[] attributes;
    private int classification;

    public Point(double[] attributes) {
        this.attributes = attributes;
        classification = 0;
    }

    public double calcDistance(Centroid centroid) {
        if (centroid.getAttributes().length != attributes.length) {
            throw new RuntimeException("Centroid size != attributes size");
        }
        double sum = 0;
        for (int i = 1; i < attributes.length; i++) {
            sum += attributes[i] - centroid.getAttributes()[i];
            sum = Math.pow(sum, 2);
        }
        return Math.sqrt(sum);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Double attribute : attributes) {
            sb.append(attribute).append(" ");
        }
        return sb.toString();
    }

    public double[] getAttributes() {
        return attributes;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }
}
