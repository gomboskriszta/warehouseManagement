package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import Model.Customer;
import Model.Order;
import Model.Product;
/**
 * Clasa ce realizeaza conexiunea cu baza de date si metodele specifice pentru functionarea aplicatiei
 * @author Gombos Kriszta
 */
public class DBConnection {

	static final String DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/Warehouse";
	static final String USER = "root";
	static final String PASS = "Pitagorasz*1";
	private Connection connection;

	/**
	 * Constructrul conexiunii la baza de date. In constructor se face legatura cu baza de date
	 * prin conectorul mysql Driver si datele specificare pentru adresa URL, user si parola
	 */
	public DBConnection() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * metoda ce insereaza un obiect de tipul Product in baza de date, in tabelul 'Products'
	 * @param product produsul care este inserat in baza de date
	 */
	public void insertProduct(Product product) {
		try {
			Statement insertStmt = connection.createStatement();
			insertStmt.execute("INSERT INTO Products (ProductID, Name, Price) VALUES " + "(" 
					+ product.getProductID() + ", '" + product.getName() + "', " + product.getPrice() + ")");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * metoda ce updateze datele din baza de date referitoare la produsul dat ca parametru la noile atribute ale acestuia
	 * @param product produsul care este modificat in baza de date
	 */
	public void updateProduct(Product product) {
		try {
			Statement updateStmt = connection.createStatement();
			updateStmt.execute("UPDATE Products set Name = '" + product.getName() + "', Price = " 
					+ product.getPrice() + " where ProductID = " + product.getProductID());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * metoda prin care se sterge produsul dat ca parametru din baza de date
	 * @param product produsul ce va fi sters
	 */
	public void deleteProduct(Product product) {
		try {
			Statement deleteStmt = connection.createStatement();
			deleteStmt.execute("Delete from Products where ProductID = " + product.getProductID());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * metoda ce insereaza un client in baza de date, in tabelul 'Customers'
	 * @param customer clientul care este inserat in baza de date
	 */
	public void insertCustomer(Customer customer) {
		try {
			Statement insertStmt = connection.createStatement();
			insertStmt.execute( "INSERT INTO Customers (CustomerID, Name, Address, Email) VALUES "
					+ "(" + customer.getCustomerID() + ", '" + customer.getName() + "', '"
					+ customer.getAddress() + "', '" + customer.getEmail() + "')");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * metoda ce modifica datele din baza de date referitoare la clientul dat ca parametru la noile atribute ale acestuia
	 * @param customer customer care este modificat in baza de date
	 */
	public void updateCustomer(Customer customer) {
		try {
			Statement updateStmt = connection.createStatement();
			updateStmt.execute( "UPDATE Customers set Name = '" + customer.getName() + "', Address = '" 
					+ customer.getAddress() + "', Email = '" + customer.getEmail() 
					+ "' where CustomerID = " + customer.getCustomerID());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}	

	/**
	 * metoda prin care se sterge clientul dat ca parametru din baza de date
	 * @param customer clientul ce va fi sters
	 */
	public void deleteCustomer(Customer customer) {
		try {
			Statement deleteStmt = connection.createStatement();
			deleteStmt.execute( "Delete from Customers where CustomerID = " + customer.getCustomerID());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * metoda prin care se insereaza o comanda in baza de date. Mai intai se insereaza datele referitoare
	 * la id-ul comenzii, id-ul clientului si pretul final in tabelul Orders, iar apoi se insereaza
	 * produsele si cantitatile ce compun comanda in tabelul OrdersProducts.
	 * @param order comanda care este inserata in baza de date
	 */
	public void insertOrder(Order order) {
		try {
			Statement insertOrderStmt = connection.createStatement();
			insertOrderStmt.execute("INSERT INTO Orders (OrderID, CustomerID, TotalPrice) VALUES "
					+ "(" + order.getOrderID() + ", " + order.getCustomer().getCustomerID()
					+ ", " + order.getTotalPrice() + ")");

			for (int i = 0; i < order.getProducts().size(); i++) {
				Statement insertProductStmt = connection.createStatement();
				insertProductStmt.execute("INSERT INTO OrderProducts (OrderID, ProductID, Quantity) VALUES "
						+ "(" + order.getOrderID() + ", " + order.getProduct(i).getProductID()
						+ ", " + order.getQuantity(i) + ")");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * metoda prin care se sterge comanda data ca parametru din baza de date
	 * @param order comanda ce va fi stearsa
	 */
	public void deleteOrder(Order order) {
		try {
			Statement deleteStmt = connection.createStatement();
			deleteStmt.execute("Delete from Orders where OrderID = " + order.getOrderID());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * metoda prin care se modifica valoarea cantitatii din baza de date a produsului dat ca parametru la valoarea specificata
	 * @param product produsul a carui cantitate se modifica
	 * @param newQuantity noua cantitate a produsului
	 */
	public void updateQuantity(Product product, Integer newQuantity) {
		try {
			Statement updateStmt = connection.createStatement();
			updateStmt.execute("UPDATE Stock set Quantity = " + newQuantity 
					+ " where ProductID = " + product.getProductID());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * metoda ce extrage datele din tabelul 'Clienti', construieste obiecte de tipul Customer,
	 * iar apoi le insereaza intr-un ArrayList de obiecte Customer pe care il returneaza
	 * @return customers
	 */
	public ArrayList<Customer> extractCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
			Statement selectStmt = connection.createStatement();
			ResultSet result = selectStmt.executeQuery("Select * from Customers");
			while (result.next())
				customers.add(new Customer((int) result.getObject(1), (String) result.getObject(2), 
						(String) result.getObject(3), (String) result.getObject(4)));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return customers;
	}

	/**
	 * metoda ce extrage datele din tabelul 'Products', construieste obiectele 'Product', iar
	 * apoi le insereaza intr-un ArrayList de obiecte Product pe care il returneaza
	 * @return products
	 */
	public ArrayList<Product> extractProducts() {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			Statement selectStmt = connection.createStatement();
			ResultSet result = selectStmt.executeQuery("Select * from Products");
			while (result.next())
				products.add(new Product((int) result.getObject(1), (String) result.getObject(2), (double) result.getObject(3)));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return products;
	}

	/**
	 * metoda ce extrage datele din tabelul 'Products' si 'Stock'. Se selecteaza si creeaza obiectul 
	 * Product si cantitatea acestuia, iar apoi se insereaza intr-un TreeMap de tipul 
	 * Product - Cantitate(Integer) ce se returneaza
	 * @return stock
	 */	
	public TreeMap<Product, Integer> extractStock() {
		TreeMap<Product, Integer> stock = new TreeMap<Product, Integer>();
		try {
			Statement selectStmt = connection.createStatement();
			ResultSet result = selectStmt.executeQuery("SELECT Products.ProductID, Name, Price, "
					+ "Quantity FROM Products, Stock where Products.ProductID = Stock.ProductID");
			while (result.next()) {
				Product product = new Product ((int) result.getObject(1), (String) result.getObject(2), (double) result.getObject(3));
				stock.put(product, (Integer) result.getObject(4));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return stock;
	}

	/**
	 * metoda ce extrage datele din tabelul 'Orders' si 'OrderProducts'.
	 * Se creeaza obiectele Order si se insereaza intr-un TreeSet de obiecte Order ce se returneaza
	 * @return orders
	 */	
	public TreeSet<Order> extractOrders() {
		TreeSet<Order> orders = new TreeSet<Order>();
		Statement stmtOrders, stmtOrderProducts;
		ResultSet resOrders, resOrderProducts;

		try {
			stmtOrders = connection.createStatement();
			resOrders = stmtOrders.executeQuery("SELECT OrderID, Orders.CustomerID, Name, Address, "
					+ "Email from Orders, Customers where Orders.CustomerID = Customers.CustomerID");
			while (resOrders.next()) {
				Customer customer = new Customer((int) resOrders.getObject(2), (String) resOrders.getObject(3), 
						(String) resOrders.getObject(4), (String) resOrders.getObject(5));
				orders.add(new Order((int) resOrders.getObject(1), customer));
			}

			stmtOrderProducts = connection.createStatement();
			resOrderProducts = stmtOrderProducts.executeQuery("SELECT OrderID, Products.ProductID, Name, Price, "
					+ "Quantity from OrderProducts, Products where OrderProducts.ProductID = Products.ProductID");
			while (resOrderProducts.next()) {
				int orderID = (int) resOrderProducts.getObject(1);
				Product product = new Product ((int) resOrderProducts.getObject(2), 
						(String) resOrderProducts.getObject(3), (double) resOrderProducts.getObject(4));
				Integer quantity = (Integer) resOrderProducts.getObject(5);
				for (Order order : orders)
					if (order.getOrderID() == orderID)
						order.addProductToOrder(product, quantity);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return orders;
	}

}
