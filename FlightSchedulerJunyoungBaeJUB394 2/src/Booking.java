/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JunyoungBae
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Booking {
    private static Connection connection;
    private static PreparedStatement addBooking;
    private static PreparedStatement getFlightSeats;
    private static int seatsBooked;
    private static PreparedStatement statusFlightDay;
    private static PreparedStatement getCustomerStatus;
    private static PreparedStatement getFlightStatus;
    private static PreparedStatement deleteFlight;
    private static PreparedStatement cancelCustomer;
    private static PreparedStatement booleanChecker;
    
    public Booking()
    {
        connection = DBConnection.getConnection();
        try
        {
            addBooking = connection.prepareStatement("INSERT INTO BOOKING" +"(PASSENGER, DATE, FLIGHT)" + "VALUES (?,?,?)");
            statusFlightDay = connection.prepareStatement("SELECT PASSENGER FROM BOOKING WHERE FLIGHT=? AND DATE = ?");
            getFlightSeats  = connection.prepareStatement("select count(FLIGHT) from BOOKING where FLIGHT = ? and DATE = ?");
            getCustomerStatus = connection.prepareStatement("SELECT  * FROM BOOKING WHERE PASSENGER = ?");
            getFlightStatus = connection.prepareStatement("SELECT * FROM BOOKING WHERE FLIGHT = ?");
            deleteFlight =connection.prepareStatement("DELETE FROM BOOKING WHERE FLIGHT=?");
            cancelCustomer = connection.prepareStatement("DELETE FROM BOOKING WHERE PASSENGER =? and DATE = ?");
            booleanChecker = connection.prepareStatement("SELECT FLIGHT FROM BOOKING WHERE PASSENGER=? AND DATE=?");
        }
        catch (SQLException ex) {
           ex.printStackTrace();
           System.exit(1);
        }
                
    }
    public List<String> inBooking(String name, Date day)
    {
        ResultSet resultSet = null;
        //List<String> flights = null;
        List<String> result = null;
        try
        {
            booleanChecker.setString(1, name);
            booleanChecker.setDate(2, day);
            result = new ArrayList<String>();
            resultSet = booleanChecker.executeQuery();
         //   flights = new ArrayList<String>();
            if (resultSet.next())
                result.add(resultSet.getString("FLIGHT"));
            else
                result = null;
                
        }
        catch (SQLException ex) {
           ex.printStackTrace();
           System.exit(1);
        }
        return result;
    }
    public int cancelCustomer(String name, Date day)
    {
        int result = 0;
        try
        {
            cancelCustomer.setString(1, name);
            cancelCustomer.setDate(2, day);
            result = cancelCustomer.executeUpdate();
        }
        catch (SQLException ex) {
           ex.printStackTrace();
           System.exit(1);
        }
        return result;
    }
    
    
    public int deleteFlight(String flight)
    {
        int result = 0;
        try
        {
            deleteFlight.setString(1, flight);
            result = deleteFlight.executeUpdate();
        }
        catch (SQLException ex) {
           ex.printStackTrace();
           System.exit(1);
        }
        return result;
    }
    
    public int addBooking(String PASSENGER, Date DATE, String FLIGHT)
    {
        int result = 0;
        
        try
        {
            addBooking.setString(1, PASSENGER);
            addBooking.setDate(2, DATE);
            addBooking.setString(3, FLIGHT);
            result = addBooking.executeUpdate();
        }
        catch (SQLException ex) {
           ex.printStackTrace();
           System.exit(1);
        }
        return result;
    }
    
    public static int getFlightSeats(String FLIGHT, Date DATE)
    {
        ResultSet resultSet = null;
        try{
            getFlightSeats.setString(1, FLIGHT); 
            getFlightSeats.setDate(2, DATE); 
            resultSet = getFlightSeats.executeQuery(); 
            resultSet.next(); 
            seatsBooked = resultSet.getInt(1);
        }
        catch (SQLException ex) {
           ex.printStackTrace();
           System.exit(1);
        }
        return seatsBooked;
    }
    
   public static List< String> getFlightStatus(String Flight, Date DATE)
    {
        List<String> results = null;
        ResultSet resultSet = null;
        try
        {
            statusFlightDay.setString(1, Flight);
            statusFlightDay.setDate(2, DATE);
            results = new ArrayList <String>();
            resultSet = statusFlightDay.executeQuery();
            while (resultSet.next())
            {
                results.add(resultSet.getString("PASSENGER"));
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
        }
   public List<List<String>> getCustomerStatus(String name)
    {
        List<String> results = null;
        List<String> results2 = null;
        ResultSet resultSet = null;
        List<List<String>> returningResults = null;
        try
        {
            getCustomerStatus.setString(1, name);
            results = new ArrayList <String>();
            results2 = new ArrayList<String>();
            returningResults = new ArrayList<List<String>>();
            
            resultSet = getCustomerStatus.executeQuery();
            while (resultSet.next())
            {
                results.add(resultSet.getString("FLIGHT"));
                results2.add(resultSet.getDate("DATE").toString());
            }
            
            returningResults.add(results);
            returningResults.add(results2);
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return returningResults;
    }
   public List<List<String>> getFlightStatus(String flight)
    {
        List<String> results = null;
        List<String> results2 = null;
        ResultSet resultSet = null;
        List<List<String>> returningResults = null;
        try
        {
            getFlightStatus.setString(1, flight);
            results = new ArrayList <String>();
            results2 = new ArrayList<String>();
            returningResults = new ArrayList<List<String>>();
            
            resultSet = getFlightStatus.executeQuery();
            while (resultSet.next())
            {
                results.add(resultSet.getString("PASSENGER"));
                results2.add(resultSet.getDate("DATE").toString());
            }
            
            returningResults.add(results);
            returningResults.add(results2);
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return returningResults;
    }
}
