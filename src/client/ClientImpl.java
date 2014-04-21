package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;

import person.Person;
import cloud.Cloud;

public class ClientImpl implements Client {
    private static final String RMI_SERVER = null;
    private Cloud cloud;

    public ClientImpl() {
        connectToCloud();
        // TODO: Run for-loop doing queries over time
        try {
            /* queryPartition(); */
            queryNonce();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new ClientImpl();
    }

    public void connectToCloud() {
        try {
            String name = Cloud.NAME;
            Registry registry = LocateRegistry.getRegistry(RMI_SERVER);
            cloud = (Cloud) registry.lookup(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryPartition() throws RemoteException {
        int randomSeed = randomNumber();
        cloud.queryPartition("SELECT salary, department_id FROM `persons` WHERE salary BETWEEN 10000 AND 30000", randomSeed);
        cloud.queryPartition("SELECT department_id, sum(salary) FROM `persons` GROUP BY department_id", randomSeed);
        cloud.queryPartition("SELECT department_id, sum(salary) FROM `persons` GROUP BY department_id WHERE SALARY > 10000", randomSeed);
    }

    public void queryNonce() throws RemoteException {
        int randomSeed = randomNumber();
        int sampleSize = 10;
        ArrayList<Person> result = cloud.queryNonce(
                "SELECT salary, department_id FROM `persons` WHERE salary BETWEEN 10000 AND 30000",
                randomSeed, sampleSize);
        for (Person p : result) {
            System.out.println(p.salary);
        }
    }

    private static int randomNumber() {
        Random r = new Random();
        return r.nextInt(Integer.MAX_VALUE);
    }
}
