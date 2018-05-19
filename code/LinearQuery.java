import java.util.ArrayList;

/**
 * Range query with time complexity O(n) where n i the number of points
 * To find the neighbors of a point it compares the distances of all other points
 * and adds those with distance smaller than or equal to epsilon to a list.
 * 
 */
public class LinearQuery implements RangeQuery{


    @Override
    public ArrayList<ClusterPoint> findNeighbors(ClusterPoint[] points, ClusterPoint p, int epsilon) {
        ArrayList<ClusterPoint> neighbors = new ArrayList<>();
        
        for (int i = 0; i < points.length; i++) {
            ClusterPoint point = points[i];
            if (p.sqDistanceTo(point) < Math.pow(epsilon, point.getDimension())){
                neighbors.add(point);
            }
        }
        return neighbors;
    }
    
    @Override
    public int countRange(ClusterPoint[] points, ClusterPoint p, int epsilon){
        return findNeighbors(points, p, epsilon).size();
    }
}
