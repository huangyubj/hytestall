package annonation;

public class PersionDao {
    private Persion persion;

    @InjectPerson(username = "hy", age=30)
    public void setPersion(Persion persion) {
        this.persion = persion;
    }

    public Persion getPersion() {
        return persion;
    }

    @Override
    public String toString() {
        return "PersionDao{" +
                "persion=" + persion.toString() +
                '}';
    }
}
