package social_network.domain;

import java.util.Objects;

/**
 * Class representing User object
 */
public class User extends Entity<Long> {
    /**
     * First name of the user
     */
    private String firstName;
    /**
     * Last name of the user
     */
    private String lastName;

    /**
     * Age of user
     */
    private Integer age;
    private String username;
    private String password;

    /**
     * Constructor of the User
     *
     * @param firstName - first name of User
     * @param lastName  - last name of User
     */
    public User(String firstName, String lastName, Integer age, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters for username and password
    public String getUsername() {
        return username;
    }
    //getters and setters for username and password
    public void setUsername(String username) {
        this.username = username;
    }
    //getters and setters for username and password
    public String getPassword() {
        return password;
    }
    //getters and setters for username and password
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    /**
     * Get method for the first name
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set method for the first name
     *
     * @param firstName - new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get method for last name
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set method for last name
     *
     * @param lastName - new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * Method used to print a user
     *
     * @return printed user
     */
    @Override
    public String toString() {

        return "User {" +
                "ID='" + this.getId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    /**
     * Method that verifies if 2 users are the same
     *
     * @param o - user that is compared to
     * @return true if the objects are the same, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName());

    }

    /**
     * Method that return the hashCode of the User
     *
     * @return hashCode of the user
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }
}
