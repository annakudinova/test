package gym.controller.storage.api;

import gym.model.Membership;
import gym.util.GymException;

import java.util.List;

public interface MembershipsDAO extends DAO<Membership, Long>{
    List<Membership> findAll() throws GymException;
    List<Membership>findByTitle(String title) throws GymException;
}
