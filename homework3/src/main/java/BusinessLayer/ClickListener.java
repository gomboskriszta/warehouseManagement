package BusinessLayer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JMenu;
/**
 * Clasa implementeaza interfata MouseListener si va fi adaugata tuturor butoanelor din aplicatie pentru a gestiona evenimentele acestora
 * @author Gombos Kriszta
 */
public class ClickListener implements MouseListener {
	private Controller controller;
	private String contentPane;
	
	/**
	 * Contructorul clasei ClickListener care capteaza evenimentele venite de la mouse, implementand interfata MouseListener
	 * @param controller evenimentele activeaza metoda din clasa Controller
	 * @param contentPane panelul sau frame-ul din care este captat evenimentul pentru a fi tratate diferit in functie de locul de unde a fost apasat butonul
	 */
	public ClickListener (Controller controller, String contentPane){
		this.controller = controller;
		this.contentPane = contentPane;
	}

	/**
	 * metoda ce se activeaza in momentul apasarii mouse-ului pentru un obiect ce are adaugat eventul respectiv
	 */
	public void mouseClicked(MouseEvent e) {
		String evt = null;
		try {
			JButton button = (JButton) e.getComponent();
			evt = new String(button.getText());
		}
		catch(ClassCastException err){
			JMenu menu = (JMenu) e.getComponent();
			evt = new String(menu.getText());
		}		
		switch (contentPane) {
		case "Frame":
			switch (evt) {
			case "Products":
				controller.getView().showProductsPanel();
				break;
			case "Customers":
				controller.getView().showCustomersPanel();
				break;
			case "Orders":
				controller.getView().showOrdersPanel();
				break;
			}
			break;

		case "ProductsPanel":
			switch (evt) {
			case "Search by Name":
				controller.getView().getProductsPanel().searchByName();
				break;
			case "Search by Quantity":
				controller.getView().getProductsPanel().searchByQuantity();
				break;
			case "Search by Price":
				controller.getView().getProductsPanel().searchByPrice();
				break;
			case "Reset filters":
				controller.getView().getProductsPanel().resetFilters();
				break;
			case "Add product":
				controller.addProduct();
				break;
			case "Delete product":
				controller.deleteProduct();
				break;
			}
			break;

		case "OrdersPanel":
			switch (evt) {
			case "Search by Customer":
				controller.getView().getOrdersPanel().searchByCustomer();
				break;
			case "Search by Price":
				controller.getView().getOrdersPanel().searchByPrice();
				break;
			case "Reset filters":
				controller.getView().getOrdersPanel().resetFilters();
				break;
			case "Add order":
				controller.addOrder();
				break;
			case "Add to cart":
				controller.addToOrder();
				break;
			case "Delete order":
				controller.deleteOrder();
				break;
			}
			break;

		case "CustomersPanel":
			switch (evt) {
			case "Search by Name":
				controller.getView().getCustomersPanel().searchByName();
				break;
			case "Search by Address":
				controller.getView().getCustomersPanel().searchByAddress();
				break;
			case "Search by Email":
				controller.getView().getCustomersPanel().searchByEmail();
				break;
			case "Reset filters":
				controller.getView().getCustomersPanel().resetFilters();
				break;
			case "Add customer":
				controller.addCustomer();
				break;
			case "Delete customer":
				controller.deleteCustomer();
				break;
			}
			break;
		case "CartPanel":
			switch (evt) {
			case "Remove All":
				controller.removeProductsFromCart();
				break;
			}
			break;
		}

	}

	/**
	 * metoda ce se activeaza in cazul intrarii cursorului in regiunea obiectului ce are adaugata actiunea
	 */
	public void mouseEntered(MouseEvent e) {}
	/**
	 * metoda ce se activeaza in momentul iesirii din regiunea obiectului ce are adaugata actiunea
	 */
	public void mouseExited(MouseEvent e) {}
	/**
	 * metoda ce se activeaza in momentul apasarii butonului mouse-ului
	 */
	public void mousePressed(MouseEvent e) {}
	/**
	 * metoda ce se activeaza cand butonul mouse-ului este eliberat
	 */
	public void mouseReleased(MouseEvent e) {}
	
}
