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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Passenger {
    private PreparedStatement selectAllPassenger;
    private PreparedStatement addPassenger;
    private Connection connection;
    
    public Passenger()
    {
        connection = DBConnection.getConnection();
        
        try {
            selectAllPassenger = connection.prepareStatement("SELECT * FROM PASSENGER");
            addPassenger = connection.prepareStatement("INSERT INTO PASSENGER" + "(PASSENGER)" + "VALUES (?)");
            
        } catch (SQLException ex) {
           ex.printStackTrace();
           System.exit(1);
        }
    }
        
    public List< String> selectAllPassenger()
    {
        List<String> results = null;
        ResultSet resultSet = null;
        try
        {
            selectAllPassenger = connection.prepareStatement("SELECT * FROM PASSENGER");
            results = new ArrayList <String>();
            resultSet = selectAllPassenger.executeQuery();
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
    
    public int addPassenger(String passenger) 
    {
        int result = 0;
        try
        {
            addPassenger.setString(1,passenger);
            result = addPassenger.executeUpdate();
            
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return result;
    }
    public void close()
    {
        try
        {
            connection.close();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
