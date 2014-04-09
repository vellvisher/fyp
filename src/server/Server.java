package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    String NAME = "Server";
    public void generateData() throws RemoteException;
    public void partition() throws RemoteException;
    public void generateVO() throws RemoteException;
    public void transmit() throws RemoteException;
}
