import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Ftia3e_Arxeia{
    //static me8odos dhmiourgias kai pio katw main gia thn klhsh ths
    
    //ME8ODOS DHMIOURGIAS ARXEIWN------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void Create() throws Exception{
        try{

            Random rand = new Random();

            for(int i =0 ; i< 10; i++){//ftiaxnei 10 arxeia gia N=10
                File fakelos = new File("D:\\AUEB\\g_e3amhno\\domes\\ergasia_2\\"+ Integer.toString(i) + "_fakelos.txt");
                fakelos.createNewFile();
                FileWriter fw = new FileWriter(fakelos);
                for(int j = 0; j < 10; j++){
                    fw.write(Integer.toString(rand.nextInt(1000000)) + "\n");
                }
                fw.close();
            }

            for(int i =0 ; i< 10; i++){//ftiaxnei 10 arxeia gia N=15
                File fakelos = new File("D:\\AUEB\\g_e3amhno\\domes\\ergasia_2\\"+ Integer.toString(i+ 10) + "_fakelos.txt");
                fakelos.createNewFile();
                FileWriter fw = new FileWriter(fakelos);
                for(int j = 0; j < 15; j++){
                    fw.write(Integer.toString(rand.nextInt(1000000)) + "\n");
                }
                fw.close();
            }

            for(int i =0 ; i< 10; i++){//ftiaxnei 10 arxeia gia N=20
                File fakelos = new File("D:\\AUEB\\g_e3amhno\\domes\\ergasia_2\\"+ Integer.toString(i + 20) + "_fakelos.txt");
                fakelos.createNewFile();
                FileWriter fw = new FileWriter(fakelos);
                for(int j = 0; j < 20; j++){
                    fw.write(Integer.toString(rand.nextInt(1000000)) + "\n");
                }
                fw.close();
            }

            
        }catch(IOException e){throw e;}catch(Exception e){throw e;}
    }

    //H MAIN POU TREXEI THN CREATE--------------------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String args[]) throws Exception {
        Ftia3e_Arxeia.Create();
    }
}