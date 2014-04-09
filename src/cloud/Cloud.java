package cloud;

interface Cloud {
    public void receiveData(int data, int verificationObject);
    // Returns result + VO with byzantine accuracy
    public void query(String query);
}
