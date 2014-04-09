package cloud;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import server.Server;
import server.ServerImpl;

class CloudImpl implements Cloud {

    private static final String RMI_SERVER = null;

    public CloudImpl() {
    }

    public static void main(String args[]) {
        CloudImpl c = new CloudImpl();
        c.initialize();
    }

    private void startCloudServer() {
        try {
            String name = Cloud.NAME;
            Cloud server = new CloudImpl();
            Cloud stub = (Cloud) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Running Cloud Server...");
        } catch (RemoteException e) {
            System.err.println("Exception thrown");
            e.printStackTrace();
        }
    }

    private void connectToServer() {
        try {
            String name = Server.NAME;
            Registry registry = LocateRegistry.getRegistry(RMI_SERVER);
            Server server = (Server) registry.lookup(name);
            System.out.println(server.transmit());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        connectToServer();
        startCloudServer();
    }

    @Override
    public void receiveData(int data, int verificationObject) {
        // TODO Auto-generated method stub

    }

    @Override
    public String query(String query) {
        return "cloud ok";
    }

}
