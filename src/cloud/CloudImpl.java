package cloud;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
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
        // TODO: Change to cloud
        /* db = new DBManager("ransam_big_cloud"); */
        db = new DBManager("ransam_big");
        System.out.println("Connected to database...");
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
            /* for (Person p : server.getPartition(5)) { */
            /*     System.out.println(p); */
            /* } */
            System.out.println("Connected to server...");
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
        ArrayList<Integer> idListAdditive = nonceToIdsAdditive(randomSeed, sampleSize);
        ArrayList<Integer> idListHash = nonceToIdsHash(randomSeed, sampleSize);
        ArrayList<Integer> idListRNG = nonceToIdsRNG(randomSeed, sampleSize);
        String queryAdditive = generateQuery(idListAdditive);
        String queryHash = generateQuery(idListHash);
        String queryNonce = generateQuery(idListRNG);
        System.out.println(queryAdditive);
        System.out.println(queryHash);
        System.out.println(queryNonce);
        Set<String> tempSet = new HashSet<String>();
        tempSet.add("id");
        tempSet.add("salary");
        return db.executeQuery(queryAdditive, tempSet);
    }

    private String generateQuery(ArrayList<Integer> idList) {
        StringBuilder sb = new StringBuilder(idList.size()*3);
        sb.append("SELECT id, salary FROM persons where id in (");
        sb.append(idList.get(0));

        for (int i = 1; i < idList.size(); i++) {
            sb.append(",").append(idList.get(i));
        }
        sb.append(")");
        return sb.toString();
    }

    private ArrayList<Integer> nonceToIdsRNG(int randomSeed, int sampleSize) {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        Random r = new Random(randomSeed);
        idList.add((r.nextInt(Integer.MAX_VALUE) % Server.TOTAL) + 1);
        for (int i = 1; i < sampleSize; i++) {
            idList.add((r.nextInt(Integer.MAX_VALUE) % Server.TOTAL) + 1);
        }
        return idList;
    }

    private ArrayList<Integer> nonceToIdsHash(int randomSeed, int sampleSize) {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        idList.add(transformHash(randomSeed));
        for (int i = 1; i < sampleSize; i++) {
            idList.add(transformHash(idList.get(i - 1), randomSeed));
        }
        return idList;
    }

    private Integer transformHash(int i) {
        return (new BigInteger(MD5(i+""), 16)).mod(BigInteger.valueOf(Server.TOTAL)).intValue() + 1;
    }

    private Integer transformHash(int i, int j) {
        return (new BigInteger(MD5(i + "" + j), 16)).mod(BigInteger.valueOf(Server.TOTAL)).intValue() + 1;
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

    private ArrayList<Integer> nonceToIdsAdditive(int randomSeed, int sampleSize) {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        idList.add(transformAdditive(randomSeed));
        for (int i = 1; i < sampleSize; i++) {
            idList.add(transformAdditive(idList.get(i - 1) + randomSeed));
        }
        return idList;
    }

    private Integer transformAdditive(int i) {
        return (i % Server.TOTAL) + 1;
    }

}
