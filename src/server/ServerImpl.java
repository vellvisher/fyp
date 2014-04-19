package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import person.Person;

public class ServerImpl implements Server {
    public static final Integer K = 100;
    public static final Integer TOTAL = 1000000;
    private ArrayList<ArrayList<Person>> partitions;
    private DBManager db;

    public ServerImpl() {
        connectToDatabase();
        /* generatePartitions(); */
    }

    @Override
    public void generateData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void generateVO() {
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

        this.partitions = partitions;
        System.out.println("Generated partitions...");
    }

    @Override
    public String transmit() {
        // TODO: Implement
        // Transmit authenticated value for number of partitions
        // Transmit VO for each partition
        return "ok";
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
}
