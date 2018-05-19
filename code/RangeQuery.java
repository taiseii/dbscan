import java.util.ArrayList;

public interface RangeQuery {
    public abstract ArrayList<ClusterPoint> findNeighbors(ClusterPoint[] points, ClusterPoint p, int epsilon);
    public abstract int countRange(ClusterPoint[] points, ClusterPoint p, int epsilon);
}
