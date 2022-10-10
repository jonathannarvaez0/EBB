import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class InventoryUI extends JDialog {
	private JScrollPane scrollPane;
	private JTable tblInventory;
	private JTextField txtItmNo;
	private JTextField txtItmName;
	private JTextField txtPrice;
	private JTextField txtQuantity;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private int in = 0;
	DefaultTableModel  model;
	DatabaseConnection db = new DatabaseConnection();
	private JLabel lblQuantity;
	private JLabel lblUnitCost;
	private JLabel lblItmName;
	private JLabel lblItmNo;
	
	public InventoryUI() 
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(InventoryUI.class.getResource("/resources/icon3.png")));
		getContentPane().addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 			
			{
				try 
				{
					Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb");
					Statement check = con.createStatement();
					ResultSet rs1 = check.executeQuery("SELECT ItemNo FROM INVENTORY");
					int resint = 0;
					while(rs1.next())
					{
						resint++;
					}
					
					if(resint == 0)
					{
						txtItmNo.setText("0001");
					}
					else
					{
						Statement st = con.createStatement();
						String lastline = null;
						ResultSet rs = st.executeQuery("SELECT ItemNo FROM INVENTORY");
						while(rs.next())
							lastline = rs.getString("ItemNo");
						in = Integer.parseInt(lastline);
						txtItmNo.setText(String.format("%04d",++in));
					}
				}
				catch (SQLException ee) 
				{
					ee.printStackTrace();
				} 
				txtItmName.setText(null);
				txtPrice.setText(null);
				txtQuantity.setText(null);
				btnAdd.setEnabled(true);
				btnEdit.setEnabled(false);
				btnDelete.setEnabled(false);
				
			}
		});
		Components();
		tableUpdate();
		Events();
	}
	
	public void Components()
	{
		setTitle("E-BentaBase Inventory");
		setModal(true);
		setBounds(300, 180, 800, 450);

		
		scrollPane = new JScrollPane();
		
		lblItmNo = new JLabel("Item Number: ");
		lblItmNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblItmNo.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtItmNo = new JTextField();
		txtItmNo.setEditable(false);
		txtItmNo.setHorizontalAlignment(SwingConstants.CENTER);
		txtItmNo.setColumns(10);
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb");
			Statement check = con.createStatement();
			ResultSet rs1 = check.executeQuery("SELECT ItemNo FROM INVENTORY");
			int resint = 0;
			while(rs1.next())
			{
				resint++;
			}
			
			if(resint == 0)
			{
				txtItmNo.setText("0001");
			}
			else
			{
				Statement st = con.createStatement();
				String lastline = null;
				ResultSet rs = st.executeQuery("SELECT ItemNo FROM INVENTORY");
				while(rs.next())
					lastline = rs.getString("ItemNo");
				in = Integer.parseInt(lastline);
				txtItmNo.setText(String.format("%04d",++in));
			}
		} 
		catch (SQLException ee) 
		{
			ee.printStackTrace();
		} 

		
		lblItmName = new JLabel("Item Name: ");
		lblItmName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblItmName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtItmName = new JTextField();
		txtItmName.setHorizontalAlignment(SwingConstants.CENTER);
		txtItmName.setColumns(10);
		
		lblUnitCost = new JLabel("Unit Cost: ");
		lblUnitCost.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUnitCost.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtPrice = new JTextField();
		txtPrice.setHorizontalAlignment(SwingConstants.CENTER);
		txtPrice.setColumns(10);
		
		lblQuantity = new JLabel("Quantity: ");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtQuantity = new JTextField();
		txtQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		txtQuantity.setColumns(10);
		
		btnAdd = new JButton("   Add");
		btnAdd.setHorizontalAlignment(SwingConstants.LEFT);
		ImageIcon addimg = new ImageIcon(MainWindow.class.getResource("/resources/add128.png"));
		Image addimg1 = addimg.getImage();
		Image modaddimg = addimg1.getScaledInstance(14, 14, java.awt.Image.SCALE_SMOOTH);
		addimg = new ImageIcon(modaddimg);
		btnAdd.setIcon(addimg);
		
		btnEdit = new JButton("    Edit");
		btnEdit.setHorizontalAlignment(SwingConstants.LEFT);
		ImageIcon editimg = new ImageIcon(MainWindow.class.getResource("/resources/edit128.png"));
		Image editimg1 = editimg.getImage();
		Image modeditimg = editimg1.getScaledInstance(14, 14, java.awt.Image.SCALE_SMOOTH);
		editimg = new ImageIcon(modeditimg);
		btnEdit.setIcon(editimg);
		
		btnDelete = new JButton("  Delete");
		btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
		ImageIcon deleteimg = new ImageIcon(MainWindow.class.getResource("/resources/delete128.png"));
		Image deleteimg1 = deleteimg.getImage();
		Image moddeleteimg = deleteimg1.getScaledInstance(14, 14, java.awt.Image.SCALE_SMOOTH);
		deleteimg = new ImageIcon(moddeleteimg);
		btnDelete.setIcon(deleteimg);
		
		
		btnAdd.setEnabled(true);
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblItmNo, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
								.addComponent(lblItmName, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
								.addComponent(lblUnitCost, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
								.addComponent(lblQuantity, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtItmNo, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtItmName, 116, 116, 116)
								.addComponent(txtPrice, 116, 116, 116)
								.addComponent(txtQuantity, 116, 116, 116))
							.addGap(68))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(15)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEdit, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addGap(18)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(61)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtItmNo)
						.addComponent(lblItmNo, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtItmName)
						.addComponent(lblItmName, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
					.addGap(22)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtPrice)
						.addComponent(lblUnitCost, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtQuantity)
						.addComponent(lblQuantity, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
					.addGap(45)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
						.addComponent(btnEdit, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
					.addGap(65))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tblInventory = new JTable();
		model = new DefaultTableModel();
		tblInventory.setModel(model);
		Object[] column = {"Item Number", "Item Name", "Unit Cost", "Item Quantity"};
        model.setColumnIdentifiers(column);
		scrollPane.setViewportView(tblInventory);
		getContentPane().setLayout(groupLayout);
		
	}
	
	public void Events()
	{

		btnAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				db.AddItem(txtItmNo.getText(), txtItmName.getText(), txtPrice.getText(), txtQuantity.getText());
				txtItmNo.setText(String.format("%04d",++in));
				txtItmName.setText(null);
				txtPrice.setText(null);
				txtQuantity.setText(null);
				JOptionPane.showMessageDialog(null,"Record Added", "Success!", JOptionPane.INFORMATION_MESSAGE); 
				tableReset();
				tableUpdate();
			}
		});//add button
		btnDelete.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				db.deleteItem(txtItmNo.getText());
				txtItmNo.setText(String.format("%04d",in));
				txtItmName.setText(null);
				txtPrice.setText(null);
				txtQuantity.setText(null);
				JOptionPane.showMessageDialog(null,"Record Deleted", "Success!", JOptionPane.INFORMATION_MESSAGE);
				tableReset();
				tableUpdate();
			}
		});//delete
		btnEdit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				db.editItem(txtItmNo.getText(), txtItmName.getText(), txtPrice.getText(), txtQuantity.getText());
				txtItmNo.setText(String.format("%04d",in));
				txtItmName.setText(null);
				txtPrice.setText(null);
				txtQuantity.setText(null);
				JOptionPane.showMessageDialog(null,"Record Edited", "Success!", JOptionPane.INFORMATION_MESSAGE);
				tableReset();
				tableUpdate();
			}
		});//edit
		
		tblInventory.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int selectedIndex = tblInventory.getSelectedRow();
				txtItmNo.setText(model.getValueAt(selectedIndex, 0).toString());
				txtItmName.setText(model.getValueAt(selectedIndex, 1).toString());
				txtPrice.setText(model.getValueAt(selectedIndex, 2).toString());
				txtQuantity.setText(model.getValueAt(selectedIndex, 3).toString());
				btnAdd.setEnabled(false);
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
			}
		});//put selected row/record in table to respective textfields [for editing and deleting of records]
	}//event handlers
	
	public void tableUpdate() 
	{
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ItemNo, Description, UnitCost, Quantity FROM INVENTORY");
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int colNo = rsmd.getColumnCount();
			while(rs.next())
			{
			 Object[] objects = new Object[colNo];
			 for(int i=0;i<colNo;i++)
			 {
			  objects[i]=rs.getObject(i+1);
			 }
			 model.addRow(objects);
			}
			tblInventory.setModel(model);
		}
		catch(SQLException sql) 
		{
			sql.printStackTrace();
	    }
	}//table update 
	public void tableReset() 
	{
        DefaultTableModel dm = (DefaultTableModel) tblInventory.getModel();
        int rowCount = dm.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }
    }
	
}//invetory ui class
