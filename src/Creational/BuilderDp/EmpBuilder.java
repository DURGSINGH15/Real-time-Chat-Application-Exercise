package Creational.BuilderDp;

public class EmpBuilder {
    private String first_name;//mandatory attribute
    private String last_name;//mandatory attribute
    private int age;//optional attribute
    private String phone;//optional attribute
    private String address;//optional attribute

    public EmpBuilder(String first_Name, String last_Name) {
        this.first_name = first_Name;
        this.last_name = last_Name;
    }
    public EmpBuilder setage(int age) {
        this.age = age;
        return this;
    }
    public EmpBuilder setphone(String phone) {
        this.phone = phone;
        return this;
    }
    public EmpBuilder setaddress(String address) {
        this.address = address;
        return this;
    }
    public Emp build() {
        return new Emp(first_name,last_name,age,phone,address);
    }
}

