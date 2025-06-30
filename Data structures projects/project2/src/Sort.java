public class Sort{
    
    static void Switch(int[] array, int mikro, int megalo){//alagh 8eshs anamesa sta 2 stoixeia
        int temp = array[mikro];
        array[mikro] = array[megalo];
        array[megalo] = temp;
    }

     static int partition(int[] array, int mikro, int megalo){
        int i = mikro -1, j = megalo;//deikths i(8eshs mikroterou stoixeiou) deikths j(megalyterou stoixeiou)
        int p = array[megalo];//pivot
        for(;;){
            while(p < array[++i]);//oso to pivot mikrotero tou mikroterou stoixeiou
                while(array[--j] < p)//oso to pivot megalutero tou megalyterou stoixeiou
                    if (j == mikro)break;//an ftasei to j sthn akrh apo aristera break
                if(i >= j)break;//an to i 3eperasei to j break
                Switch(array, i, j);//alla3e ta stoixeia
        }
        Switch(array, i, megalo);//alla3e kai to pivot
        return i;//return thn 8esh pou egine to partition
    }

    public static void QuickSort(int[] array, int mikro, int megalo){
        if(mikro < megalo){//an ginetai na kaneis sort ton pinaka
            int thesh_allaghs = partition(array, mikro, megalo);//8esh allaghs einai to shmeio pou egine partition
            QuickSort(array, mikro, thesh_allaghs - 1);//quicksort ton pinaka aristera 
            QuickSort(array, thesh_allaghs + 1, megalo);//quicksort ton pinaka de3ia
        }
    }

}