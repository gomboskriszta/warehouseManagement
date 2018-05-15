package PresentationLayer;

import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import BusinessLayer.ClickListener;
/**
 * Clasa ce extinde JFrame si contine anumite componente Swing neceare functionarii aplicatiei. 
 * De asemenea contine si panelurile in care se afiseaza clientii, produsele si comenzile
 * @author Gombos Kriszta
 */
public class MainFrame extends JFrame {
	

	private static final long serialVersionUID = 1L;	
	private JMenu orders, products, customers;
	private JLabel frameBg;
	private ProductsPanel productsPanel;
	private CustomersPanel customersPanel;
	private OrdersPanel ordersPanel;
	private CartPanel cartPanel;
	private JMenuBar menu;
	
	/**
	 * Constructorul clasei in care se initializeaza componentele Swing necesare cat si obiectele de tipul CustomersPanel, ProductPanel, OrdersPanel, CartPanel 
	 */
	public MainFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(605, 730);	
		this.setTitle("Order Management");
		this.setLayout(null);

		productsPanel = new ProductsPanel();
		customersPanel = new CustomersPanel();
		ordersPanel = new OrdersPanel();
		cartPanel = new CartPanel();
		products = new JMenu("Products");
		customers = new JMenu("Customers");
		orders = new JMenu("Orders");
		frameBg = new JLabel(new ImageIcon("C:\\Users\\Kriszta\\Desktop\\bg2.jpg"));
		menu = new JMenuBar();
		
		productsPanel.setBounds(30, 20, 530, 620);
		customersPanel.setBounds(30, 20, 530, 620);
		ordersPanel.setBounds(30, 20, 530, 620);
		cartPanel.setBounds(598, 257, 200, 382);
		frameBg.setBounds(0, 0, 1200, 700);

		menu.add(products);
		menu.add(customers);
		menu.add(orders);
		setJMenuBar(menu);
		add(productsPanel);
		add(customersPanel);
		add(ordersPanel);
		add(cartPanel);
		add(frameBg);
		
		productsPanel.setVisible(false);
		customersPanel.setVisible(false);
		ordersPanel.setVisible(false);
		cartPanel.setVisible(false);
	}


	/**
	 * metoda prin care se adauga MouseListener meniurilor prezente in bara de meniu pentru a fi tratate de obiectul dat ca parametru
	 * @param clickListener obiectul ce trateaza actiunile
	 */
	public void addClickListeners(ClickListener clickListener){
		products.addMouseListener((MouseListener) clickListener);
		customers.addMouseListener((MouseListener) clickListener);
		orders.addMouseListener((MouseListener) clickListener);
	}
	
	/**
	 * metoda prin se afiseaza doar panelul corepunzator produselor
	 */
	public void showProductsPanel() {
		this.setSize(605, 730);		
		productsPanel.setVisible(true);
		customersPanel.setVisible(false);
		ordersPanel.setVisible(false);
		cartPanel.setVisible(false);
	}

	/**
	 * metoda prin se afiseaza doar panelul corepunzator clientilor
	 */
	public void showCustomersPanel() {
		this.setSize(605, 730);
		productsPanel.setVisible(false);
		customersPanel.setVisible(true);
		ordersPanel.setVisible(false);	
		cartPanel.setVisible(false);

	}

	/**
	 * metoda prin se afiseaza panelul corespunzator comenzilor, cat si panulul corespunzator cosului de produse specific comenzilor
	 */
	public void showOrdersPanel() {
		this.setSize(850, 730);
		productsPanel.setVisible(false);
		customersPanel.setVisible(false);
		ordersPanel.setVisible(true);
		cartPanel.setVisible(true);
	}
	
	/**
	 * metode ce returneaza panelul de produse
	 * @return productsPanel
	 */
	public ProductsPanel getProductsPanel(){
		return this.productsPanel;
	}

	/**
	 * metode ce returneaza panelul de clienti
	 * @return customersPanel
	 */
	public CustomersPanel getCustomersPanel(){
		return this.customersPanel;
	}

	/**
	 * metode ce returneaza panelul de comenzi
	 * @return ordersPanel
	 */
	public OrdersPanel getOrdersPanel(){
		return this.ordersPanel;
	}

	/**
	 * metode ce returneaza panelul reprezentat de cosul de produse
	 * @return cartPanel
	 */
	public CartPanel getCartPanel (){
		return this.cartPanel;
	}
}
