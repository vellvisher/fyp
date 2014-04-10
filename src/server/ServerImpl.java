package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl implements Server {

    @Override
    public void generateData() {
        // TODO Auto-generated method stub

    }

    @Override
    public void generateVO() {
        // TODO Auto-generated method stub

    }

    @Override
    public void partition() {
        // TODO Auto-generated method stub

    }

    @Override
    public String transmit() {
        return "ok";
    }

    public static void main(String args[]) {
        try {
            String name = Server.NAME;
            Server server = new ServerImpl();
            Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Running Server");
        } catch (RemoteException e) {
            System.err.println("Exception thrown");
            e.printStackTrace();
        }
    }
}
