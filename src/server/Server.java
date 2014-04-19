package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    String NAME = "Server";
    public void generateData() throws RemoteException;
    public void generateVO() throws RemoteException;
    public String transmit() throws RemoteException;
    public Object getPartitions() throws RemoteException;
}
