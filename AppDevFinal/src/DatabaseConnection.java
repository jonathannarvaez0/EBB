import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection 
{
    public void AddItem(String itemNo, String itemName, String unitCost, String Quantity)
    {
	    try
		{ 
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		}
		catch(ClassNotFoundException cn)
		{
				System.out.println("There was a problem in your code");
				cn.printStackTrace();
		}
	    try {
            
            String query = "INSERT INTO INVENTORY (ItemNo,Description,UnitCost,Quantity)"+" VALUES(?,?,?,?)";
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb"); 
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, itemNo);
            preparedStmt.setString (2, itemName);
            preparedStmt.setDouble(3, Double.parseDouble(unitCost));
            preparedStmt.setInt (4, Integer.parseInt(Quantity));
            preparedStmt.execute();
        } 
	    catch (SQLException ex) 
	    {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
	    catch (NumberFormatException ee) 
	    {
            JOptionPane.showMessageDialog(null, "Input Valid value/s!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
    }//add 
    
    public void deleteItem(String itemNo) 
    {
    	try
		{ 
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		}
		catch(ClassNotFoundException cn)
		{
				System.out.println("There was a problem in your code");
				cn.printStackTrace();
		}
	    try 
	    {
            
            String query = "DELETE FROM INVENTORY WHERE ItemNo = ?";
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb"); 
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, itemNo);
            preparedStmt.execute();
            
	    }
            
	    catch (SQLException ex) 
	    {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editItem(String itemNo, String itemName, String unitCost, String Quantity) 
    {
        try
    	{ 
    		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    	}
    	catch(ClassNotFoundException cn)
    	{
    			System.out.println("There was a problem in your code");
    			cn.printStackTrace();
    	}
        try 
        {
                
        	String query = "UPDATE INVENTORY SET  Description=?, UnitCost=?, Quantity=? WHERE ItemNo=?";
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb"); 
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, itemName);
            preparedStmt.setDouble(2, Double.parseDouble(unitCost));
            preparedStmt.setInt (3, Integer.parseInt(Quantity));
            preparedStmt.setString (4, itemNo);
            preparedStmt.execute();
        } 
    	catch (SQLException ex) 
    	{
    		JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    	catch (NumberFormatException ee) 
    	{
         	JOptionPane.showMessageDialog(null, "Input Valid value/s!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
    	
    }//edit
    
    
}//end of class