package gym.controller.storage.api;

import gym.model.Entity;
import gym.util.GymException;

public interface DAO <E extends Entity<E, K>,K>{
    E save(E e) throws GymException;
    E update(E e)  throws GymException;
    E delete(E e)  throws GymException;
    E find(K k)  throws GymException;

}
