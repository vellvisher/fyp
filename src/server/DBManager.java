package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import person.Person;

public class DBManager {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String SERVER = "localhost";

    public DBManager(String database) {
        String url = "jdbc:mysql://" + SERVER + "/" + database + "?user=fyp"+
            "&password=fyp";
        try {
            // this will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // setup the connection with the DB.
            connect = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String query) {
        try {
            statement = connect.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Person> executeQuery(String query) {
        ArrayList<Person> results = null;
        try {
            // statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // resultSet gets the result of the SQL query
            resultSet = statement
                .executeQuery(query);
            results = resultSetToPersons(resultSet);
            /*

            // preparedStatements can use variables and are more efficient
            preparedStatement = connect
                .prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
            // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
            // parameters start with 1
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
            preparedStatement.setString(5, "TestSummary");
            preparedStatement.setString(6, "TestComment");
            preparedStatement.executeUpdate();

            preparedStatement = connect
                .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);

            // remove again the insert comment
            preparedStatement = connect
                .prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();

            resultSet = statement
                .executeQuery("select * from FEEDBACK.COMMENTS");
            writeMetaData(resultSet);
             */

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;

    }

    private ArrayList<Person> resultSetToPersons(ResultSet resultSet) throws SQLException{
        // resultSet is initialised before the first data set
        ArrayList<Person> persons = new ArrayList<Person>();
        while (resultSet.next()) {
            Person person = new Person();
            person.id = resultSet.getInt("id");
            person.name = resultSet.getString("name");
            person.department_id = resultSet.getInt("department_id");
            person.salary = resultSet.getInt("salary");
            persons.add(person);
        }
        return persons;
    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        // now get some metadata from the database
        System.out.println("The columns in the table are: ");
        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    public void verifyPartitions(Integer k, int i) {
        // TODO Auto-generated method stub

    }
}
