package cloud;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import person.Person;

import server.DBManager;
import server.Server;

public class CloudImpl implements Cloud {

    private static final String RMI_SERVER = null;
    private static final int NUM_PARTITIONS = 1000;
    private Object partitions;
    private DBManager db;

    public CloudImpl() {
        connectToDatabase();
        connectToServer();
        startCloudServer();
    }

    public static void main(String args[]) {
        CloudImpl c = new CloudImpl();
    }

    private void connectToDatabase() {
        db = new DBManager("ransam_cloud");
    }

    private void startCloudServer() {
        try {
            String name = Cloud.NAME;
            Cloud server = this;
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
            this.partitions = server.getPartitions();
            for (Person p : server.getPartition(5)) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveData(int data, int verificationObject) {
        // TODO Auto-generated method stub

    }

    @Override
    public String query(String query) {
        return "cloud ok";
    }

    @Override
    public String query(String query, int randomSeed) throws RemoteException {
        db.executeQuery(query);
        Integer partitionId = randomSeed % Server.K;
        // TODO Auto-generated method stub
        // Get data for supplied partition
        // Perform query on supplied partition
        // Return VO for partition and result
        return null;
    }

}
