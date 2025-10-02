package Creational.BuilderDp;

public class Emp {
    private String first_name;//mandatory attribute
    private String last_name;//mandatory attribute
    private int age;//optional attribute
    private String phone;//optional attribute
    private String address;//optional attribute

    Emp(String first_name,String last_name,int age,String phone,String address) {
        this.first_name=first_name;
        this.last_name=last_name;
        this.age=age;
        this.phone=phone;
        this.address=address;
    }

    public String toString() {
        return "Employee:" +this.first_name+","+ this.last_name+ ", " +this.age+ ", "+this.phone+", "+this.address;
    }
}
