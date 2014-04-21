package cloud;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Set;

import person.Person;

import server.DBManager;
import server.Server;

public class CloudImpl implements Cloud {

    private static final String RMI_SERVER = null;
    private static final int NUM_PARTITIONS = 1000;
    private Object partitions;
    private DBManager db;
    // TODO: Probobly should not be a field
    private Server server;

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
            server = (Server) registry.lookup(name);
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
    public String queryPartition(String query, int randomSeed)
            throws RemoteException {
        // TODO: Fix exception
        /* db.executeQuery(query); */
        Integer partitionId = (randomSeed % Server.K) + 1; // Prevent partitionId 0
        for (Person p : server.getPartition(partitionId)) {
            System.out.println(p);
        }
        // Get data for supplied partition
        // Perform query on supplied partition
        // Return VO for partition and result
        return null;
    }

    @Override
    public ArrayList<Person> queryNonce(String query, int randomSeed,
            int sampleSize) throws RemoteException {
        ArrayList<Integer> idList = nonceToIds(randomSeed, sampleSize);
        StringBuilder sb = new StringBuilder(sampleSize*3);
        sb.append("SELECT id, salary FROM persons where id in (");
        sb.append(idList.get(0));

        for (int i = 1; i < idList.size(); i++) {
            sb.append(",").append(idList.get(i));
        }
        sb.append(")");
        System.out.println(sb);
        Set<String> tempSet = new HashSet<String>();
        tempSet.add("id");
        tempSet.add("salary");
        return db.executeQuery(sb.toString(), tempSet);
    }

    private ArrayList<Integer> nonceToIds(int randomSeed, int sampleSize) {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        idList.add(transform(randomSeed));
        for (int i = 1; i < sampleSize; i++) {
            idList.add(transform(idList.get(i - 1) + randomSeed));
        }
        return idList;
    }

    private Integer transform(int i) {
        return i % Server.TOTAL;
    }

}
