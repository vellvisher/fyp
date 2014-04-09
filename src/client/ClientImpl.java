package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import cloud.Cloud;

class ClientImpl implements Client {
    private static final String RMI_SERVER = null;

    public static void main(String args[]) {
        try {
            String name = Cloud.NAME;
            Registry registry = LocateRegistry.getRegistry(RMI_SERVER);
            Cloud server = (Cloud) registry.lookup(name);
            System.out.println(server.query("5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
