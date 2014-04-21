package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.Random;

import cloud.Cloud;

public class ClientImpl implements Client {
    private static final String RMI_SERVER = null;
    private Cloud cloud;

    public ClientImpl() {
        connectToCloud();
        // TODO: Run for-loop doing queries over time
        try {
            query();
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

    public void query() throws RemoteException {
        int randomSeed = randomNumber();
        cloud.query("SELECT salary, department_id FROM `persons` WHERE salary BETWEEN 10000 AND 30000", randomSeed);
        cloud.query("SELECT department_id, sum(salary) FROM `persons` GROUP BY department_id", randomSeed);
        cloud.query("SELECT department_id, sum(salary) FROM `persons` GROUP BY department_id WHERE SALARY > 10000", randomSeed);
    }

    private static int randomNumber() {
        Random r = new Random();
        return r.nextInt(Integer.MAX_VALUE);
    }
}
