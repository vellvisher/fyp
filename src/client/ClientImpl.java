package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.Server;

class ClientImpl implements Client {
    public static void main(String args[]) {
        try {
            String name = Server.NAME;
            Registry registry = LocateRegistry.getRegistry(args[0]);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
