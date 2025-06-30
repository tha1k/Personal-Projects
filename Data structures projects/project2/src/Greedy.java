import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public class Greedy{
    //MAIN-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) throws Exception{
        
        String file = args[0];//pernei apto cmd txt arxeio
        try{
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            buffer.mark(1000000000);
            String line;
            int fakeloi[];
            int number_of_files = 0;

            while(buffer.readLine() != null){
                number_of_files++;
            }

            //diabazei posous faekelous exw 
            
            fakeloi = new int[number_of_files];

            buffer.reset();//reset sthn arxh to arxeio

            int reps = 0;

            while(reps < number_of_files){//oso exei fakelous to arxeio diabaze
                line = buffer.readLine();
                if(Integer.parseInt(line)< 0 || Integer.parseInt(line)> 1000000){
                    System.out.println("File size out of bounds!");
                    System.exit(0);
                }
                fakeloi[reps] = Integer.parseInt(line);
                reps++;
            }
            

            buffer.close();
            
            Disk[] diskoi = new Disk[number_of_files];//ftiaxnei pinaka me to megisto plh8os diskwn pou mporei na xreiastoun

            for(int i = 0; i < number_of_files; i++){//ton gemizei me disks
                diskoi[i] = new Disk();
            }

            Greedy.algori8mos1(fakeloi, diskoi);//efarmogh algori8mou1
            
            int diskoi_se_xrhsh = 0;

            for(int i =0; i < diskoi.length; i++){//checkarei posous aptous diskous xrhshmopoihse
                if(diskoi[i].getFreeSpace() != 1000000){
                    diskoi_se_xrhsh++;
                }
            }

            int mege8os_fakelwn= 0;//to synoliko mege8os olwn twn fakelwn tou arxeiou

            for(int i =0; i < fakeloi.length; i++){//ypologizetai ^^^
                mege8os_fakelwn += fakeloi[i];
            }

            System.out.println("Sum of all folders = " + Integer.toString(mege8os_fakelwn/1000) + "TB");
            System.out.println("Total number of disks used = " + Integer.toString(diskoi_se_xrhsh));

            MaxPQ pq = new MaxPQ(new Comparator<Disk>() {//priority q gia na ektypwnei tous diskous apo ton disko me perissotero adeio xwro pros autous me ton ligotero adeio xwro
                @Override
                public int compare(Disk d1, Disk d2){
                    return d1.compareTo(d2);
                }
            });

            for(int i = 0; i < diskoi_se_xrhsh; i++){//bazei tous diskous sto q
                pq.insert(diskoi[i]);
            }


            if(number_of_files < 100){//an exoume ligotera apo 100 files ektypwnei to id, ton eleu8ero xwro, kai poious fakelous exei mesa
                for(int i = 0; i < diskoi_se_xrhsh; i++){
                    Disk current_disk = (Disk)pq.getMax();
                    System.out.print("id " + Integer.toString(current_disk.my_id) + " " + Integer.toString(current_disk.getFreeSpace()) + ": ");
                    current_disk.printFolders();
                    System.out.println();
                }
            }
            
        }catch(FileNotFoundException e){throw e;}catch(IOException e){throw e;}catch(Exception e){throw e;}

    }

    //ALGORI8MOS 1-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void algori8mos1 (int[] fakeloi, Disk[] diskoi){
        for(int i = 0; i < fakeloi.length; i++){//gia ka8e fakelo sto fakeloi[] 
            for(int y = 0; y < diskoi.length - 1; y++){//gia ka8e disko sto diskoi[]
                if(fakeloi[i] < diskoi[y].getFreeSpace()){//an xwraei o fakelos
                    diskoi[y].put_folder(fakeloi[i]);//balton ston disko
                    break;//proxwra ston epomeno fakelo
                }//an den xwraei checkare ston epomeno disko mexri na breis enan na xwraei
            }
        }
    }

    //ALGORI8MOS 2-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void algori8mos2(int[] fakeloi, Disk[] diskoi){
        Sort.QuickSort(fakeloi, 0, fakeloi.length- 1);
        Greedy.algori8mos1(fakeloi, diskoi);
    }

}

