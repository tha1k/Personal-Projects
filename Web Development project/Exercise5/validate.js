//Check if phone number matches country code
function CheckPhoneNumber(){
    const constraints ={
        us:[ "^\\+1(\\s?\\d{10})$","US format +1 1231231231"],
        uk: ["^\\+44(\\s?\\d{10})$","UK format +44 1231231231" ],
        gr: ["^\\+30(\\s\\d{10})$","GR format +30 1231231231" ]
    }

    const country = document.getElementById("country").value;
    const phoneField = document.getElementById("phone");

    const constraint = new RegExp(constraints[country][0])
    if( constraint.test(phoneField.value)){
         // If valid, clear any previous error messages
        phoneField.setCustomValidity("");
    }else{
        phoneField.setCustomValidity(constraints[country][1]);
    }  

}
function checkAdult(){
    const dob_elem = document.getElementById("dob"); 
    const dob_date = new Date(dob_elem.value);
    let age = new Date().getFullYear() - dob_date.getFullYear();
    const today = new Date();

    const birthdayHasNOTPassedThisYear =
    today.getMonth() > dob_date.getMonth() ||
    (today.getMonth() === dob_date.getMonth() && today.getDate() >= dob_date.getDate());

  if (!birthdayHasNOTPassedThisYear) {
    age = age-1;
  }

    if (age>=18){
        dob_elem.setCustomValidity("");
    }else{
        dob_elem.setCustomValidity("User must be over 18");
    }

}

function CheckPasswordMatch(){
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirmPassword");

    if(password.value !== confirmPassword.value){
        confirmPassword.setCustomValidity("Password does not match");

    }else{
        confirmPassword.setCustomValidity("");
    }

}



window.onload = () =>{

    //check password verification match
    document.getElementById("password").oninput = CheckPasswordMatch;
    document.getElementById("confirmPassword").oninput = CheckPasswordMatch;


    //check user is adult 
    document.getElementById("dob").addEventListener("change", checkAdult);

    //check phone num test 3
    document.getElementById("country").onchange = CheckPhoneNumber;
    document.getElementById("phone").oninput = CheckPhoneNumber;

}
  