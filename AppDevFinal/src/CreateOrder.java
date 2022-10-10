import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class CreateOrder extends JDialog 
{
	//PANELS
	private JPanel pnlCustomer;
	private JPanel pnlAddOrder;
	private JPanel pnlInvoice;
	private JScrollPane scrollpaneOrderDet;
	private JPanel pnlSummary;
	
	//COMPONENTS(CUSTOMER)
	private JLabel lblCustomerName;
	private JTextField txtCustomerName;
	private JLabel lblContact;
	private JTextField txtContact;
	private JLabel lblAddress;
	private JTextField txtAddress;
	
	//COMPONENTS(ITEM)
	private JLabel lblItem;
	private JComboBox<Object> cmbbxItem;
	private JLabel lblQuantity;
	private JSpinner spnnrQuantity;
	private JButton btnAddOrder;
	
	//COMPONENTS(INVOICE)
	private JLabel lblInvoice;
	private JTextField txtInvoice;
	private JLabel lblDate;
	private JTextField txtDate;
	
	//COMPONENTS(ORDER DETAILS)
	private JTable tblOrderDetails;
	
	//COMPONENTS(SUMMARY)
	private JTextField txtTotal;
	private JButton btnFinish;
	
	//VARIABLES
	DefaultTableModel  model;
	private String currId;
	private String currName;
	private Double currCost;
	private int quant = 0;
	private int qty;
	private Double totCost=0.00;
	private int in = 0;
	private JButton btnCompute;
	private JButton btnDelete;
	
	
	
	public CreateOrder() 
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(CreateOrder.class.getResource("/resources/icon3.png")));
		getContentPane().addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				btnDelete.setEnabled(false);
			}
		});
		Components();
		Events();
		//fillComboBox();
	}
	
	@SuppressWarnings("deprecation")
	public void Components()
	{
		setTitle("Create Order");
		setBounds(305, 180, 800, 450);
		
		pnlCustomer = new JPanel();
		pnlCustomer.setBorder(new TitledBorder(null, "Customer Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		scrollpaneOrderDet = new JScrollPane();
		
		scrollpaneOrderDet.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Order Details", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		pnlAddOrder = new JPanel();
		pnlAddOrder.setBorder(new TitledBorder(null, "Add Order", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		pnlSummary = new JPanel();
		pnlSummary.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Total Amount", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		btnFinish = new JButton("   Finish Transaction");
		ImageIcon finishimg = new ImageIcon(MainWindow.class.getResource("/resources/finish128.png"));
		Image finishimg1 = finishimg.getImage();
		Image modfinishimg = finishimg1.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
		finishimg = new ImageIcon(modfinishimg);
		btnFinish.setIcon(finishimg);
		btnFinish.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFinish.setEnabled(false);
		
		pnlInvoice = new JPanel();
		pnlInvoice.setBorder(new TitledBorder(null, "Invoice Detail", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		btnDelete = new JButton("  Delete");
		ImageIcon delimg = new ImageIcon(MainWindow.class.getResource("/resources/delete128.png"));
		Image delimg1 = delimg.getImage();
		Image moddelimg = delimg1.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
		delimg = new ImageIcon(moddelimg);
		btnDelete.setIcon(delimg);
		btnDelete.setEnabled(false);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(pnlCustomer, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlAddOrder, GroupLayout.PREFERRED_SIZE, 236, Short.MAX_VALUE))
						.addComponent(scrollpaneOrderDet, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlSummary, GroupLayout.PREFERRED_SIZE, 255, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
							.addGap(126))
						.addComponent(pnlInvoice, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
						.addComponent(btnFinish, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlInvoice, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
						.addComponent(pnlAddOrder, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pnlCustomer, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(pnlSummary, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnFinish, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
							.addComponent(btnDelete))
						.addComponent(scrollpaneOrderDet, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
					.addGap(7))
		);
		
		lblInvoice = new JLabel("Invoice #: ");
		lblInvoice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInvoice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		lblDate = new JLabel("Date: ");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		txtDate = new JTextField();
		txtDate.setHorizontalAlignment(SwingConstants.CENTER);
		txtDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtDate.setEditable(false);
		txtDate.setColumns(10);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyy");
		LocalDate now = LocalDate.now();
		txtDate.setText(dtf.format(now));
		
		txtInvoice = new JTextField();
		txtInvoice.setHorizontalAlignment(SwingConstants.CENTER);
		txtInvoice.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtInvoice.setEditable(false);
		txtInvoice.setColumns(10);
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb");
			Statement check = con.createStatement();
			ResultSet rs = check.executeQuery("SELECT InvoiceNo FROM ORDER");
			int resint = 0;
			while(rs.next())
			{
				resint++;
			}
			if(resint == 0)
			{
				txtInvoice.setText("OR0001");
			}
			else
			{
				Statement st = con.createStatement();
				String lastline = null;
				ResultSet rs1 = st.executeQuery("SELECT InvoiceNo FROM ORDER");
				while(rs1.next())
					lastline = rs1.getString("InvoiceNo");
				String code = lastline.substring(2,6);
				in = Integer.parseInt(code);
				txtInvoice.setText("OR"+String.format("%04d",++in));
			}
		}
		catch (SQLException ee) 
		{
			ee.printStackTrace();
		}
		GroupLayout gl_pnlInvoice = new GroupLayout(pnlInvoice);
		gl_pnlInvoice.setHorizontalGroup(
			gl_pnlInvoice.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlInvoice.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlInvoice.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlInvoice.createSequentialGroup()
							.addComponent(lblDate, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtDate, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
						.addGroup(gl_pnlInvoice.createSequentialGroup()
							.addComponent(lblInvoice, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtInvoice, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_pnlInvoice.setVerticalGroup(
			gl_pnlInvoice.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlInvoice.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_pnlInvoice.createParallelGroup(Alignment.LEADING)
						.addComponent(txtInvoice)
						.addComponent(lblInvoice, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlInvoice.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtDate, Alignment.LEADING)
						.addComponent(lblDate, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(39, Short.MAX_VALUE))
		);
		pnlInvoice.setLayout(gl_pnlInvoice);
		
		txtTotal = new JTextField();
		txtTotal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		
		btnCompute = new JButton("  Compute");
		btnCompute.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ImageIcon computeimg = new ImageIcon(MainWindow.class.getResource("/resources/compute128.png"));
		Image computeimg1 = computeimg.getImage();
		Image modcomputeimg = computeimg1.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
		computeimg = new ImageIcon(modcomputeimg);
		btnCompute.setIcon(computeimg);
		
		GroupLayout gl_pnlSummary = new GroupLayout(pnlSummary);
		gl_pnlSummary.setHorizontalGroup(
			gl_pnlSummary.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSummary.createSequentialGroup()
					.addComponent(txtTotal, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_pnlSummary.createSequentialGroup()
					.addGap(47)
					.addComponent(btnCompute, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(50))
		);
		gl_pnlSummary.setVerticalGroup(
			gl_pnlSummary.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSummary.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtTotal, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCompute, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
					.addContainerGap())
		);
		pnlSummary.setLayout(gl_pnlSummary);
		
		tblOrderDetails = new JTable();
		model= new DefaultTableModel();
		tblOrderDetails.setModel(model);
		Object[] column = {"Item Number", "Item Name", "Item Price", "Item Quantity", "Amount"};
        model.setColumnIdentifiers(column);
		scrollpaneOrderDet.setViewportView(tblOrderDetails);
		
		lblItem = new JLabel("Item: ");
		lblItem.setHorizontalAlignment(SwingConstants.RIGHT);
		lblItem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		cmbbxItem = new JComboBox<Object>();
		cmbbxItem.addItem(null);
		spnnrQuantity = new JSpinner();
		spnnrQuantity.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));

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
			Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ItemNo, Description FROM INVENTORY");
			while(rs.next()) 
			{
				String itemNo=rs.getString("ItemNo");
				String Description=rs.getString("Description");
				//cmbbxItem.setModel(new DefaultComboBoxModel(new String[] {"", itemNo + "-" + Description}));

				//cmbbxItem.setModel(new DefaultComboBoxModel(new String[] {"", itemNo + "-" + Description + "("+ quantity + ")"}));	
				cmbbxItem.addItem(itemNo + " - " + Description);
			}	
		}
		catch(SQLException ex) 
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		lblQuantity = new JLabel("Quantity: ");
		lblQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		btnAddOrder = new JButton("Add Order");
		btnAddOrder.setHorizontalAlignment(SwingConstants.LEFT);
		ImageIcon addimg = new ImageIcon(MainWindow.class.getResource("/resources/add128.png"));
		Image addimg1 = addimg.getImage();
		Image modaddimg = addimg1.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
		addimg = new ImageIcon(modaddimg);
		btnAddOrder.setIcon(addimg);
		
		GroupLayout gl_pnlAddOrder = new GroupLayout(pnlAddOrder);
		gl_pnlAddOrder.setHorizontalGroup(
			gl_pnlAddOrder.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlAddOrder.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlAddOrder.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblItem, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblQuantity, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlAddOrder.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlAddOrder.createSequentialGroup()
							.addComponent(btnAddOrder, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(71))
						.addGroup(gl_pnlAddOrder.createSequentialGroup()
							.addGroup(gl_pnlAddOrder.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(spnnrQuantity, Alignment.LEADING)
								.addComponent(cmbbxItem, Alignment.LEADING, 0, 148, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_pnlAddOrder.setVerticalGroup(
			gl_pnlAddOrder.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlAddOrder.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_pnlAddOrder.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblItem, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmbbxItem, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlAddOrder.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblQuantity, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(spnnrQuantity, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnAddOrder, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		pnlAddOrder.setLayout(gl_pnlAddOrder);
		
		lblCustomerName = new JLabel("Customer Name: ");
		lblCustomerName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCustomerName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblContact = new JLabel("Contact Number: ");
		lblContact.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContact.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblAddress = new JLabel("Address: ");
		lblAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		txtCustomerName = new JTextField();
		txtCustomerName.setColumns(10);
		
		txtContact = new JTextField();
		txtContact.setColumns(10);
		
		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		GroupLayout gl_pnlCustomer = new GroupLayout(pnlCustomer);
		gl_pnlCustomer.setHorizontalGroup(
			gl_pnlCustomer.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlCustomer.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_pnlCustomer.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblAddress, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
						.addComponent(lblContact, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblCustomerName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlCustomer.createParallelGroup(Alignment.LEADING)
						.addComponent(txtCustomerName, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtContact, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_pnlCustomer.setVerticalGroup(
			gl_pnlCustomer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCustomer.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_pnlCustomer.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCustomerName, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlCustomer.createSequentialGroup()
							.addGap(1)
							.addComponent(txtCustomerName, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlCustomer.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblContact, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlCustomer.createSequentialGroup()
							.addGap(1)
							.addComponent(txtContact, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlCustomer.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAddress, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlCustomer.createSequentialGroup()
							.addGap(1)
							.addComponent(txtAddress, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)))
					.addGap(27))
		);
		pnlCustomer.setLayout(gl_pnlCustomer);
		getContentPane().setLayout(groupLayout);
	}
	
	public void Events()
	{
		cmbbxItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String selectedItem = null;
				if(cmbbxItem.getSelectedIndex()> 0)
				{
					selectedItem = cmbbxItem.getSelectedItem().toString().substring(0, 5);
				}
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
					Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb");
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT ItemNo, Quantity FROM INVENTORY WHERE ItemNo="+selectedItem);
					while(rs.next())
					{
						
						quant = rs.getInt("Quantity");
						spnnrQuantity.setModel(new SpinnerNumberModel(1, 1, quant, 1));
					}
				}
				catch(SQLException ex) 
				{
					JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAddOrder.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				String selectedItem = cmbbxItem.getSelectedItem().toString().substring(0, 5);
				int varName = (Integer) spnnrQuantity.getValue();
				qty= varName;
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
					Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb");
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT ItemNo, Description, UnitCost  FROM INVENTORY WHERE ItemNo="+selectedItem);
					while(rs.next())
					{
						currId=rs.getString("ItemNo");
						currName=rs.getString("Description");
						currCost=rs.getDouble("UnitCost");
					}
				}
				catch(SQLException ex) 
				{
					JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				totCost=currCost*qty+totCost;
				//txtTotal.setText(String.valueOf(totCost) + " PHP");
				model.addRow(new Object[]{currId, currName, currCost,qty, currCost*qty});
				
				//Quantity Limit
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
					Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb");
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT ItemNo, Quantity FROM INVENTORY WHERE ItemNo="+selectedItem);
					while(rs.next())
					{
						quant = rs.getInt("Quantity");
						spnnrQuantity.setModel(new SpinnerNumberModel(1, 1, quant-qty, 1));
					}
				}
				catch(SQLException ex) 
				{
					JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				cmbbxItem.setSelectedIndex(0);
			}//action performed
		});
		btnDelete.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				int selectedIndex = tblOrderDetails.getSelectedRow();
				((DefaultTableModel)tblOrderDetails.getModel()).removeRow(selectedIndex);
				cmbbxItem.setSelectedIndex(0);
			}
		});//delete row in jtable
		btnCompute.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Double total = 0.00;
		        Double Amount=0.00;
				for(int i = 0; i < tblOrderDetails.getRowCount(); i++)
				{
			        Amount = Double.parseDouble(tblOrderDetails.getValueAt(i, 4)+"");
			        total = Amount+total;
			        txtTotal.setText(String.valueOf(total));
			        btnFinish.setEnabled(true);
			        btnCompute.setEnabled(true);
			    }
			}
		});//total price
		btnFinish.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String ObjButtons[] = {"Yes", "No"};
				int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to finish transaction?", "E-BentaBase", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if(PromptResult == 0)
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
						SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyy");
						String strdate = txtDate.getText();
						java.util.Date date = df.parse(strdate);
						String query = "INSERT INTO ORDER (InvoiceNo,InvoiceDate,CustomerName,ContactNumber,Address,Amount)"+" VALUES(?,?,?,?,?,?)";
			            Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb"); 
			            PreparedStatement preparedStmt = con.prepareStatement(query);
			            preparedStmt.setString (1, txtInvoice.getText());
			            preparedStmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			            preparedStmt.setString(3, txtCustomerName.getText());
			            preparedStmt.setString(4, txtContact.getText());
			            preparedStmt.setString(5, txtAddress.getText());
			            preparedStmt.setDouble(6, Double.parseDouble(txtTotal.getText()));
			            preparedStmt.execute();
					}
					catch(SQLException ex)
					{
						JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					} 
					catch (ParseException e1) 
					{
						e1.printStackTrace();
					}
					
					//QUERY PARA SA ORDER DETAILS
					try 
					{
						int rows=tblOrderDetails.getRowCount();
						String query = "INSERT INTO ORDER DETAIL(ItemNo, Quantity,InvoiceNo) values (?,?,?)";
						Connection con = DriverManager.getConnection("jdbc:ucanaccess://BasicEcommerce.accdb");
						PreparedStatement pst = con.prepareStatement(query);
						
						for(int row = 0; row<rows; row++)
						{
							String itmNo = (String)tblOrderDetails.getValueAt(row, 0);
							int qt = (Integer)tblOrderDetails.getValueAt(row, 3);
							pst.setString(1, itmNo);
							pst.setInt(2, qt);
							pst.setString(3, txtInvoice.getText());
							pst.addBatch();
							Statement stmt = con.createStatement();
							String query1 = "SELECT Quantity FROM INVENTORY WHERE ItemNo="+itmNo;
							ResultSet invent = stmt.executeQuery(query1);
							while(invent.next())
							{
								int quantity = invent.getInt("Quantity");
								String query2 = "UPDATE INVENTORY SET Quantity=? WHERE ItemNo=?";
								PreparedStatement invent1 = con.prepareStatement(query2);
								invent1.setInt(1, quantity-qt);
								invent1.setString(2, itmNo);
								invent1.execute();
							}
						}
						pst.executeBatch();
						con.commit();
					}
					catch(SQLException ex)
					{
						JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null,"Order Added", "Success!", JOptionPane.INFORMATION_MESSAGE);
					btnFinish.setEnabled(false);
					dispose();
				}				
			}
		});//finish transactions
		scrollpaneOrderDet.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				btnDelete.setEnabled(true);
				
			}
		});
	}//event handlers
}//create order class
