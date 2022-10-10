import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Image;

@SuppressWarnings("serial")
public class TransactionHistory extends JDialog {
	private JScrollPane scrlpnTHistory;
	private JTable tblTHistory;
	private JTable tblDisp;
	private JScrollPane scrllpndDisp;
	DefaultTableModel modelTHistory;
	DefaultTableModel modelDisp;
	private JButton btnPass;

	public TransactionHistory()
	{
		Components();
		tableTHistory();
		Events();
	}

	public void Components()
	{
		setTitle("E-BentaBase Transaction History");
		setModal(true);
		setBounds(300, 180, 800, 450);
		
		scrlpnTHistory = new JScrollPane();
		
		
		
		scrllpndDisp = new JScrollPane();
		btnPass = new JButton("");
		ImageIcon viewimg = new ImageIcon(MainWindow.class.getResource("/resources/view128.png"));
		Image viewimg1 = viewimg.getImage();
		Image modviewimg = viewimg1.getScaledInstance(12, 12, java.awt.Image.SCALE_SMOOTH);
		viewimg = new ImageIcon(modviewimg);
		btnPass.setIcon(viewimg);

		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrlpnTHistory, GroupLayout.PREFERRED_SIZE, 768, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrllpndDisp, GroupLayout.PREFERRED_SIZE, 768, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(310)
					.addComponent(btnPass, GroupLayout.PREFERRED_SIZE, 163, Short.MAX_VALUE)
					.addGap(311))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrlpnTHistory, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPass)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrllpndDisp, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		tblTHistory = new JTable();
		scrlpnTHistory.setViewportView(tblTHistory);
		modelTHistory = new DefaultTableModel();
		tblTHistory.setModel(modelTHistory);
		Object[] columnHist = {"Invoice #", "Date", "Customer Name", "Contact Number", "Address", "Amount"};
		modelTHistory.setColumnIdentifiers(columnHist);
		scrlpnTHistory.setViewportView(tblTHistory);
		//lower table
		tblDisp = new JTable();
		modelDisp= new DefaultTableModel();
		tblDisp.setModel(modelDisp);
		Object[] columnDisp = {"Item No", "Item Name", "Item Quantity"};
        modelDisp.setColumnIdentifiers(columnDisp);
		scrllpndDisp.setViewportView(tblDisp);
		
		getContentPane().setLayout(groupLayout);
	}//end of constructor
	
	private void Events() {

		btnPass.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				tableReset();
				int rn=tblTHistory.getSelectedRow();
				String selectedName =((String) tblTHistory.getValueAt(rn, 2));
				scrllpndDisp.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), selectedName+"'s Order/s", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
				tableDisp();
				
			}
		});
	}//event handler for button
	
	public void tableDisp()
	{
		int rowNum=tblTHistory.getSelectedRow();
		String selectedInvNo =((String) tblTHistory.getValueAt(rowNum, 0)).substring(0,6);
		try
		{ 
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		}
		catch(ClassNotFoundException cn)
		{
				System.out.println("There was a problem in your code");
				cn.printStackTrace();
		}//1st try catch block
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT [ORDER DETAIL].ItemNo, INVENTORY.Description, [ORDER DETAIL].Quantity FROM INVENTORY INNER JOIN [ORDER DETAIL] ON INVENTORY.[ItemNo] = [ORDER DETAIL].[ItemNo]WHERE InvoiceNo ='"+selectedInvNo+"'");
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int colNo = rsmd.getColumnCount();
			while(rs.next())
			{
			 Object[] objects = new Object[colNo];
			 for(int i=0;i<colNo;i++)
			 {
			  objects[i]=rs.getObject(i+1);
			 }
			 modelDisp.addRow(objects);
			}//while
			tblDisp.setModel(modelDisp);
		}
		catch(SQLException ex) 
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}//2nd try catch block
	}
	
	public void tableReset() 
	{
        DefaultTableModel dm = (DefaultTableModel) tblDisp.getModel();
        int rowCount = dm.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--)
        {
            dm.removeRow(i);
        }
    }
	
	public void tableTHistory() 
	{
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT InvoiceNo, Format(InvoiceDate,\"Short Date\"), CustomerName, ContactNumber, Address, Amount FROM ORDER");
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int colNo = rsmd.getColumnCount();
			while(rs.next())
			{
			 Object[] objects = new Object[colNo];
			 for(int i=0;i<colNo;i++)
			 {
				 objects[i]=rs.getObject(i+1);
			 }
			 modelTHistory.addRow(objects);
			}
			tblTHistory.setModel(modelTHistory);
		}
		catch(SQLException sql) 
		{
			sql.printStackTrace();
	    }
	}//table update
}
