package gym.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

public class Client extends Entity<Client, Long> {
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String phone;
    private String email;
    private byte[] photo;
    private Membership membership;
    private Trainer trainer;

    public Client(String name, String surname, Date dateOfBirth, String phone, String email, byte[] photo, Membership membership, Trainer trainer) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
        this.membership = membership;
        this.trainer = trainer;
    }

    public Client(Long id) {
        super(id);
    }

    public Client(Long id, String name, String surname, Date dateOfBirth, String phone, String email, byte[] photo, Membership membership, Trainer trainer) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
        this.membership = membership;
        this.trainer = trainer;
    }
    public Client(Long id, String name, String surname, Date dateOfBirth, String phone, String email){
        super(id);
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
    }
    public Client(Long id, String name, String surname, Date dateOfBirth, String phone, String email, byte[] photo) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
    }
    public Client() {
    }
    public Client(String name, String surname, Date dateOfBirth, String phone, String email, byte[] photo){
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
    }
    public Client (String name, String surname, String phone){
        this.name = name;
        this.surname = surname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
    public String toString(){
       return  super.toString() + " Name: " + name + " Surname: " + surname + " Date of birth: " + dateOfBirth + " Phone: " + phone + " Email: " + email + " Photo: " + photo + " \n ";
    }

}
