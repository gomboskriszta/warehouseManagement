package PresentationLayer;

import java.awt.Component;
import java.awt.event.MouseListener;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import BusinessLayer.ClickListener;
import Model.Customer;
import Model.OPDept;
import Model.Order;
import Model.Product;
/**
 * Clasa ce extinde superclasa JPanel si ce contine anumite componente Swing necesare afisarii, inserarii si stergerii unei comenzi
 * @author Gombos Kriszta
 */
public class OrdersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private TableRowSorter<DefaultTableModel> sorter;
	private JTextField minPrice, maxPrice, customerName, quantity, orderID;
	private JLabel filterLabel, panelBg, quantityLabel; 
	private JButton searchByCustomerButton, searchByPriceButton, resetFilters, addOrder,
			addToOrder, deleteButton;
	private JScrollPane tableScroll;
	private JComboBox<Customer> customerCombo;
	private JComboBox<Product> productCombo;
	
	
	public static void retrieveProperties(Object object) {

		for (Field field : object.getClass().getDeclaredFields()) {
			field.setAccessible(true); // set modifier to public
			Object value;
			try {
				value = field.get(object);
				System.out.println(field.getName() + "=" + value);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
	}
	
	JTable createTable(List<Object> objects) {
		
		Field[] fields   = objects.get(0).getClass().getDeclaredFields();
		String[] colName = {fields[0].getName(), fields[1].getName(), fields[2].getName()};
		DefaultTableModel model = new DefaultTableModel(colName, 0);
		return new JTable(model);
	}
	
	/**
	 * Constructorul clasei in care se declara si initializeaza compenenteleSwing necesare
	 */
	public OrdersPanel(){
		setLayout(null);
		List<Object> objects = new ArrayList<Object>();
		objects.add(new Order(0, null));
		table = createTable(objects);
		table.setFillsViewportHeight(true);
		tableScroll = new JScrollPane(table); 
		filterLabel = new JLabel("<html>Search by customer name: <br><br><br>Search by price range: "
				+ "<br><br><br><br><br>"
				+ "________________________________________________________________"
				+ "<br>Insert a new order: <br><br><br><br><br><br><br><br>"
				+ "________________________________________________________________"
				+ "<br><br><br>Delete an order (OrderID): </html>");
		quantityLabel = new JLabel("Quantity: ");
		searchByCustomerButton = new JButton("Search by Customer");
		searchByPriceButton = new JButton("Search by Price");
		resetFilters = new JButton ("Reset filters");
		addOrder = new JButton ("Add order");
		deleteButton = new JButton("Delete order");
		addToOrder = new JButton("Add to cart");
		customerName = new JTextField();
		minPrice = new JTextField();
		maxPrice = new JTextField();
		quantity = new JTextField();
		orderID = new JTextField();
		productCombo = new JComboBox<Product>();
		customerCombo = new JComboBox<Customer>();
		panelBg = new JLabel(new ImageIcon("C:\\Users\\Kriszta\\Desktop\\background.jpg"));
		
		filterLabel.setBounds(35, 235, 465, 350);		
		customerName.setBounds(200, 240, 120, 25);		
		searchByCustomerButton.setBounds(340, 237, 150, 30);		
		minPrice.setBounds(200, 290, 50, 25);		
		maxPrice.setBounds(270, 290, 50, 25);
		searchByPriceButton.setBounds(340, 287, 150, 30);
		resetFilters.setBounds(200, 335, 120, 30);
		addOrder.setBounds(350, 468, 135, 30);
		addToOrder.setBounds(350, 418, 135, 30);
		deleteButton.setBounds(350, 557, 135, 30);
		customerCombo.setBounds(35, 470, 285, 25);
		productCombo.setBounds(35, 420, 130, 25);
		quantity.setBounds(270, 420, 50, 25);
		orderID.setBounds(230, 560, 70, 25);
		quantityLabel.setBounds(200, 420, 100, 25);
		tableScroll.setBounds(40, 30, 450, 180);
		panelBg.setBounds(0, 0, 700, 700);
		
		add(customerName);
		add(filterLabel);
		add(searchByCustomerButton);
		add(minPrice);
		add(maxPrice);
		add(searchByPriceButton);	
		add(resetFilters);
		add(addOrder);
		add(addToOrder);
		add(deleteButton);
		add(quantity);
		add(quantityLabel);
		add(orderID);
		add(tableScroll);
		add(customerCombo);
		add(productCombo);
		add(panelBg);
	} 
	
	/**
	 * metoda prin care se aduaga MouseListener butoanelor prezente in acest panel
	 * @param clickListener obiectul ce prelucreaza actiunile
	 */
	public void addClickListeners(ClickListener clickListener){
		searchByCustomerButton.addMouseListener((MouseListener) clickListener);
		searchByPriceButton.addMouseListener((MouseListener) clickListener);
		addOrder.addMouseListener((MouseListener) clickListener);
		addToOrder.addMouseListener((MouseListener) clickListener);
		deleteButton.addMouseListener((MouseListener) clickListener);
		resetFilters.addMouseListener((MouseListener) clickListener);
	}

	/**
	 * metoda prin care se returneaza componenta JTable ce contine comenzile facute
	 * @return table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * metoda ce updateaza continutul tabelului de comenzi la valoarea referita de obiectul orders
	 * @param orders situatia actuala a comenzilor
	 */
	public void updateOrders(OPDept orders) {
		String[] colName = { "OrderID", "Customer", "TotalPrice", "View Order" };
		DefaultTableModel model = new DefaultTableModel(colName, 0);
		table.setModel(model);
		for (Order order : orders.getOrders()) {

			Object[] row = { "" + order.getOrderID() + "", order.getCustomer().getName(), order.getTotalPrice(), order };
			model = (DefaultTableModel) table.getModel();
			
			try {
				model.addRow(row);
			} catch (IndexOutOfBoundsException e) {
			}
		}
		
		sorter = new TableRowSorter<DefaultTableModel>(model);
		table.setRowSorter(sorter);
		table.getColumn("View Order").setMaxWidth(100);
		table.getColumn("View Order").setPreferredWidth(100);
		table.getColumn("View Order").setCellEditor(
				new DefaultCellEditor(new JCheckBox()) {
					private static final long serialVersionUID = 1L;
					public Component getTableCellEditorComponent(JTable table,
							Object value, boolean isSelected, int row, int column) {
						OrderFrame orderFrame = new OrderFrame((Order) value);
						orderFrame.setVisible(true);
						return null;
					}
				}); 

	}
	
	/**
	 * metoda prin care se seteaza clientii prezenti in combobox la valorile referite de parametru
	 * @param customers situatia actuala a clientilor
	 */
	public void setCustomersCombo(ArrayList<Customer> customers){
		customerCombo.removeAllItems();
		for (Customer customer : customers)
			customerCombo.addItem(customer);
	}

	/**
	 * metoda prin care se seteaza produsele din combobox la valorile referite de parametru
	 * @param products situatia actuala a produselor
	 */
	public void setProductsCombo(ArrayList<Product> products){
		productCombo.removeAllItems();
		for (Product product : products)
			productCombo.addItem(product);
	}

	/**
	 * metoda prin care se filtreaza comenzile in functie de numele clientului referit de utilizator
	 */
	public void searchByCustomer() {
		sorter.setRowFilter(RowFilter.regexFilter(customerName.getText(), 1));
	}
	
	/**
	 * metoda prin care se filtreaza comenzile in functie de costul total al comenzii, un interval dat de utilizator
	 */
	public void searchByPrice() {
		RowFilter<DefaultTableModel, Object> rf = null;
		try {
			RowFilter<DefaultTableModel, Object> low = RowFilter.numberFilter(ComparisonType.AFTER, Integer.parseInt(minPrice.getText()), 2);
			RowFilter<DefaultTableModel, Object> high = RowFilter.numberFilter(ComparisonType.BEFORE, Integer.parseInt(maxPrice.getText()), 2);
			ArrayList<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<RowFilter<DefaultTableModel, Object>>();
			filters.add(low);
			filters.add(high);
			rf = RowFilter.andFilter(filters);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please insert a valid price range", "Error", JOptionPane.ERROR_MESSAGE);
			minPrice.setText("");
			maxPrice.setText("");
		}
		sorter.setRowFilter(rf);
	}
	
	/**
	 * metoda prin care se reseteaza toate filtrele
	 */
	public void resetFilters(){
		sorter.setRowFilter(null);
		customerName.setText("");
		minPrice.setText("");
		maxPrice.setText("");
	}
	
	/**
	 * metoda ce returneaza cantitatea furnizata de utilizator in vederea adaugarii in cos a unui produs
	 * @return quantity
	 */
	public Integer getQuantity (){
		Integer productQuantity = null;
		try {
			productQuantity = Integer.parseInt(quantity.getText());
		}
		catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Insert a valid value for quantity", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		quantity.setText("");
		if (productQuantity > 0) return productQuantity;
		else
		{
			JOptionPane.showMessageDialog(null, "Insert a valid value for quantity", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	/**
	 * metoda ce returneaza id-ul unei comenzi, specificat de utilizator in vederea stergerii unei comenzi
	 * @return orderID
	 */
	public Integer getOrderID() {
		Integer ID = null;
		try {
			ID = Integer.parseInt(orderID.getText());
		}
		catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Insert a valid value for orderID", "Error", JOptionPane.ERROR_MESSAGE);
		}
		orderID.setText("");
		return ID;
	}

	/**
	 * metoda ce returneaza produsul selectat in comboBox pentru adaugarea unui produs in cos
	 * @return product
	 */
	public Product getProduct() {
		return (Product) productCombo.getSelectedItem();
	}

	/**
	 * metoda ce returneaza clientul selectat in comboBox in vederea introducerii unei comenzi
	 * @return customer
	 */
	public Customer getCustomer() {
		return (Customer) customerCombo.getSelectedItem();
	}	
}
