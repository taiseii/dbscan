import java.util.ArrayList;

class Quadtree {
    QuadtreeNode boundary;
    int capacity; // number of points before being subdividedd
    int size;
    Quadtree ne, nw, se, sw; //children
    ArrayList<ClusterPoint> arrPoints;// = new ArrayList<>();
    boolean divided;

    Quadtree(QuadtreeNode boundary, int cap /*, ArrayList<ClusterPoint> arrPoints*/ ) {
        this.boundary = boundary;
        this.capacity = cap;
        this.arrPoints = new ArrayList<ClusterPoint>();
        this.divided = false;
        this.size = arrPoints.size();
    }

    // helper functions ------------------------------------
    public int getQtSize() {
        return arrPoints.size();
    }

    public ArrayList<ClusterPoint> getQtPoints() {
        return arrPoints;
    }

    public int getQtCap() {
        return capacity;
    }

    public QuadtreeNode getBoundary() {
        return boundary;
    }

    public boolean isdivided() {
        return divided;
    }
    // ------------------------------------------------------

    public void subdivide() {

        double sx, sy, sW, sH;

        sx = boundary.getX();
        sy = boundary.getY();
        sW = boundary.getWidth();
        sH = boundary.getHeight();

        ArrayList<ClusterPoint> nwp = new ArrayList<>();
        ArrayList<ClusterPoint> nep = new ArrayList<>();
        ArrayList<ClusterPoint> sep = new ArrayList<>();
        ArrayList<ClusterPoint> swp = new ArrayList<>();

        QuadtreeNode ne_n_boundary = new QuadtreeNode(sx + sW / 2, sy + sH / 2, sW / 2, sH / 2);
        QuadtreeNode nw_n_boundary = new QuadtreeNode(sx - sW / 2, sy + sH / 2, sW / 2, sH / 2);
        QuadtreeNode se_n_boundary = new QuadtreeNode(sx + sW / 2, sy - sH / 2, sW / 2, sH / 2);
        QuadtreeNode sw_n_boundary = new QuadtreeNode(sx - sW / 2, sy - sH / 2, sW / 2, sH / 2);
      

        ne = new Quadtree(ne_n_boundary,this.capacity);
        nw = new Quadtree(nw_n_boundary,this.capacity);
        se = new Quadtree(se_n_boundary,this.capacity);
        sw = new Quadtree(sw_n_boundary,this.capacity);

        

        this.divided = true;

    }

    public boolean insert(ClusterPoint point) {
        boolean inserted = false;
        if(!this.boundary.containe(point)){
            inserted = false;
        }

        if (arrPoints.size() <= capacity) {
            // do nothing. add ClusterPoint to the node withoud dividing
            this.arrPoints.add(point);
            return true;
        } else {
            if (!divided) {
                subdivide();
                this.divided = true;
            }

            if(this.ne.insert(point)){
                inserted = true;
            }else if(this.nw.insert(point)){
                inserted = true;
            }else if(this.se.insert(point)){
                inserted = true;
            }else if(this.sw.insert(point)){
                inserted = true;
            }     
        }
        return inserted;
    }

    int count;

    public void setCount(int count){
        this.count = count;
    }


    public ArrayList<ClusterPoint> query(QuadtreeNode range){
        ArrayList<ClusterPoint> found = new ArrayList<>();
        if(! range.intersect(range)){
            return found;
        }else {
            for(int i=0; i<this.arrPoints.size(); i++){
                count ++;
                //search the node
                if(range.containe(this.arrPoints.get(i))){
                    found.add( this.arrPoints.get(i) );
                }
            }
        }

        if(this.divided){
            found.addAll(this.nw.query(range));
            found.addAll(this.ne.query(range));
            found.addAll(this.sw.query(range));
            found.addAll(this.se.query(range));
        }
        
        return found;
    }

    public int getCount(){
        return count;
    }


}