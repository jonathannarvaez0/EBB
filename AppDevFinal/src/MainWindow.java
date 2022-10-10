import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class MainWindow extends JFrame 
{
	private JPanel contentPane;
	private JPanel pnlTitle;
	private JButton btnCreate;
	private JButton btnInventory;
	private JButton btnTHistory;
	private JPanel btnpnl;
	private JScrollPane scrlpnAbout;
	private JLabel lblAbout;
	private JTextPane txtAbout;
	private JLabel lblIcon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}


	public MainWindow()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/resources/icon3.png")));
		Components();
		Events();
	}
	
	public void Components()
	{
		setTitle("E-BentaBase");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				String ObjButtons[] = {"Yes", "No"};
				int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "E-BentaBase", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if(PromptResult == 0)
				{
					System.exit(0);
				}
			}
		});
		setBounds(400, 150, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		pnlTitle = new JPanel();
		pnlTitle.setBackground(SystemColor.menu);
		pnlTitle.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
		
		
		
		btnpnl = new JPanel();
		btnpnl.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.BLACK, null));
		
		scrlpnAbout = new JScrollPane();
		scrlpnAbout.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.BLACK, null));
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnlTitle, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(btnpnl, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrlpnAbout, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlTitle, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrlpnAbout, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
						.addComponent(btnpnl, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		lblAbout = new JLabel("About");
		lblAbout.setFont(new Font("Adobe Arabic", Font.BOLD | Font.ITALIC, 30));
		lblAbout.setHorizontalAlignment(SwingConstants.CENTER);
		scrlpnAbout.setColumnHeaderView(lblAbout);
		
		txtAbout = new JTextPane();
		txtAbout.setToolTipText("About the Application");
		txtAbout.setText("E-BentaBase is a basic database system that would help people who still use Excel, Spreadsheets, Google Docs, and Manual listing of inventory and sales\r\nfor their online stores and home-based businesses. This app would help simplify tasks regarding to monitoring their sales, invoices, transactions, and their\r\ntotal inventory for their items, products, and services. Our goal for this app is to be user friendly to all ages and people who are not that used to the modern ways of\r\ninventory and transactions systems.");
		txtAbout.setEditable(false);
		txtAbout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAbout.setBackground(SystemColor.menu);
		scrlpnAbout.setViewportView(txtAbout);
		
		btnCreate = new JButton("     Create Order");
		btnCreate.setHorizontalAlignment(SwingConstants.LEFT);
		btnCreate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ImageIcon schedimg = new ImageIcon(MainWindow.class.getResource("/resources/createorder128.png"));
		Image img = schedimg.getImage();
		Image modImg = img.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
		schedimg = new ImageIcon(modImg);
		btnCreate.setIcon(schedimg);
		btnCreate.setFocusPainted(false);
		
		btnInventory = new JButton("       Inventory");
		btnInventory.setHorizontalAlignment(SwingConstants.LEFT);
		btnInventory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ImageIcon invimg = new ImageIcon(MainWindow.class.getResource("/resources/checkinventory128.png"));
		Image invimg1 = invimg.getImage();
		Image modinvimg = invimg1.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
		invimg = new ImageIcon(modinvimg);
		btnInventory.setIcon(invimg);
		btnInventory.setFocusPainted(false);
		
		btnTHistory = new JButton(" Transaction History");
		btnTHistory.setHorizontalAlignment(SwingConstants.LEFT);
		btnTHistory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ImageIcon thisimg = new ImageIcon(MainWindow.class.getResource("/resources/transactionhistory128.png"));
		Image thisimg1 = thisimg.getImage();
		Image modthisimg = thisimg1.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
		thisimg = new ImageIcon(modthisimg);
		btnTHistory.setIcon(thisimg);
		btnTHistory.setFocusPainted(false);
		
		GroupLayout gl_btnpnl = new GroupLayout(btnpnl);
		gl_btnpnl.setHorizontalGroup(
			gl_btnpnl.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_btnpnl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_btnpnl.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnCreate, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.addComponent(btnTHistory, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnInventory, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_btnpnl.setVerticalGroup(
			gl_btnpnl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_btnpnl.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnCreate, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnInventory, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTHistory, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addContainerGap())
		);
		btnpnl.setLayout(gl_btnpnl);
		
		lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/icon2.png")));
		lblIcon.setForeground(new Color(250, 128, 114));
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setFont(new Font("Century Gothic", Font.BOLD, 28));
		GroupLayout gl_pnlTitle = new GroupLayout(pnlTitle);
		gl_pnlTitle.setHorizontalGroup(
			gl_pnlTitle.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTitle.createSequentialGroup()
					.addGap(8)
					.addComponent(lblIcon, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(8))
		);
		gl_pnlTitle.setVerticalGroup(
			gl_pnlTitle.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlTitle.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblIcon, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
					.addContainerGap())
		);
		pnlTitle.setLayout(gl_pnlTitle);
		
		contentPane.setLayout(gl_contentPane);
	}
	
	public void Events()
	{
		btnInventory.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				InventoryUI invui = new InventoryUI();
				invui.setVisible(true);
			}
		});//INVENTORY
		btnCreate.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				CreateOrder crorder = new CreateOrder();
				crorder.setVisible(true);
			}
		});
		btnTHistory.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				TransactionHistory thistory = new TransactionHistory();
				thistory.setVisible(true);
			}
		});
	}
}
