package realms.c7j.net.realmsampleapp.gsontest;

/**
 * Created by Ivan.Zh on Q1 2016.
 */
public class SomeItem {

    private String name;
    private int number;

    public SomeItem(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String out = "name: " + name + ", number: " + number;
        return out;
    }
}
