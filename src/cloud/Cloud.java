package cloud;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Cloud extends Remote {
    public static String NAME = "Cloud";
    public void receiveData(int data, int verificationObject) throws RemoteException;
    // Returns result + VO with byzantine accuracy
    public String query(String query) throws RemoteException;
    public String query(String query, int randomSeed) throws RemoteException;
}
