package person;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Person implements Serializable {
    private static final long serialVersionUID = -3909629844439352088L;
    public Integer id;
    public String name;
    public Integer salary;
    public Integer department_id;
    public static final Set<String> ALL_KEYS = new HashSet<String>();

    static {
        ALL_KEYS.add("id");
        ALL_KEYS.add("name");
        ALL_KEYS.add("salary");
        ALL_KEYS.add("department_id");
    }

    @Override
    public String toString() {
        return this.id + " " + this.name;
    }
}
