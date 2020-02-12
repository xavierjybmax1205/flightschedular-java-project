
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JunyoungBae
 */


public class Days {
    private static Connection connection;
    private static PreparedStatement getDate;
    private static PreparedStatement addDate;
    
    public Days()
    {
        connection = DBConnection.getConnection();
        try
        {
            getDate = connection.prepareStatement("SELECT * FROM DATE");
            addDate = connection.prepareStatement("INSERT INTO DATE" + "(DATE)" + "VALUES (?)");
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public int addDate(Date day) 
    {
        int result = 0;
        try
        {
            addDate.setDate(1,day);
            result = addDate.executeUpdate();
            
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return result;
    }
    public static List<Date> getDate()
    {
        List<Date> results = null;
        ResultSet resultSet = null;
        try
        {
            resultSet = getDate.executeQuery();
            results = new ArrayList <Date>();
            while (resultSet.next())
            {
                results.add(resultSet.getDate("DATE"));
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
