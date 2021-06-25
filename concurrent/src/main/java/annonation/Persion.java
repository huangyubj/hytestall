package annonation;

public class Persion {
    private String username;
    private int age;

    public Persion() {
    }

    public Persion(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Persion{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
