package cloud;

import java.rmi.Remote; import java.rmi.RemoteException;
import java.util.ArrayList;

import person.Person;

public interface Cloud extends Remote {
    public static String NAME = "Cloud";

    public void receiveData(int data, int verificationObject) throws
        RemoteException;

    // Returns result + VO with byzantine accuracy
    public String queryPartition(String query, int randomSeed) throws
        RemoteException;

    public ArrayList<Person> queryNonce(String query, int randomSeed, int sampleSize) throws RemoteException;
}
