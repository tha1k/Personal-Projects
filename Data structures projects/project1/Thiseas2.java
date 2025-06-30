import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class Thiseas2{   
    
    public static void main (String[] args) throws Exception{
        
        FileReader file = new FileReader("D:\\AUEB\\g_e3amhno\\domes\\ergasia_1\\sample-input.txt");
        try{
            BufferedReader buffer = new BufferedReader(file);
            String line;
            line = buffer.readLine();
            int ari8mos_grammwn;
            int ari8mos_sthlwn;
            String diastaseis[] = line.split(" ");
            ari8mos_grammwn = Integer.parseInt(diastaseis[0]);
            ari8mos_sthlwn = Integer.parseInt(diastaseis[1]);

            line = buffer.readLine();
            int syntetagmenh_grammhs_eisodou;
            int syntetagmenh_sthlhs_eisodou;
            String eisodos[] = line.split(" ");
            syntetagmenh_grammhs_eisodou = Integer.parseInt(eisodos[0]);
            syntetagmenh_sthlhs_eisodou = Integer.parseInt(eisodos[1]);

            String[][] maze = new String[ari8mos_grammwn][ari8mos_sthlwn];
            int grammh = 0;
            
            while(grammh < ari8mos_grammwn){
                line = buffer.readLine();
                maze[grammh] = line.split(" ");
                grammh++;
            }


            String syntetagmenes_eisodou = maze[syntetagmenh_grammhs_eisodou][syntetagmenh_sthlhs_eisodou];

            if(syntetagmenes_eisodou.equals("1") || syntetagmenes_eisodou.equals("0")){
                System.out.println("la8os syntetagmenes eisodou");
                System.exit(0);
            }

            StringStackImpl stack = new StringStackImpl();


            

            int currentGrammh = syntetagmenh_grammhs_eisodou;
            int currentSthlh = syntetagmenh_sthlhs_eisodou;

            String tempo = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(0);

            stack.push(tempo);
            //System.out.println(tempo);
            while(!stack.isEmpty()){

                tempo = stack.peek();
                String [] tempo_array = tempo.split(" ");
                int d = Integer.valueOf(tempo_array[2]);
                currentGrammh = Integer.valueOf(tempo_array[0]);
                currentSthlh = Integer.valueOf(tempo_array[1]);


                if(maze[currentGrammh][currentSthlh].equals("0") && d == 0){
                    if(currentGrammh - 1 < 0 || currentGrammh + 1 >= ari8mos_grammwn || currentSthlh - 1 < 0 || currentSthlh + 1 >= ari8mos_sthlwn){
                        String [] lysh = tempo.split(" ");
                        System.out.println("Brhkes thn e3odo kai einai sthn grammh " + lysh[0] + " kai sthlh " + lysh[1] + " !");
                        System.exit(0);
                    }
                }
                

                if(d == 0){//PANW
                    if(currentGrammh - 1 >= 0 && Integer.parseInt(maze[currentGrammh - 1][currentSthlh]) == 0){
                        String tempo2 = Integer.toString(currentGrammh - 1) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(0);
                        maze[currentGrammh][currentSthlh] = Integer.toString(1);
                        stack.push(tempo2);
                    }
                    else{
                        String tempo2 = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(1);
                        stack.pop();
                        stack.push(tempo2);
                    }
                }

                else if(d == 1){//DE3IA
                    if(currentSthlh + 1 < ari8mos_sthlwn && Integer.parseInt(maze[currentGrammh][currentSthlh + 1]) == 0){
                        String tempo2 = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh + 1) + " " + Integer.toString(0);
                        maze[currentGrammh][currentSthlh] = Integer.toString(1);
                        stack.push(tempo2);
                    }
                    else{
                        String tempo2 = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(2);
                        stack.pop();
                        stack.push(tempo2);
                    }
                }

                else if(d == 2){//KATW
                    if(currentGrammh + 1 < ari8mos_grammwn && Integer.parseInt(maze[currentGrammh + 1][currentSthlh]) == 0){
                        String tempo2 = Integer.toString(currentGrammh + 1) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(0);
                        maze[currentGrammh][currentSthlh] = Integer.toString(1);
                        stack.push(tempo2);
                    }
                    else{
                        String tempo2 = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(3);
                        stack.pop();
                        stack.push(tempo2);
                    }
                }

                else if(d == 3){//ARISTERA
                    if(currentSthlh - 1 >= 0 && Integer.parseInt(maze[currentGrammh][currentSthlh - 1]) == 0){
                        String tempo2 = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh - 1) + " " + Integer.toString(0);
                        maze[currentGrammh][currentSthlh] = Integer.toString(1);
                        stack.push(tempo2);
                    }
                    else{
                        maze[currentGrammh][currentSthlh] = Integer.toString(1);
                        stack.pop();
                    }
                }

                

            }
            if(stack.isEmpty()){
                System.out.println("Den yparxei e3odos, rip!");
                System.exit(0);
            }
            buffer.close();

        }

        catch(Exception e){
            throw e;
        }
        

    }
}