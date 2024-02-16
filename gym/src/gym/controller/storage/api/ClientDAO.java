package gym.controller.storage.api;

import gym.model.Client;
import gym.model.Membership;
import gym.model.Trainer;
import gym.model.Visit;
import gym.util.GymException;

import java.util.List;

public interface ClientDAO extends DAO<Client, Long> {
    List<Client> findByNameAndSurname(String name, String surname) throws GymException;

    Client findByPhone(String phone) throws GymException;

    boolean addTrainer(Client client, Trainer trainer) throws GymException;

    boolean addMembership(Client client, Membership membership, int cost) throws GymException;

    boolean deleteMembership(Client client, Membership membership) throws GymException;

    boolean deleteTrainer(Client client, Trainer trainer) throws GymException;

    Long findTrainer(Client client) throws GymException;

    Long findMembership(Client client) throws GymException;

    Integer findRemainedClasses(Client client) throws GymException;

    Integer showMembershipBalance(Client client) throws GymException;

    List<Client> showClientWithHighestNumberOfVisits()throws GymException;
    boolean changeMembership(long clientId, long oldMembershipId, long newMembershipId, int cost) throws GymException;


}
