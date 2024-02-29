package gym.model;

import java.sql.Date;

public class Trainer extends Entity<Trainer, Long> {
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String description;
    private Category category;
    private String direction;
    private int cost;
    private String phone;

    public Trainer(String name, String surname, Date dateOfBirth, String description, Category category, String direction, int cost, String phone) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.category = category;
        this.direction = direction;
        this.cost = cost;
        this.phone = phone;
    }

    public Trainer(Long id) {
        super(id);
    }

    public Trainer() {

    }

    public Trainer(Long id, String name, String surname, Date dateOfBirth, String description, Category category, String direction, int cost, String phone) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.category = category;
        this.direction = direction;
        this.cost = cost;
        this.phone = phone;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return super.toString() + " Name: " + name + " Surname: " + surname + " Date of birth: " + dateOfBirth + " Description: " + description + " Category: " + category + " Direction: " + direction + " Cost: " + cost + " Phone: " + phone + "; " + " \n ";

    }


}
