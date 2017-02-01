package usecases.merger;

/**
 * Created by sivag on 31/1/17.
 */
public class Employee {

    private long code;
    private String name,department;
    private long phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (getCode() != employee.getCode()) return false;
        return getDepartment() != null ? getDepartment().equals(employee.getDepartment()) : employee.getDepartment() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getCode() ^ (getCode() >>> 32));
        result = 31 * result + (getDepartment() != null ? getDepartment().hashCode() : 0);
        return result;
    }
}
