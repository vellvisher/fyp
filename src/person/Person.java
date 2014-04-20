package person;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = -3909629844439352088L;
    public Integer id;
    public String name;
    public Integer salary;
    public Integer department_id;

    @Override
    public String toString() {
        return this.id + " " + this.name;
    }
}
