package gym.model;

public class Membership extends Entity<Membership, Long>{
private String title;
private String description;
private int number;
private int cost;
private int duration;

public Membership(String title, String description, int number,int cost, int duration){
    this.title = title;
    this.description = description;
    this.number = number;
    this.cost = cost;
    this.duration = duration;
}
    public Membership(Long id){
        super(id);
    }
    public Membership(Long id, String title, String description, int number,int cost, int duration){
        super(id);
        this.title = title;
        this.description = description;
        this.number = number;
        this.cost = cost;
        this.duration = duration;
    }
    public Membership(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String toString(){
    return super.toString() + " title: " + title + " description: " + description + " number: " + number + " cost: " + cost + " duratin: " + duration + " \n";
    }

}
