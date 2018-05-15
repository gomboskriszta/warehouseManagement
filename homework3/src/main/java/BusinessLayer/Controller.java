package BusinessLayer;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import DataAccessLayer.DBConnection;
import PresentationLayer.MainFrame;
import Model.Customer;
import Model.OPDept;
import Model.Order;
import Model.Product;
import Model.Warehouse;
/**
 * Clasa Controller din design patternul Model-View-Controller, ce controleaza evenimentele captate 
 * din interfata prin clasa ClickListener, folosind metodele implementate in clasele ce apartin de model
 * @author Gombos Kriszta
 */
public class Controller {

	private MainFrame view;
	private DBConnection db;
	private ArrayList<Customer> customers;
	private Warehouse warehouse;
	private OPDept orders;
	private ArrayList<Product> productsToAdd;
	private ArrayList<Integer> quantitiesToAdd;

	/**
	 * Constructorul clasei view are ca parametrii interfata pe care o controleaza si conexiunea la baza de date.
	 * Tot in contructor se initializeaza componentele necesare functionarii aplicatiei si se adauga actiuni pentru butoane si pentru celule tabelului
	 * @param view interfata controlata, reprezentata de un obiect MainFrame
	 * @param db conexiunea la baza de date prin care se vor extrage clientii, produsele si comenzile
	 */
	public Controller(MainFrame view, DBConnection db) {
		this.view = view;
		this.db = db;
		warehouse = new Warehouse(this.db.extractStock());
		orders = new OPDept(this.db.extractOrders());
		customers = db.extractCustomers();
			
		view.addClickListeners(new ClickListener(this, "Frame"));
		view.getProductsPanel().addClickListeners(new ClickListener(this, "ProductsPanel"));
		view.getCustomersPanel().addClickListeners(new ClickListener(this, "CustomersPanel"));
		view.getOrdersPanel().addClickListeners(new ClickListener(this, "OrdersPanel"));
		view.getCartPanel().addClickListeners(new ClickListener(this, "CartPanel"));
		
		view.getProductsPanel().updateWarehouse(warehouse);
		view.getCustomersPanel().updateCustomers(customers);
		view.getOrdersPanel().updateOrders(orders);
		view.getOrdersPanel().setCustomersCombo(customers);
		view.getOrdersPanel().setProductsCombo(db.extractProducts());
		
		new TableCellListener(view.getProductsPanel().getTable(), new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				updateProduct(e);
			}
		});		
		
		new TableCellListener(view.getCustomersPanel().getTable(), new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				updateCustomer(e);
			}
		});		
		
		new TableCellListener(view.getOrdersPanel().getTable(), new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				view.getOrdersPanel().updateOrders(orders);
			}
		});		
		productsToAdd = new ArrayList<Product>();
		quantitiesToAdd = new ArrayList<Integer>();
		view.showProductsPanel();
		
	}

	/**
	 * metoda ce returneaza interfata ce tine de controller pentru a putea fi accesata de alte clase
	 * @return view
	 */
	public MainFrame getView() {
		return this.view;
	}	
	
	/**
	 * metoda ce se apeleaza in momentul apasarii butonului pentru adaugarea unui nou produs.
	 * Metoda acceseaza datele din interfata, verifica daca acestea sunt corecte, iar apoi
	 * creeaza obiectul Product pe care il adauga la arborele ce compune depozitul si la baza de date
	 */
	public void addProduct() {	
		String name = view.getProductsPanel().getProductName();
		Integer quantity = view.getProductsPanel().getProductQuantity();
		Double price = view.getProductsPanel().getProductPrice();
		int productID;
		if (quantity == null || quantity < 0)
			quantity = 0;
		if (name != null && price != null)
		{
			if (warehouse.isEmpty()) productID = 1;
			else productID = warehouse.getStock().lastKey().getProductID() + 1;
			Product product = new Product(productID, name, price);
			warehouse.addProduct(product, quantity);
			db.insertProduct(product);	
			db.updateQuantity(product, quantity);
			view.getProductsPanel().updateWarehouse(warehouse);
			view.getOrdersPanel().setProductsCombo(db.extractProducts());
		} 
	}

	/**
	 * metoda prin care se sterge un produs atat din depozit cat si din baza de date, iar apoi se fac updatarile necesare pentru interfata
	 */
	public void deleteProduct() {
		Product product = view.getProductsPanel().getProduct();
		if (product != null){
			warehouse.deleteProduct(product);
			db.deleteProduct(product);
			view.getProductsPanel().updateWarehouse(warehouse);
			orders = new OPDept(db.extractOrders());
			view.getOrdersPanel().updateOrders(orders);
			view.getOrdersPanel().setProductsCombo(db.extractProducts());
		}
		else JOptionPane.showMessageDialog(null, "The product does not exist in database", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * metoda care capteaza evenimentul realizat in momentul modificarii unei celule din tabelul de produse din interfeta
	 * in functie de coloana modificata se fac modificarile necesare in baza de date si in obiectele warehouse sau produs 
	 * Nu sunt acceptate modificarile pentru celule ProductID, UnderStock si OverStock. 
	 * @param e evenimentul care activeaza metoda
	 */
	public void updateProduct(ActionEvent e){
		TableCellListener tcl = (TableCellListener) e.getSource();
		try {
			int productID = Integer.parseInt((String) view.getProductsPanel().getValueAt(tcl.getRow(), 0));
			Product product = warehouse.findByProductID(productID);
			if (product != null && tcl.getColumn() != 0 && tcl.getColumn() != 4 && tcl.getColumn() != 5) {
				product.setName((String) view.getProductsPanel().getValueAt(tcl.getRow(), 1));
			try {
				Double price;
				price = Double.parseDouble((String) view.getProductsPanel().getValueAt(tcl.getRow(), 2));
				if (price > 0)
					product.setPrice(price);
			} catch (ClassCastException err) {}
			try {
				Integer quantity = Integer.parseInt((String) view.getProductsPanel().getValueAt(tcl.getRow(), 3));
				if (quantity >= 0){
					warehouse.setQuantity(product, quantity);
					db.updateQuantity(product, quantity);
				}
			} catch (ClassCastException err) {}
			
			db.updateProduct(product);
			orders = new OPDept(db.extractOrders());
			view.getOrdersPanel().updateOrders(orders);
			view.getOrdersPanel().setProductsCombo(db.extractProducts());
			}		
		}
		catch (NumberFormatException err) {}		
		view.getProductsPanel().updateWarehouse(warehouse);
		
	}
	
	/**
	 * metoda care face verificarile necesare pentru adaugarea unui client, 
	 * apoi creeaza obiectul Customer si apoi il introduce in baza de date
	 */
	public void addCustomer() {
		String name = view.getCustomersPanel().getCustomerName();
		String address = view.getCustomersPanel().getCustomerAddress();
		String email = view.getCustomersPanel().getCustomerEmail();
		int customerID;
		if (customers.isEmpty()) customerID = 1;
		else customerID = customers.get(customers.size() - 1).getCustomerID() + 1;
		if (!name.equals(""))
		{			 
			Customer customer = new Customer(customerID, name, address, email);
			customers.add(customer);
			db.insertCustomer(customer);
			view.getCustomersPanel().updateCustomers(customers);
			view.getOrdersPanel().setCustomersCombo(customers);
		} 
		else JOptionPane.showMessageDialog(null, "Please specify a name for the customer", "Error", JOptionPane.ERROR_MESSAGE);
	}
	

	/**
	 * metoda prin care se sterge un client din baza de date si updateaza tabelele din interfata la noile schimbari
	 */
	public void deleteCustomer() {
		Customer customer = view.getCustomersPanel().getCustomer();
		if (customer != null) {			
			customers.remove(customer);
			db.deleteCustomer(customer);
			view.getCustomersPanel().updateCustomers(customers);
			orders = new OPDept(db.extractOrders());
			view.getOrdersPanel().updateOrders(orders);
			view.getOrdersPanel().setCustomersCombo(customers);
			return;
		}
		else
			JOptionPane.showMessageDialog(null, "The customer does not exist in database", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * metoda care capteaza evenimentul realizat in momentul modificarii unei celule din tabelul de clienti din interfeta
	 * in functie de coloana modificata se fac modificarile necesare in baza de date  
	 * Nu sunt acceptate modificarile pentru celule CustomerID
	 * @param e evenimentul care activeaza metoda
	 */
	public void updateCustomer(ActionEvent e) {
		TableCellListener tcl = (TableCellListener) e.getSource();
		try {
			int customerID = Integer.parseInt((String) view.getCustomersPanel().getValueAt(tcl.getRow(), 0));
			for (Customer customer : customers)
				if (customer.getCustomerID() == customerID && tcl.getColumn() != 0){
					customer.setName((String) view.getCustomersPanel().getValueAt(tcl.getRow(), 1));
					customer.setAddress((String) view.getCustomersPanel().getValueAt(tcl.getRow(), 2));
					customer.setEmail((String) view.getCustomersPanel().getValueAt(tcl.getRow(), 3));
					db.updateCustomer(customer);
					orders = new OPDept(db.extractOrders());
					view.getOrdersPanel().updateOrders(orders);
					view.getOrdersPanel().setCustomersCombo(customers);
					return;
				}
		
		}
		catch (NumberFormatException err) {}		
		view.getCustomersPanel().updateCustomers(customers);
		
	}
	/**
	 * metoda ce se activeaza in momentul apasarii butonului de adaugare a unui produs la comanda
	 * aceasta verifica datele introduse si apoi adauga produsul si cantitatea in cosul clientului
	 */
	public void addToOrder(){
		Product product = view.getOrdersPanel().getProduct();
		Integer quantity = view.getOrdersPanel().getQuantity();
		if (product != null && quantity != null)
			if (warehouse.checkQuantity(product, quantity))	{
				productsToAdd.add(product);
				quantitiesToAdd.add(quantity);
				view.getCartPanel().addProduct(product, quantity);
			}
			else JOptionPane.showMessageDialog(null, "There isn't enough quantity for this product: '" + product.getName() + "'", "Error", JOptionPane.ERROR_MESSAGE);
		else if(product == null)
			JOptionPane.showMessageDialog(null, "The product does not exists in warehouse", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * metoda prin care se verifica datele si apoi se creeaza comanda si o adauga in baza de date
	 * Produsele ce sunt adaugate in cos vor fi produsele ce compun comanda
	 */
	public void addOrder(){
		
		Customer customer = view.getOrdersPanel().getCustomer();
		if (!productsToAdd.isEmpty() && !quantitiesToAdd.isEmpty() && customer != null){
			int orderID;
			if (orders.isEmpty()) orderID = 1;
			else orderID = orders.getOrders().last().getOrderID() + 1;
			Order order = new Order(orderID, customer, productsToAdd, quantitiesToAdd);				
			warehouse.decreaseQuantities(productsToAdd, quantitiesToAdd);
			for (int j = 0; j < productsToAdd.size(); j++)
				db.updateQuantity(productsToAdd.get(j), warehouse.getProductQuantity(productsToAdd.get(j)));
			orders.addOrder(order);
			db.insertOrder(order);
			productsToAdd = new ArrayList<Product>();
			quantitiesToAdd = new ArrayList<Integer>();
			view.getCartPanel().init();
	 		view.getOrdersPanel().updateOrders(orders);
			view.getProductsPanel().updateWarehouse(warehouse);
		}
		else 
			JOptionPane.showMessageDialog(null, "Please add to cart at least a product", "Error", JOptionPane.ERROR_MESSAGE);
		
	}
	
	/**
	 * metoda prin care se sterge o comanda in functie id-ul furnizat in interfata
	 */
	public void deleteOrder(){
		Integer orderID = view.getOrdersPanel().getOrderID();
		if (orderID != null){
			Order order = orders.findByOrderID(orderID);
			if (order != null){
				orders.deleteOrder(order);
				db.deleteOrder(order);
				view.getOrdersPanel().updateOrders(orders);
			}
			else JOptionPane.showMessageDialog(null, "The orderID does not exists in database", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * metoda prin care se sterg produsele din cosul clientului
	 */
	public void removeProductsFromCart() {
		productsToAdd = new ArrayList<Product>();
		quantitiesToAdd = new ArrayList<Integer>();
		view.getCartPanel().init();
	}
}