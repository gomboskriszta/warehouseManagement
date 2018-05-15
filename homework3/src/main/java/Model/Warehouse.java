package Model;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

/**
 * Clasa ce contine obiectele produse si cantitatile acestora si implementeaza operatiile specifice unui depozit
 * @author Gombos Kriszta
 */
public class Warehouse {

	private TreeMap<Product, Integer> stock;

	/**
	 * Constructorul clasei Warehouse care primeste ca parametri un arbore binar de cautare format din
	 * grupuri de tipul Product - Integer, reprezentand produsul si cantitatea acestuia
	 * @param stock arborele format din produse si cantitatile acestuia
	 */
	public Warehouse(TreeMap<Product, Integer> stock) {
		this.stock = stock;
	}
	
	/**
	 * metoda prin care e returneaza arborele binar de cautare format din produsele si cantitatile din depozit
	 * @return stock
	 */
	public TreeMap<Product, Integer> getStock() {
		return stock;
	}
	
	/**
	 * metoda prin care se returneaza produsele din depozit 
	 * @return products
	 */
	public Set<Product> getProducts(){
		return stock.keySet();
	}
	
	/**
	 * metoda ce returneaza cantitatea dintr-un produs referit de parametru
	 * @param product produsul a carui cantitate este returnata
	 * @return quantity
	 */
	public Integer getProductQuantity(Product product) {
		return stock.get(product);
	}

	/**
	 * metoda prin care se adauga un produs si cantitatea acestuia in depozit
	 * @param product produsul adaugat
	 * @param quantity cantitatea produsului adaugat
	 */
	public void addProduct(Product product, Integer quantity) {
		stock.putIfAbsent(product, quantity);
	}

	/**
	 * metoda prin care se elimina un produs din depozit
	 * @param product produsul ce este sters
	 */
	public void deleteProduct(Product product) {
		stock.remove(product);
	}
	
	/**
	 * metoda prin care se seteaza cantitatea unui produs la valoarea referita ca parametru
	 * @param product produsul a carui cantitate este schimbata
	 * @param quantity noua cantitate a produsului
	 */
	public void setQuantity(Product product, Integer quantity) {
		stock.replace(product, quantity);
	}
	
	/**
	 * metoda prin care se scad cantitatile unei liste de produse, in cazul unei comenzi
	 * @param products lista de produse cumparate, a caror cantitati trebuie sa scada
	 * @param quantities lista de cantitati cumparate
	 */
	public void decreaseQuantities(ArrayList<Product> products, ArrayList<Integer> quantities) {
		for (int i = 0; i < products.size(); i++)
			stock.replace(products.get(i), stock.get(products.get(i)) - quantities.get(i));	
	}
	
	/**
	 * metoda prin care se verifica daca exista destule bucati dintr-un anumit produs pentru a se realiza
	 * o comanda. Metoda primeste ca parametru produsul si cantitate ce se doreste a scadea, si returneaza
	 * true daca exista destule produse si false in caz contrar
	 * @param product produsul a carui cantitate ete verificata
	 * @param quantity cantitatea care se verifica
	 * @return true sau false
	 */
	public boolean checkQuantity(Product product, Integer quantity) {
		if (stock.get(product) >= quantity)
				return true;
		return false;
	}
	
	/**
	 * metoda ce returneaza daca stocul unui produs este prea mic
	 * @param product produsul a carui stoc este verificat
	 * @return true(cantitatea mai putin de 100 bucati) false (altfel)
	 */
	public boolean checkUnderStock(Product product) {
		if (stock.get(product) < 100)
			return true;
		return false;
	}

	/**
	 * metoda ce returneaza daca stocul unui produs este mai mare de 200 de bucati
	 * @param product produsul a carui stoc este verificat
	 * @return true(cantitatea mai mult de 200 bucati) false (altfel)
	 */
	public boolean checkOverStock(Product product) {
		if (stock.get(product) > 200)
			return true;
		return false;
	}

	/**
	 * metoda prin care se returneaza obiectul Product referit prin id-ul acetuia
	 * @param productID id-ul produsului cautat
	 * @return produsul cautat ce are id-ul referit ca paramtru
	 */
	public Product findByProductID(int productID){
		for (Product product : stock.keySet())
			if (product.getProductID() == productID)
				return product;
		return null;
	}
	
	/**
	 * metoda prin care se returneaza daca depozitul este gol sau nu
	 * @return true(nu exista niciun produs), false(exista cel putin un produs)
	 */
	public boolean isEmpty(){
		return stock.isEmpty();
	}
	
}
