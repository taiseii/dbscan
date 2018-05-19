import java.util.ArrayList;

public class QuadtreeQuery implements RangeQuery {
    double rootH, rootW;

    QuadtreeQuery(double rootH, double rootW) {
        this.rootH = rootH;
        this.rootW = rootW;
    }

    @Override
    public ArrayList<ClusterPoint> findNeighbors(ClusterPoint[] points, ClusterPoint p, int epsilon) {
        ArrayList<ClusterPoint> neighbors = new ArrayList<>();
        QuadtreeNode boundary = new QuadtreeNode(rootW / 2, rootH / 2, rootW / 2, rootH / 2);
        Quadtree qt = new Quadtree(boundary, 1);
        
        for (int i = 0; i < points.length; i++) {
            //inseret points in quad tree
            qt.insert(points[i]);
        }

        double range = Math.pow( epsilon, p.getDimension() );
        QuadtreeNode searchRange =  new QuadtreeNode(p.getCoords()[0], p.getCoords()[1], range, range);
        neighbors.addAll(qt.query(searchRange));

        return neighbors;
    }

    @Override
    public int countRange(ClusterPoint[] points, ClusterPoint p, int epsilon){
        return findNeighbors(points, p, epsilon).size();
    }

}