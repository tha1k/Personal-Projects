import java.io.FileReader;
import java.util.SortedMap;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class TwoDTree{


    public static void main(String args[]) throws Exception{
        try{
            String file= args[0];
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            buffer.mark(1000000);
            int grammes = 0;
            while(buffer.readLine() != null){
                grammes++;
            }

            buffer.reset();

            String line;
            line = buffer.readLine();
            int shmeia = Integer.parseInt(line);

            if((shmeia + 1) != grammes){
                System.out.println("Asynepeia eisodou!");
                System.exit(0);
            }

            String synt_x[] = new String[shmeia];
            String synt_y[] = new String[shmeia];
            String split;
            int counter = 0;
            int index = 0;
            while((split = buffer.readLine()) != null){
                String pin[] = split.split(" ");
                if(index < shmeia){
                    synt_x[index] = pin[0];
                    synt_y[index] = pin[1];
                }
                counter++;
                index++;
            }


            boolean eisodoi = true;
            
            for(int i = 0; i < shmeia; i++){
                if(Integer.parseInt(synt_x[i]) > 100 || Integer.parseInt(synt_x[i]) < 0 || Integer.parseInt(synt_y[i]) > 100 || Integer.parseInt(synt_y[i]) < 0){
                    eisodoi = false;
                    break;
                }
            }

            if(counter != shmeia || eisodoi == false){
                System.out.println("Asynepeia eisodou!");
                System.exit(0);
            }

            TwoDTree tree = new TwoDTree();
            for(int i = 0; i < shmeia; i++){
                Point point = new Point(Integer.parseInt(synt_x[i]), Integer.parseInt(synt_y[i]));
                tree.insert(point);
            }

            Scanner scan = new Scanner(System.in);
            int action;

            while(true){
                System.out.println("Select action!");
                System.out.println("1. Compute the size of the tree");
                System.out.println("2. Insert a new point");
                System.out.println("3. Search if a given point exists in the tree");
                System.out.println("4. Provide a query rectangle");
                System.out.println("5. Provide a query point");
                System.out.print("Action: ");
                action = scan.nextInt();
                System.out.println();
                while (action < 1 || action > 5) {
                    System.out.println("Input must be 1-5.");
                    System.out.print("Action: ");
                    action = scan.nextInt();
                }
                switch (action){
                    case 1: {
                        System.out.println("Tree size is equal to " + tree.size() + "\n");
                        break;
                    }
                    case 2: {
                        System.out.print("Input x coordinate: ");
                        int x_co = scan.nextInt(); 
                        System.out.print("Input y coordinate: ");
                        int y_co = scan.nextInt();
                        tree.insert(new Point(x_co, y_co));
                        System.out.println("Point inserted \n");
                        break;
                    }
                    case 3: {
                        System.out.print("Input x coordinate: ");
                        int x_co = scan.nextInt(); 
                        System.out.print("Input y coordinate: ");
                        int y_co = scan.nextInt();
                        boolean yparxei = tree.search(new Point(x_co, y_co));
                        if(yparxei){System.out.println("It does exist! \n");}
                        else{System.out.println("It does not exist! \n");}
                        break;
                    }
                    case 4: {
                        System.out.print("Input rectangles xmax: ");
                        int xmax = scan.nextInt();
                        System.out.print("Input rectangles xmin: ");
                        int xmin = scan.nextInt();
                        System.out.print("Input rectangles ymax: ");
                        int ymax = scan.nextInt();
                        System.out.print("Input rectangles ymin: ");
                        int ymin = scan.nextInt();
                        Rectangle query = new Rectangle(xmin,xmax,ymin,ymax);
                        List<Point> a=tree.rangeSearch(query);
                        //a.insertAtBack(new Point(100,100));
                        System.out.println(a.toString());
                        break;
                    }
                    case 5: {
                        System.out.print("Input query's x coordinate: ");
                        int x_co = scan.nextInt(); 
                        System.out.print("Input queary's y coordinate: ");
                        int y_co = scan.nextInt();
                        Point a = tree.nearestNeighbor(new Point(x_co, y_co));
                        System.out.println(a.toString());
                        break;
                    }
                }
            }

        }catch (Exception e){throw e;}
    }


    private class TreeNode{
        Point item;
        TreeNode l;
        TreeNode r;
        TreeNode parent;

        
        TreeNode(){
        }

        TreeNode(Point x){
            this.item = x;
        }      
    }

    private TreeNode head;
    private int numberOfPoints;

    TwoDTree(){
        numberOfPoints = 0;
    }

    TwoDTree(TreeNode node){
        head = node;
        numberOfPoints = 0;
    }

    public boolean isEmpty(){
        if(head == null){
            return false;
        }
        return true;
    }

    public int size(){
        return numberOfPoints;
    }

    public void insert(Point p){
        head = insertPoint(true, p, head);
    }

    private TreeNode insertPoint(boolean x, Point p, TreeNode node){
        if(node == null){
            numberOfPoints++;
            return new TreeNode(p);
        }
        else if(node.item != null){
            if(node.item.x() == p.x() && node.item.y() == p.y()){
                System.out.println("yparxei");
                return node;
            }
        }
        if(x){
            if(p.x() < node.item.x()){
                node.l = insertPoint(false, p, node.l);
            }
            else{
                node.r = insertPoint(false, p, node.r);
            }
        }
        else{
            if(p.y() < node.item.y()){
                node.l = insertPoint(true, p, node.l);
            }
            else{
                node.r = insertPoint(true, p, node.r);
            }
        }
        return node;
    }

    public boolean search(Point p){
        return searchPoint(true, p, head);
    }


    private boolean searchPoint(boolean x, Point p, TreeNode node){
        if(node == null){
            return false;
        }
        else {
            TwoDTree tree;
            if(node.item != null){
                if(node.item.x() == p.x() && node.item.y() == p.y()){
                    return true;
                }
                else{
                    if(x){
                        if(p.x() < node.item.x()){
                            tree = new TwoDTree(node.l);
                            return tree.searchPoint(false, p, node.l);
                        }
                        else{
                            tree = new TwoDTree(node.r);
                            return tree.searchPoint(false, p, node.r);
                        }
                    }
                    else{
                        if(p.y() < node.item.y()){
                            tree = new TwoDTree(node.l);
                            return tree.searchPoint(true, p, node.l);
                        }
                        else{
                            tree = new TwoDTree(node.r);
                            return tree.searchPoint(true, p, node.r);
                        }
                    }
                }
            }
            else{
                return false;
            }
        }
    }

    public Point nearestNeighbor(Point p){
        
        if(head==null){return new Point(-1,-1);}
        Point min_distp=head.item;
        //double min_dist= min_distp.distanceToSquare(p);
        Rectangle domain= new Rectangle(0,100,0,100);

        return nearestNeighboR(p,min_distp,true,domain,this.head);

    }
    private Point nearestNeighboR(Point p ,Point min,boolean x,Rectangle domain,TreeNode N){
        if(N==null){return min;}//base cond
        if(N.item.distanceToSquare(p)<p.distanceToSquare(min)){
            min=N.item;
        }
        
        if(p.distanceToSquare(min)<domain.distanceTo(p)){return min;
        }
        else{
            if(x){
                Rectangle newdoml= new Rectangle(domain.xmin(),N.item.x(),domain.ymin(),domain.ymax());//domain left of head node Xε[domainmin,head.x]
                Rectangle newdomr=new Rectangle(N.item.x(),domain.xmax(),domain.ymin(),domain.ymax());//right head node Xε[head.x,domainmax]
                min=nearestNeighboR(p,min,false,newdoml,N.l);
                min=nearestNeighboR(p,min,false,newdomr,N.r);
            }else{
                Rectangle newdoml= new Rectangle(domain.xmin(),N.item.x(),domain.ymin(),domain.ymax());//domain left of head node Xε[domainmin,head.x]
                Rectangle newdomr=new Rectangle(N.item.x(),domain.xmax(),domain.ymin(),domain.ymax());//right head node Xε[head.x,domainmax]
                min=nearestNeighboR(p,min,true,newdoml,N.l);
                min=nearestNeighboR(p,min,true,newdomr,N.r);
            }
        }
        return min;  
    }
    public List<Point> rangeSearch(Rectangle r){
        List<Point> list = new List<>();
        return rangeSearchR(r,true,new Rectangle(0,100,0,100),list,this.head);
    }
    private List<Point> rangeSearchR(Rectangle r,boolean level,Rectangle dom,List<Point> list,TreeNode h){
        
        if(h==null){return list;}
        if(r.contains(h.item)){list.insertAtBack(h.item);}
        if(r.intersects(dom)){
          
        
            if(level){
                TwoDTree newTree= new TwoDTree(this.head.l);
                Rectangle newdoml= new Rectangle(dom.xmin(),this.head.item.x(),dom.ymin(),dom.ymax());//right rectagle subtree
                list= newTree.rangeSearchR(r,false,newdoml,list,h.l);
                TwoDTree newTree2= new TwoDTree(this.head.r);   
                Rectangle newdomr= new Rectangle(this.head.item.x(),dom.xmax(),dom.ymin(),dom.ymax());
                list=newTree2.rangeSearchR(r,false,newdomr,list,h.r);
                

            }else{
                TwoDTree newTree= new TwoDTree(this.head.l);
                Rectangle newdoml= new Rectangle(dom.xmin(),dom.xmax(),dom.ymin(),this.head.item.y());//right rectagle subtree
                list=newTree.rangeSearchR(r,false,newdoml,list,h.l);
                TwoDTree newTree2= new TwoDTree(this.head.r);   
                Rectangle newdomr= new Rectangle(dom.xmin(),dom.xmax(),this.head.item.y(),dom.ymax());
                list=newTree2.rangeSearchR(r,false,newdomr,list,h.r);
            }
        }
        return list;

    }
}

