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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FlightManager {
    private static Connection connection;
    private static PreparedStatement getFlight;
    private static PreparedStatement getSeat;
    private static PreparedStatement addFlight;
    private static PreparedStatement deleteFlight;
    
    
    public FlightManager(){
    connection = DBConnection.getConnection();
    try
    {
        getFlight = connection.prepareStatement("SELECT * FROM FLIGHT"); 
        getSeat = connection.prepareStatement("SELECT SEATS FROM FLIGHT WHERE NAME = ?");
        addFlight = connection.prepareStatement("INSERT INTO FLIGHT"+ "(NAME, SEATS)" +"VALUES(?,?)");
        deleteFlight =connection.prepareStatement("DELETE FROM FLIGHT WHERE NAME=?");
    }
    catch (SQLException ex) {
           ex.printStackTrace();
           System.exit(1);
        }
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
    
    public int addFlight(String Name, int SeatAmount)
    {
        int result = 0;
        try
        {
            addFlight.setString(1, Name);
            addFlight.setInt(2,SeatAmount);
            result = addFlight.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return result;
    }
    
    public static int getSeat(String seat)
    {
        int seatAmount = 0;
        ResultSet resultSet = null;
        
        try
        {
            getSeat.setString(1, seat);
            resultSet = getSeat.executeQuery();
            resultSet.next();
            seatAmount = resultSet.getInt(1);
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return seatAmount;
        
    }
    public static List<String> getFlight()
    {
        List<String> results = null;
        ResultSet resultSet = null;
        try
        {
            resultSet = getFlight.executeQuery();
            results = new ArrayList <String>();
            while (resultSet.next())
            {
                results.add(resultSet.getString("NAME"));
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return results;
    }
    
    
}
