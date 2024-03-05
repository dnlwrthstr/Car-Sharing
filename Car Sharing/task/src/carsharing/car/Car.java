package carsharing.car;

public class Car {
    int id;
    String name;
    int companyId;

    public Car(String name, int companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public Car(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompanyId() {
        return companyId;
    }

}
