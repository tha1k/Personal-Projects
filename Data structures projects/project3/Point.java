public class Point{
    private int x,y;
    public int x(){return x;}
    public int y(){return y;}
    public Point(int x,int y){this.x=x;this.y=y;}
    public Point(){x=0;y=0;}
    public int squareDistanceto(Point z){
        int dx= x-z.x(); int dy= y-z.y();
        return dx*dx+dy*dy;
    }
    public double distanceToSquare(Point a){
        int dx= x-a.x(); int dy= y-a.y();
        return Math.sqrt(dx*dx+dy*dy);
    }
    public String toString(){
        return "("+x+", "+y+")";
    }

}