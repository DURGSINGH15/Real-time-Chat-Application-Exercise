package Creational.BuilderDp;

public class EmpClient {
    public static void main(String[] args) {

        Emp e1= new Emp("Anil","Kumar",34,"9106xxxxxx","ABC nagar");
        System.out.println(e1);

        Emp e2= new EmpBuilder("Guddu","Tripathi").build();
        Emp e3= new EmpBuilder("Bruce","Wayne").setage(35).setaddress("Gotham city").build();
        System.out.println("Using builder class:");
        System.out.println(e2);
        System.out.println(e3);
    }
}

