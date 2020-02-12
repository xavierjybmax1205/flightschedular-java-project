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
import java.sql.Timestamp;
import java.util.Calendar;

public class Waitlist {
    private static Connection connection;
    private PreparedStatement addWaitList;
    private PreparedStatement getWaitListStatus;
    private PreparedStatement getCustomerStatus;
    private PreparedStatement deleteFlight;
    private PreparedStatement cancelCustomer;
    private PreparedStatement booleanChecker;
    private PreparedStatement getFirstCustomer;
    private PreparedStatement getCustomerName;
    public Waitlist()
    {
        connection = DBConnection.getConnection();
        try
        {
            addWaitList = connection.prepareStatement("INSERT INTO WAITLIST (PASSENGER, NAME, DATE, TIME) VALUES (?, ?, ?, ?)");
            getWaitListStatus = connection.prepareStatement("SELECT * FROM WAITLIST WHERE DATE = ? ORDER BY TIME");
            getCustomerStatus = connection.prepareStatement("SELECT * FROM WAITLIST WHERE PASSENGER = ?");
            deleteFlight =connection.prepareStatement("DELETE FROM WAITLIST WHERE NAME=?");
            cancelCustomer =connection.prepareStatement("DELETE FROM WAITLIST WHERE PASSENGER =? AND DATE=?");
            booleanChecker = connection.prepareStatement("SELECT NAME FROM WAITLIST WHERE PASSENGER=? AND DATE=?");
            getFirstCustomer = connection.prepareStatement("SELECT PASSENGER FROM WAITLIST WHERE NAME=? AND DATE=? ORDER BY TIME");
            getCustomerName = connection.prepareStatement("SELECT * FROM WAITLIST WHERE NAME=?");
        }
        catch (SQLException ex) {
           ex.printStackTrace();
           System.exit(1);
        }
    }
    
    public String getFirstCustomer(String Flight, Date Day)
    {
        List<String> results = null;
        ResultSet resultSet = null;
        try
        {
            getFirstCustomer.setString(1, Flight);
            getFirstCustomer.setDate(2, Day);
            results = new ArrayList <String>();
            resultSet = getFirstCustomer.executeQuery();
            if (resultSet.next())
            {
                results.add(resultSet.getString("PASSENGER"));
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results.get(0);
    }
    
    public List<List<String>> getCustomerName(String flight)
    {
        List<String> results = null;
        List<String> results2 = null;
        ResultSet resultSet = null;
        List<List<String>> returningResults = null;
        try
        {
            getCustomerName.setString(1, flight);
            results = new ArrayList <String>();
            results2 = new ArrayList<String>();
            returningResults = new ArrayList<List<String>>();
            
            resultSet = getCustomerName.executeQuery();
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
    
    public boolean inWaitlist(String name, Date day)
    {
        ResultSet resultSet = null;
        //List<String> flights = null;
        boolean result = false;
        try
        {
            booleanChecker.setString(1, name);
            booleanChecker.setDate(2, day);
            resultSet = booleanChecker.executeQuery();
            //flights = new ArrayList<String>();
            if (resultSet.next())
                result = true;
            else
                result = false;
                
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
    
    public int addWaitList(String Flight, Date Day, String Passenger, Timestamp currentTimestamp)
    {
        int count = 0;
        try{
            addWaitList.setString(1, Passenger);
            addWaitList.setString(2, Flight);
            addWaitList.setDate(3, Day);
            addWaitList.setTimestamp(4, currentTimestamp);
            count = addWaitList.executeUpdate();
        
        }
        catch (SQLException ex) {
           ex.printStackTrace();
           System.exit(1);
        }
        return count;
    }
    
    public List<List<String>> getWaitListStatus(Date Day)
    {
        List<String> results = null;
        List<String> results2 = null;
        ResultSet resultSet = null;
        List<List<String>> returningResults = null;
        try
        {
            getWaitListStatus.setDate(1, Day);
            results = new ArrayList <String>();
            results2 = new ArrayList<String>();
            returningResults = new ArrayList<List<String>>();
            
            resultSet = getWaitListStatus.executeQuery();
            while (resultSet.next())
            {
                results.add(resultSet.getString("PASSENGER"));
                results2.add(resultSet.getString("NAME"));
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
                results.add(resultSet.getString("NAME"));
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
