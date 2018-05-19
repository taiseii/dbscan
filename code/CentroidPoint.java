public class CentroidPoint extends Point {
    public int clusterID, pointCount;
    private Point sumPoint;
    private double score;
    int[] zero;  
    public CentroidPoint(int dimension, double[] coords, int clusterID) throws IllegalArgumentException {
        super(dimension, coords);
        
        this.clusterID = clusterID;
        
        
        sumPoint = new Point(dimension);
    }

    @Override
    public void add(Point other) throws IllegalArgumentException {
        this.pointCount++;
        this.sumPoint.add(other);
        long dist = this.sqDistanceTo(other);
        this.score += dist;
    }
    
    public double[] getSumCoords() {
        return this.sumPoint.getCoords();
    }
    
    public double getScore() {
        return this.score;
    }
    
    public void reset() {
        this.score = 0;
        this.pointCount = 0;
        this.sumPoint = new Point(this.getDimension());
    }
}
