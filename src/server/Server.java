package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.ArrayList;

import person.Person;

public interface Server extends Remote {
    String NAME = "Server";
    public static final Integer K = 100;
    public static final Integer TOTAL = 100000000;
    public void generateData() throws RemoteException;
    public void generateVO() throws RemoteException;
    public Object getPartitions() throws RemoteException;
    public ArrayList<Person> getPartition(Integer partitionId) throws RemoteException;
    void getVO(int partitionId) throws RemoteException;
}
