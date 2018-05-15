package Model;
/**
 * Clasa prin care se creeaza obiectul Customer, necesar pentru functionarea aplicatiei
 * @author Gombos Kriszta
 */
public class Customer {

	private int customerID;
	private String name; 
	private String address;
	private String email;

	/**
	 * Constructorul clasei Customer. Primeste ca parametrii obiectele ce formeaza un client
	 * @param customerID id-ul prin care se identifica clientul
	 * @param name numele clientului
	 * @param address adresa clientului
	 * @param email adresa de email a clientului
	 */
	public Customer(int customerID, String name, String address, String email){
		
		this.customerID = customerID;
		this.name = name;
		this.address = address;
		this.email = email;
	}
	/**
	 * metoda prin care se poate accesa id-ul clientului din alte clase
	 * @return customerID
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * metoda care returneaza numele clientului, pentru a putea fi accesat din alte clase
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * metoda care returneaza adresa clientului, pentru a putea fi accesata din alte clase
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * metoda prin care se returneaza adresa de email a clientului pentru a putea fi accesata de alte clase
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * metoda care seteaza numele clientului ca fiind sirul de caractere dat de paramentrul metodei
	 * @param name numele setat pentru client
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * metoda prin care se seteaza adresa clientului la sirul de caractere dat ca parametru
	 * @param address adresa clientului
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * metoda prin care se seteaza adresa de email a clientului la sirul de caractere dat ca parametru
	 * @param email adresa de email a clientului
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * metoda ce returneaza un sir de caractere reprezentat de atributele clientului, pentru a fi afisat in interfata.
	 */
	public String toString(){
		return name + ", " + address;
	}
	
	/**
	 * metoda ce returneaza un sir de caractere reprezentat de atributele clientului.
	 * Este folosit la afisarea unei comenzi, pentru o vizualizare amanuntita a datelor clientului 
	 * @return string
	 */
	public String toStringOrder(){
		return " CustomerID: " + customerID + "\n Name: " + name + "\n Address: " + address + "\n Email: " + email + "\n\n";
	}
}
