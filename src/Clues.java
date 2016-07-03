import java.util.*;

public class Clues {
    public ArrayList<Vector<Integer>> rows;
    public ArrayList<Vector<Integer>> columns;

    public Clues(int width, int height) {
        rows = new ArrayList<Vector<Integer>>(width);
        columns = new ArrayList<Vector<Integer>>(height);
    }
}
