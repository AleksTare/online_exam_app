package user;

public class User {

    private int id;

    private String name;

    private String surname;

    private String username;

    private String password;

    private Role role;

    public User(String name, String surname, String username, String password, Role roleId) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.role = roleId;
    }

    public User(int id, String name, String surname, String username, String password, Role roleId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.role = roleId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return name +" "+surname;
    }
}
