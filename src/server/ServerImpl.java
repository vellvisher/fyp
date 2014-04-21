package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import person.Person;

public class ServerImpl implements Server {
    private ArrayList<ArrayList<Person>> partitions;
    private DBManager db;

    public ServerImpl() {
        connectToDatabase();
        /* generatePartitions(); */
    }

    private void verifyPartitions() {
        db.verifyPartitions(K, TOTAL/K);
    }

    @Override
    public void generateData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void generateVO() {

    }

    @Override
    public void getVO(int partitionId) {
        // TODO Auto-generated method stub

    }

    public void generatePartitions() {
        System.out.println("Generating partitions...");
        ArrayList<Integer> data = new ArrayList<Integer>();
        for (int i = 1; i <= TOTAL; i++) {
            data.add(i);
        }
        Collections.shuffle(data);
        int id = 0;
        int partition_id = 1;
        int partSize = TOTAL/K;
        for (int i = 1; i <= TOTAL; i++) {
            id = data.get(i-1);
            db.executeUpdate(String.format("UPDATE persons SET partition_id = %d WHERE id = %d",
                    partition_id, id));
            if (i % partSize == 0) {
                System.out.println(i);
                partition_id++;
            }
        }
        System.out.println("Generated partitions...");
    }

    private void connectToDatabase() {
        db = new DBManager("ransam");
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
            e.printStackTrace();
        }
    }

    @Override
    public Object getPartitions() {
        return this.partitions;
    }

    @Override
    public ArrayList<Person> getPartition(Integer partitionId) throws RemoteException {
        ArrayList<Person> partitionData = db.executeQuery(
                "SELECT * from persons WHERE partition_id=" + partitionId);
        for (Person p : partitionData) {
            // Do some transformation thing to encrypt data
            // TODO: Replace with better
            p.name = MD5(p.name);
        }

        return partitionData;
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
