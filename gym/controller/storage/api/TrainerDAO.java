package gym.controller.storage.api;

import gym.model.Trainer;
import gym.util.GymException;

import java.util.List;

public interface TrainerDAO extends DAO<Trainer, Long>{
    List<Trainer> findByNameAndSurname(String name, String surname) throws GymException;
    List<Trainer> findByCategory(long category) throws GymException;
    List<Trainer>findByDirection(String direction) throws GymException;
    List<Trainer>findAll() throws GymException;
    List<Long>findClients(Trainer trainer) throws GymException;
}
