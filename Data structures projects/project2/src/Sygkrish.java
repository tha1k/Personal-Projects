import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public class Sygkrish{
    public static void main(String args[]) throws Exception{

        int diskoi_gia_n_iso_me_10_otan_trexei_o_1=0, diskoi_gia_n_iso_me_15_otan_trexei_o_1 = 0, diskoi_gia_n_iso_me_20_otan_trexei_o_1=0;
        int diskoi_gia_n_iso_me_10_otan_trexei_o_2=0, diskoi_gia_n_iso_me_15_otan_trexei_o_2=0, diskoi_gia_n_iso_me_20_otan_trexei_o_2=0;


        try{
            for(int j = 0; j< 30; j++){
                FileReader file = new FileReader("D:\\AUEB\\g_e3amhno\\domes\\ergasia_2\\" + Integer.toString(j) + "_fakelos.txt");//diabazei ola ta files apo to sygkekrimeno path opoy einai apo8hkeumena sto pc mas
                BufferedReader buffer = new BufferedReader(file);
                buffer.mark(1000000000);
                int number_of_files = 0;
                String line;
                int[] fakeloi;

                while(buffer.readLine() != null){
                   number_of_files++;
                }

        
                fakeloi = new int[number_of_files];

                buffer.reset();

                int reps = 0;

                while(reps < number_of_files){
                    line = buffer.readLine();
                    if(Integer.parseInt(line)< 0 || Integer.parseInt(line)> 1000000){
                        System.out.println("File size out of bounds!");
                        System.exit(0);
                    }
                    fakeloi[reps] = Integer.parseInt(line);
                    reps++;
                    }

                Disk[] diskoi1;
                Disk[] diskoi2;

                buffer.close();
            
                diskoi1 = new Disk[number_of_files];
                diskoi2 = new Disk[number_of_files];

                for(int i = 0; i < number_of_files; i++){
                    diskoi1[i] = new Disk();
                }

                for(int i = 0; i < number_of_files; i++){
                    diskoi2[i] = new Disk();
                }

                System.out.println("GIA TON FAKELO ME ONOMA " + Integer.toString(j) + "_fakelos:");
                Greedy.algori8mos1(fakeloi, diskoi1);
            

                int diskoi_se_xrhsh1 = 0;

                for(int i =0; i < diskoi1.length; i++){
                    if(diskoi1[i].getFreeSpace() != 1000000){
                        diskoi_se_xrhsh1++;
                    }
                }   

                System.out.println("Total number of disks used = " + Integer.toString(diskoi_se_xrhsh1) + " kata ton algori8mo 1");

                Greedy.algori8mos2(fakeloi, diskoi2);

                int diskoi_se_xrhsh2 = 0;

                for(int i =0; i < diskoi2.length; i++){
                    if(diskoi2[i].getFreeSpace() != 1000000){
                        diskoi_se_xrhsh2++;
                    }
                }

                System.out.println("Total number of disks used = " + Integer.toString(diskoi_se_xrhsh2) + " kata ton algori8mo 2");

                if(j<10){//metraei to synolo twn fakelwn
                    diskoi_gia_n_iso_me_10_otan_trexei_o_1 += diskoi_se_xrhsh1;
                    diskoi_gia_n_iso_me_10_otan_trexei_o_2 += diskoi_se_xrhsh2;
                }

                if(j>=10 && j<20){
                    diskoi_gia_n_iso_me_15_otan_trexei_o_1 += diskoi_se_xrhsh1;
                    diskoi_gia_n_iso_me_15_otan_trexei_o_2 += diskoi_se_xrhsh2;
                }

                if(j<30 && j>=20){
                    diskoi_gia_n_iso_me_20_otan_trexei_o_1 += diskoi_se_xrhsh1;
                    diskoi_gia_n_iso_me_20_otan_trexei_o_2 += diskoi_se_xrhsh2;
                }

                System.out.println();

            }
            
        }catch(Exception e){throw e;}

        System.out.println("O ALGORI8MOS 1 XRHSIMOPOIEI KATA MESO ORO: " + Integer.toString(diskoi_gia_n_iso_me_10_otan_trexei_o_1/10)/*MESOS OROS GIATI GIA KA8E N EXOUME 10 FAKELOUS */ +" DISKOUS OTAN KA8E ARXEIO EXEI 10 FOLDERS\n");
        System.out.println("O ALGORI8MOS 2 XRHSIMOPOIEI KATA MESO ORO: " + Integer.toString(diskoi_gia_n_iso_me_10_otan_trexei_o_2/10) +" DISKOUS OTAN KA8E ARXEIO EXEI 10 FOLDERS\n");
        System.out.println("O ALGORI8MOS 1 XRHSIMOPOIEI KATA MESO ORO: " + Integer.toString(diskoi_gia_n_iso_me_15_otan_trexei_o_1/10) +" DISKOUS OTAN KA8E ARXEIO EXEI 15 FOLDERS\n");
        System.out.println("O ALGORI8MOS 2 XRHSIMOPOIEI KATA MESO ORO: " + Integer.toString(diskoi_gia_n_iso_me_15_otan_trexei_o_2/10) +" DISKOUS OTAN KA8E ARXEIO EXEI 15 FOLDERS\n");
        System.out.println("O ALGORI8MOS 1 XRHSIMOPOIEI KATA MESO ORO: " + Integer.toString(diskoi_gia_n_iso_me_20_otan_trexei_o_1/10) +" DISKOUS OTAN KA8E ARXEIO EXEI 20 FOLDERS\n");
        System.out.println("O ALGORI8MOS 2 XRHSIMOPOIEI KATA MESO ORO: " + Integer.toString(diskoi_gia_n_iso_me_20_otan_trexei_o_2/10) +" DISKOUS OTAN KA8E ARXEIO EXEI 20 FOLDERS\n");

    }
}