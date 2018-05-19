import java.util.*;

public class DBSCAN {    
    RangeQuery queryObject;
    
    public DBSCAN(RangeQuery queryObject){
        this.queryObject = queryObject;
    }
    
    /**
     * 
     * @param n number of points
     * @param minPts minPts as specified in lecture notes
     * @param epsilon epsilon as specified in lecture notes
     * @param points array of all points
     * @return number of clusters found
     */
    public int findClustering(int n, int minPts, int epsilon, ClusterPoint[] points){
        /**
         * For every point in our dataset we find the set of it's neighbors N
         * A neighbor is a point with distance smaller than or equal to epsilon
         * If |N| < minPts it is not a core point and we move to the next point
         */
        int cluster_counter = 0;
        
        for (int i = 0; i < n; i++) {
            ClusterPoint current_point = points[i];
            if (current_point.isLabeled()) {
                continue;
            }
            ArrayList<ClusterPoint> N = queryObject.findNeighbors(points, current_point, epsilon);

            if (N.size() < minPts) {
                current_point.setCluster(0);
                continue;
            }
            /**
             * If this number not smaller than minPts we classify it as a core
             * point and begin labeling this to a new cluster (cc).
             */
            cluster_counter++;
            current_point.setCluster(cluster_counter);

            LinkedList<ClusterPoint> S = new LinkedList<>(N);

            /**
             * We add all it's neighbors N to stack S
             * For every P we pop off S, if P is labeled as noise we add P to cc.
             * 
             * If P has not been labeled before we process it by finding the set
             * of it's neighbors N. Once again if |N| >= minPts P is a core point
             * and we add each of N to S.
             */
            while (!S.isEmpty()) {
                ClusterPoint seed_point = S.pop();
                        
                if (seed_point.isLabeled()) {
                    continue;
                }
                N = queryObject.findNeighbors(points, seed_point, epsilon);
                if (N.size() < minPts) continue;
                seed_point.setCluster(cluster_counter);
                S.addAll(N);
            }
        }
        return cluster_counter;
    }
}
