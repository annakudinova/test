package gym.model;

import java.util.Date;

public class Payment  {
    private Client client;
    private Membership membership;
    private Date date;
    private int sum;

    public Payment(Client client, Membership membership, Date date, int sum) {
        this.client = client;
        this.membership = membership;
        this.date = date;
        this.sum = sum;
    }
    public Payment(){

    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

}
