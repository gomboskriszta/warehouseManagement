package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Clasa dupa care se modeleaza obiectele Order si implementeaza operatiile specifice acestei clase
 * @author Gombos Kriszta
 */
public class Order implements Comparable<Order>{

	private int orderID;
	private Customer customer;
	private double totalPrice;
	private ArrayList<Product> products;
	private ArrayList<Integer> quantities;
	
	//File file = new File("");
	
	/**
	 * Unul dintr constructorii clasei Order. Primeste ca parametrii toate obiectele prin care se 
	 * identifica o comanda si calculeaza petrul toatl al comenzii in functie de pretul si 
	 * cantitatile produselor comandate
	 * @param orderID id-ul unic prin care e identifica o comanda
	 * @param customer clientul care realizeaza comanda
	 * @param products produsele comandate, regasite intr-un ArrayList
	 * @param quantities cantitatile pentru fiecare produs, regasite intr-un ArrayList
	 */
	public Order(int orderID, Customer customer, ArrayList<Product> products, ArrayList<Integer> quantities){
		this.orderID = orderID;
		this.customer = customer;
		this.products = products;
		this.quantities = quantities;
		this.totalPrice = 0;
		for (int i = 0; i < products.size(); i++)
			totalPrice += products.get(i).getPrice() * quantities.get(i);
		
			try {
				File file = new File("C:\\Users\\Kriszta\\Desktop\\bill.txt");
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write("OrderID " + orderID);
				fileWriter.write(System.lineSeparator());
				for(int i = 0; i < products.size(); i++) {
				
				fileWriter.write(products.get(i).toString() + " ");
				//fileWriter.write(System.lineSeparator());
				fileWriter.write("quantity " + quantities.get(i).toString() + " ");
				//fileWriter.write(System.lineSeparator());
				fileWriter.write("\r\n");
				}
				fileWriter.write("Total price " + totalPrice + " RON");
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Constructor ce primeste ca parametrii doar id-ul comenzii si clientul ce realizeaza comanda, 
	 * urmand ca restul atributelor sa fie setate ulterior
	 * @param orderID id-ul unic prin care se identifica o comanda
	 * @param customer clientul care realizeaza comanda
	 */
	public Order(int orderID, Customer customer){
		this.orderID = orderID;
		this.customer = customer;
		this.products = new ArrayList<Product>();
		this.quantities = new ArrayList<Integer>();
		this.totalPrice = 0;
	}

	/**
	 * metoda ce returneaza id-ul prin care se identifica o comanda pentru a putea fi accesat de alte clase
	 * @return orderID
	 */
	public int getOrderID() {
		return orderID;
	}
	
	/**
	 * metoda ce returneaza clientul ce a realizat comanda pentru a putea fi vizibil in alte clase
	 * @return customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	
	/**
	 * metoda ce returneaza lita de produse ce compun comanda pentru a putea fi vizibile in alte clase
	 * @return products
	 */
	public ArrayList<Product> getProducts() {
		return products;
	}
	
	/**
	 * metoda ce returneaza lista de cantitati a comenzii pentru a putea fi accesata de alte clase
	 * @return quantities
	 */
	public ArrayList<Integer> getQuantities() {
		return quantities;
	}

	/**
	 * metoda ce returneaza un anumit produs din comanda identificat prin parametrul i
	 * @param i indexul ce refera produsul din comanda
	 * @return produsul de pe pozitia i
	 */
	public Product getProduct(int i) {
		return products.get(i);
	}
	
	 /**
	 * metoda ce returneaza o anumita cantitate din comanda identificata prin parametrul i
	 * @param i indexul ce refera cantitatea din comanda
	 * @return cantitatea de pe pozitia i
	 */
	public int getQuantity(int i) {
		return quantities.get(i);
	}
	
	/** 
	 * metoda ce returneaza pretul total al comenzii pentru a putea fi accesat de alte clase
	 * @return totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * metoda ce aduaga un produs si o cantitate in lista de produse ce compune comanda
	 * @param product produsul ce este adaugat
	 * @param quantity cantitatea produsului adaugat
	 */
	public void addProductToOrder(Product product, Integer quantity){
		products.add(product);
		quantities.add(quantity);
		totalPrice += product.getPrice() * quantity;
	}
	
	/**
	 * metoda prin care se compara id-urile a doua comenzi: comanda referita prin this si comanda referita
	 * de parametrul metodei. Daca id-urile sunt egale se returneaza valoarea 0, daca id-ul primei
	 * comenzi este mai mic se va returna o valoare negativa, altfel se va returna o valoare pozitiva
	 */
	public int compareTo(Order order) {
		return orderID - order.getOrderID();
	}
	
	/**
	 * metoda ce returneaza un sir de caractere constand in afisarea detaliata a componentelor comenzii.
	 * Aceasta afisare este regasita in interfata programului pentru vizualizarea detaliata a unei comenzi
	 */
	public String toString(){
		String str = "\n Order " + orderID +"  details \n\n\n" + customer.toStringOrder() + " Products:\n\n" ;
		str += " Name\t\tPrice\tQuantity \n---------------------------------------------------------"
				+ "-----------------------------\n";
		for (int i = 0; i < products.size(); i++)
			str += products.get(i).toStringOrder() + "\t" + quantities.get(i) + "\n";
		str += "--------------------------------------------------------------------------------------\n"
				+ " Total price: " + totalPrice + " RON";
		
		return str;
	}	
}
