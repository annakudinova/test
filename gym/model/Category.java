package gym.model;

public class Category extends Entity<Category, Long>{
    private String title;

    public Category(Long id, String title) {
        super(id);
        this.title = title;
    }

    public Category(String title) {
        this.title = title;
    }

    public Category(){

    }
    public Category(Long id){
        super(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String toString(){
        return super.toString() + " title " + title + " \n ";
    }
}
