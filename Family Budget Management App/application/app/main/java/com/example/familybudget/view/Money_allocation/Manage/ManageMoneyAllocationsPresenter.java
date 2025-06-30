package com.example.familybudget.view.Money_allocation.Manage;



import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILYMEMBER_ID;
import static com.example.familybudget.view.Family.Login.LoginPresenter.FAMILY_ID;

import com.example.familybudget.Dao.Family_DAO;
import com.example.familybudget.Dao.Family_Member_DAO;
import com.example.familybudget.Dao.Piggy_Bank_DAO;
import com.example.familybudget.DaoMemory.Family_DAOMemory;
import com.example.familybudget.DaoMemory.Family_Member_DAOMemory;
import com.example.familybudget.DaoMemory.Piggy_bank_DAOMemory;
import com.example.familybudget.Family;
import com.example.familybudget.Family_Member;
import com.example.familybudget.Money_alloc;
import com.example.familybudget.Piggy_bank;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageMoneyAllocationsPresenter {
    private ManageAllocationsView view;
    private Family_Member_DAO fmd;
    private Family_DAO fd;
    private Piggy_Bank_DAO pdg;
    private int bank_id;
    private Piggy_bank pg;
    private List<Money_alloc> allocs ;

   public void setView(ManageAllocationsView v){
       view=v;
       fmd= new Family_Member_DAOMemory();
       pdg= new Piggy_bank_DAOMemory();
       fd= new Family_DAOMemory();
       bank_id=-1;
   }
   public void setId(int id){
       this.bank_id=id;
       pg = pdg.find(bank_id);


   }
   public List<Money_alloc> get_allocations_list(){

       allocs= pg.get_allocations();
       return allocs;
   }
   /**filter allocations list based on the input dates
    * @param start
    * @param end
    * @return dates between start and end
    * */
   public List<Money_alloc> filter_allocs(String start, String end){

       String start_formated=start.replace("/", "-");
       String end_formated = end.replace("/","-");
       LocalDate startdate= LocalDate.parse(start_formated.trim());
       LocalDate enddate= LocalDate.parse(end_formated.trim());

       allocs= pg.get_allocation_for(startdate,enddate);
       return allocs;
   }
   /**validates that dates are inputed in the YYYY-MM-dd format
    * @param date1 is a string representing a date eg 2000/01/01 not 2000/1/1
    * */
   public static boolean validate_format(String date1){
       String regex = "\\d{4}[-/]\\d{2}[-/]\\d{2}";
       Pattern pattern = Pattern.compile(regex);
       Matcher matcher = pattern.matcher(date1);
       return matcher.matches();
   }
   /**Calls the view see
    * {@link  com.example.familybudget.view.Money_allocation.Manage.ManageMoneyAllocationsActivty#addMoneyAlloc(int)}
    * passing in the id of the selected piggy bank
    * */
   public void add_alloc(){
       Family_Member member= fmd.find(FAMILYMEMBER_ID);
       Family f= fd.find(FAMILY_ID);
       if(f.getProtector().equals(member) && member.Is_protector()){
        view.addMoneyAlloc(this.bank_id);
       }else{
           view.Show_message("Only guardian accounts can allocate");
       }
   }

}
