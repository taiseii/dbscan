
// this class represent rectangle node property

class QuadtreeNode {
    // w & h are half width and height of the fill size rectangle
    double w, h, x, y;

    // constructor used to create root
    QuadtreeNode(double x, double y, double w, double h) {
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
    }

    public void setBoundary(double x, double y, double w, double h) {
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean containe(ClusterPoint point) {
        boolean inRange = false;

        if (x - w <= point.getCoords()[0] && point.getCoords()[0] <= x + w) {
            if (y - h <= point.getCoords()[1] && point.getCoords()[1] <= y + h) {
                inRange = true;
            }
        }
        // do stuff to check weather particular point is in

        return inRange;
    }

    public boolean intersect(QuadtreeNode range){
        boolean inBox = false;
       
        if( range.x - range.w > this.x + this.w || //out of range
            range.x + range.w < this.y - this.w || //out of range
            range.y - range.h > this.y + this.h ||
            range.y + range.h < this.y - this.h ){
                inBox = false;
            } else {
                inBox = true;
        }

        return inBox;        
    }

}