import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // number of colors should be equal to k (number of clusters)
        // also don't change the dimension pretty please
        //Kmeans km = new Kmeans(3, 2);
        //km.setColors(new Color[]{Color.green, Color.blue, Color.magenta});
        //km.run();
        new Kmeans(3, 2).run();
    }
}
