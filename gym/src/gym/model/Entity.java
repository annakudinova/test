package gym.model;


public abstract class Entity<E, K> {
    private K id;

    public Entity(K id) {
        this.id = id;
    }
    public Entity(){

    }

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
       Entity entity = (Entity) object;
        if (entity.id.equals(id)) {
            return true;
        }
        return false;
    }
    public int hashCode(){
        int a = 13;
        a = 13 *a + id.hashCode();
        return a;
    }
    public String toString(){
        return getClass().getSimpleName() + " Id = " + id;
    }
}
