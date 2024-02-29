package gym.model;

import java.sql.Date;

public class Visit extends Entity<Visit, Long> {
    private Client client;
    private Date date;
    private int lockerNumber;

    public Visit(Long id, Client client, Date date, int lockerNumber) {
        super(id);
        this.client = client;
        this.date = date;
        this.lockerNumber = lockerNumber;
    }

    public Visit(Client client, Date date, int lockerNumber) {
        this.client = client;
        this.date = date;
        this.lockerNumber = lockerNumber;
    }
    public Visit(Long id){
        super(id);
    }
    public Visit(){

    }
    public Visit(Client client){
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLockerNumber() {
        return lockerNumber;
    }

    public void setLockerNumber(int lockerNumber) {
        this.lockerNumber = lockerNumber;
    }

    public String toString(){
        return super.toString() + " date " + date + " locker number " + lockerNumber + " \n ";
    }
}
