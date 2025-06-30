import java.lang.*;

public class Disk implements Comparable<Disk>{
    public int size = 1000000;
    static public int id = 1;
    private List folders = new List();
    public int numberOfFolders = 0;
    private int freeSpace = 1000000;
    public int my_id;

    public Disk(){//ftiaxnei ta disks me diaforetika ids
        this.my_id = id;
        id++;
    }


    public int put_folder(int folder){//bazei fakelo
        this.folders.insertAtFront(folder);
        this.freeSpace -= folder;//ananewnei ton eleu8ero xwro
        this.numberOfFolders += 1;//posous fakelous exei o diskos
        return folder;
    }

    public int getFreeSpace(){
        return this.freeSpace;
    }

    public int compareTo(Disk diskos){
        if(this.freeSpace > diskos.freeSpace){
            return 1;
        }
        else if(this.freeSpace == diskos.freeSpace){
            return 0;
        }
        else{
            return -1;
        }
    }

    public void printFolders() throws Exception{
        try{
            for(int i = 0; i < numberOfFolders; i++){
                System.out.print(Integer.toString(folders.removeFromFront()) + " ");
            }
        }catch (EmptyListException e){throw e;}catch (Exception e){throw e;}

    }

}

