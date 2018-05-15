package Model;

import java.util.TreeSet;

/**
 * Clasa ce contine toate comenzile realizate intr-un arbore binar de cautare (TreeSet) si implementeaza metodele specifice gestionarii acetora
 * @author Gombos Kriszta
 */
public class OPDept {
	
	private TreeSet<Order> orders;
	/**
	 * Constructorul clasei OPDept primeste ca parametru un arbore binar de cautare compus din comenzile
	 * departamentului de procesare a comenzilor
	 * @param ordoneaza comenzile ce compun departamentul de procesare a comenzilor
	 */
	public OPDept(TreeSet<Order> orders){
		this.orders = orders;
	}

	/**
	 * metoda ce returneaza un TreeSet de obiecte Order, reprezentand comenzile existente
	 * @return orders
	 */
	public TreeSet<Order> getOrders() {
		return orders;
	}

	/**
	 * metoda ce adauga o noua comanda arborelui, referita prin parametru
	 * @param order comanda ce este adaugata
	 */
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	/**
	 * metoda ce elimina o comanda din arborele de cautare
	 * @param order comanda ce este stearsa
	 */
	public void deleteOrder(Order order){
		orders.remove(order);
	}
	
	/**
	 * metoda ce determina daca arborele de cautare este vid sau nu
	 * @return true (nu exista nicio comanda), false (exista cel putin o comanda)
	 */
	public boolean isEmpty(){
		return orders.isEmpty();
	}
	
	/**
	 * metoda ce returneaza obiectul de tipul Order in functie de id-ul specificat ca parametru
	 * @param orderID id-ul comenzii care este cautata
	 * @return order
	 */
	public Order findByOrderID(int orderID){
		for (Order order : orders)
			if (order.getOrderID() == orderID)
				return order;
		return null;
	}
	
}
