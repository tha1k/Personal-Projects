import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class Thiseas{   
    
    public static void main (String[] args) throws Exception{
        
        String file= args[0];
        try{
            BufferedReader buffer = new BufferedReader(new FileReader(file));//pernei san eisodo to arxeio apo to cmd
            String line;
            line = buffer.readLine();
            int ari8mos_grammwn;
            int ari8mos_sthlwn;
            String diastaseis[] = line.split(" ");//spaei to line sta kena
            ari8mos_grammwn = Integer.parseInt(diastaseis[0]);//distaseis pinaka (grammes)
            ari8mos_sthlwn = Integer.parseInt(diastaseis[1]);//diastaseis pinaka (sthles)

            line = buffer.readLine();
            int syntetagmenh_grammhs_eisodou;
            int syntetagmenh_sthlhs_eisodou;
            String eisodos[] = line.split(" ");//spaei to line sta kena
            syntetagmenh_grammhs_eisodou = Integer.parseInt(eisodos[0]);
            syntetagmenh_sthlhs_eisodou = Integer.parseInt(eisodos[1]);

            String[][] maze = new String[ari8mos_grammwn][ari8mos_sthlwn];//dhmiourgeia tou maze
            int grammh = 0;
            
            while(grammh < ari8mos_grammwn){//eisagwgh stoixeiwn sto maze
                line = buffer.readLine();
                maze[grammh] = line.split(" ");
                grammh++;
            }


            String syntetagmenes_eisodou = maze[syntetagmenh_grammhs_eisodou][syntetagmenh_sthlhs_eisodou];//stoixeio stis grammes kai sthles pou exoun dw8ei ws eisodos

            if(syntetagmenes_eisodou.equals("1") || syntetagmenes_eisodou.equals("0")){//an sthn eisodo den exei E bgazei la8os
                System.out.println("La8os syntetagmenes eisodou!");
                System.exit(0);
            }

            StringStackImpl stack = new StringStackImpl();//stoiba


            

            int currentGrammh = syntetagmenh_grammhs_eisodou;
            int currentSthlh = syntetagmenh_sthlhs_eisodou;

            String tempo = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(0);//temporary string

            stack.push(tempo);
 
            while(!stack.isEmpty()){//kanei tis kinhseis

                tempo = stack.peek();//kanei to tempo to teleutaio stoixeio ths stack
                String [] tempo_array = tempo.split(" ");// to spaei sta kena
                int d = Integer.valueOf(tempo_array[2]);//kateu8hnsh kinhshs (0=panw/ 1=de3ia/ 2=katw/ 3=aristera)
                currentGrammh = Integer.valueOf(tempo_array[0]);//se poia grammh briskete to tempo
                currentSthlh = Integer.valueOf(tempo_array[1]);//se poia sthlh briskete to tempo


                if(maze[currentGrammh][currentSthlh].equals("0") && d == 0){//checkarei an exeis brei thn eisodo
                    if(currentGrammh - 1 < 0 || currentGrammh + 1 >= ari8mos_grammwn || currentSthlh - 1 < 0 || currentSthlh + 1 >= ari8mos_sthlwn){
                        String [] lysh = tempo.split(" ");
                        System.out.println("Brhkes thn e3odo kai einai sthn grammh " + lysh[0] + " kai sthlh " + lysh[1] + "!");
                        System.exit(0);
                    }
                }
                

                if(d == 0){//PANW
                    if(currentGrammh - 1 >= 0 && Integer.parseInt(maze[currentGrammh - 1][currentSthlh]) == 0){//an mporei proxoraei kai kanei push thn kainouria 8esh sto stack
                        String tempo2 = Integer.toString(currentGrammh - 1) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(0);
                        maze[currentGrammh][currentSthlh] = Integer.toString(9);//an perasame to kanei 9
                        stack.push(tempo2);
                    }
                    else{//alliws petaei to prohgoumeno kai allazei thn kateu8ynsh kinhshs mesa apo to d
                        String tempo2 = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(1);
                        stack.pop();
                        stack.push(tempo2);
                    }
                }

                else if(d == 1){//DE3IA
                    if(currentSthlh + 1 < ari8mos_sthlwn && Integer.parseInt(maze[currentGrammh][currentSthlh + 1]) == 0){//an mporei proxoraei kai kanei push thn kainouria 8esh sto stack
                        String tempo2 = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh + 1) + " " + Integer.toString(0);
                        maze[currentGrammh][currentSthlh] = Integer.toString(9);//an perasame to kanei 9
                        stack.push(tempo2);
                    }
                    else{//alliws petaei to prohgoumeno kai allazei thn kateu8ynsh kinhshs mesa apo to d
                        String tempo2 = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(2);
                        stack.pop();
                        stack.push(tempo2);
                    }
                }

                else if(d == 2){//KATW
                    if(currentGrammh + 1 < ari8mos_grammwn && Integer.parseInt(maze[currentGrammh + 1][currentSthlh]) == 0){//an mporei proxoraei kai kanei push thn kainouria 8esh sto stack
                        String tempo2 = Integer.toString(currentGrammh + 1) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(0);
                        maze[currentGrammh][currentSthlh] = Integer.toString(9);//an perasame to kanei 9
                        stack.push(tempo2);
                    }
                    else{//alliws petaei to prohgoumeno kai allazei thn kateu8ynsh kinhshs mesa apo to d
                        String tempo2 = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh) + " " + Integer.toString(3);
                        stack.pop();
                        stack.push(tempo2);
                    }
                }

                else if(d == 3){//ARISTERA
                    if(currentSthlh - 1 >= 0 && Integer.parseInt(maze[currentGrammh][currentSthlh - 1]) == 0){//an mporei proxoraei kai kanei push thn kainouria 8esh sto stack
                        String tempo2 = Integer.toString(currentGrammh) + " " + Integer.toString(currentSthlh - 1) + " " + Integer.toString(0);
                        maze[currentGrammh][currentSthlh] = Integer.toString(9);//opoio perasame to kanei 9
                        stack.push(tempo2);
                    }
                    else{//an den mporei kai sthn teleutaia kinhsh apla to petaei apto stack, kanei thn 8esh 1(toixo) kai checkarei to prohgoumeno stoixeio ths stoibas
                        maze[currentGrammh][currentSthlh] = Integer.toString(1);
                        stack.pop();
                    }
                }

                

            }
            if(stack.isEmpty()){//an den exoume brei e3odo kai h stoiba einai pleon adeia feygei apo to while kai sou petaei oti den exei e3odo
                System.out.println("Den yparxei e3odos!");
                System.exit(0);
            }
            buffer.close();

        }

        catch(Exception e){
            throw e;
        }
        

    }
}