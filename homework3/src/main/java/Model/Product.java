package Model;

/**
 * Clasa dupa care se modeleaza obiectele Produs si implementeaza operatiile specifice acestei clase
 * @author Gombos Kriszta
 */
public class Product implements Comparable<Product> {

	private int productID;
	private double price;
	private String name;

	/**
	 * Contructorul clasei Product. Un produs este format din urmatorii parametri:
	 * @param productID id-ul unic prin care se identifica produsul
	 * @param name numele produsului
	 * @param price pretul produsului
	 */
	public Product(int productID, String name, double price) {
		this.productID = productID;
		this.name = name;
		this.price = price;
	}

	/**
	 * metoda ce returneaza id-ul produsului pentru a putea fi accesat de alte clase
	 * @return productID
	 */
	public int getProductID() {
		return productID;
	}
	
	/**
	 * metoda ce returneaza denumirea produsului pentru a putea fi accesata de alte clase
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * metoda ce returneaza pretul produsului pentru a putea fi accesat de alte clase
	 * @return price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * metoda ce seteaza numele produsului la valoarea referita ca parametru
	 * @param name numele produsului
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * metoda ce seteaza pretul produsului la valoarea double referita prin parametru
	 * @param price pretul produsului
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * metoda ce permite afisarea produsului sub forma de sir de caractere, pentru a putea fi afisat in interfata
	 */
	public String toString(){
		return name + ", " + price + " RON";
	}
	
	/**
	 * metoda ce returneaza o afisare sub forma de sir de caractere 
	 * pentru a putea fi afisata la vizualizarea detaliata a unei comenzi
	 * @return string
	 */
	public String toStringOrder() {
		if (name.length() > 13)
			return " " + name + "\t" + price + " RON";
		else
			return " " + name + "\t\t" + price + " RON";
	}

	/**
	 * metoda prin care se compara id-urile a doua produse: produsul referit prin this si produsul referit de parametrul metodei
	 * daca id-urile sunt egale se returneaza valoarea 0, daca id-ul primului produs este mai mic 
	 * se va returna o valoare negativa, altfel se va returna o valoare pozitiva
	 */
	public int compareTo(Product product) {
		return productID - product.getProductID();
	}
}
