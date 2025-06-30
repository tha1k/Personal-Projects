public class Rectangle{
    private int xmin,ymin,xmax,ymax;
    Point a , b ,c ,d ;
    //the rectangle is represented by four points 
    public int xmin(){return xmin;}
    public int ymin(){return ymin;}
    public int xmax(){return xmax;}
    public int ymax(){return ymax;}
    public Rectangle(int xmin, int xmax, int ymin,int ymax){
        this.xmin=xmin;
        this.xmax=xmax;
        this.ymin=ymin;
        this.ymax=ymax;
        a= new Point(xmin,ymin);
        b= new Point(xmin,ymax);
        c= new Point(xmax,ymin);
        d= new Point(xmax,ymax);
        /*b      d


          a      c
        */
    }
    public boolean contains(Point p){
        return this.xmin<=p.x() && this.xmax>=p.x()&& this.ymin<=p.y()&& this.ymax>=p.y();
        /*if(this.xmin<=p.x() && this.xmax>=p.x()&& this.ymin<=p.y()&& this.ymax>=p.y()){
            return true;
        }
        return false;*/
    }
    public boolean intersects(Rectangle that){
        if(this.contains(that.a)||this.contains(that.b)||this.contains(that.c)||this.contains(that.d)){return true;}
        else if(that.contains(this.a)||that.contains(this.b)||that.contains(this.c)||that.contains(this.d)){return true;}
        else{ return false;}
        
    }
    public String toString(){
        return "["+xmin+", "+xmax+"]"+"x"+"["+ymin+", "+ymax+"]";
    }
    public double distanceTo(Point z){return Math.sqrt(squareDistanceto(z));}
    public int squareDistanceto(Point p){//see comments in lines 18-22
        if(this.contains(p)){return 0;}
        if(p.x()<=this.xmin()){//left 
            if(p.y()<this.ymin()){//lower
                return a.squareDistanceto(p);
            }
            else if(p.y()<this.ymax){//use a border point that is vertical to the given point
                return p.squareDistanceto(new Point(xmin,p.y()));
            }
            return b.squareDistanceto(p);//above
        }
        if(p.x()>=this.xmax){//right
            if(p.y()<this.ymin){//lower
                return c.squareDistanceto(p);
            }
            else if(p.y()<this.ymax){
                return p.squareDistanceto(new Point(xmax,p.y()));
            }
            return d.squareDistanceto(p);
        }
        if(p.x()> xmin && p.x()<xmax){//between x borders(over or under the rect)
            if(p.y()<ymin){//under
                return p.squareDistanceto(new Point(p.x(),ymin));
            }
            else if(p.y()>ymax){
                return p.squareDistanceto(new Point(p.x(),ymax));
            } 
        }

        return 0;
    }
}