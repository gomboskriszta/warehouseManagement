package PresentationLayer;

import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import BusinessLayer.ClickListener;
import Model.Customer;
import Model.Order;
import Model.Product;
/**
 * Clasa ce extinde superclasa JPanel in care vor fi afisati clientii din baza de date 
 * @author Gombos Kriszta
 */
public class CustomersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private TableRowSorter<DefaultTableModel> sorter;
	private JTextField nameFilter, addressFilter, emailFilter, customerName,
			customerAddress, customerEmail;
	private JComboBox<Customer> customersCombo;
	private JLabel description, deleteLabel, panelBg; 
	private JButton searchByNameButton, searchByAddressButton,
			searchByEmailButton, resetFilters, addButton, deleteButton;
	private JScrollPane tableScroll;
	
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
		String[] colName = { fields[0].getName(), fields[1].getName(), fields[2].getName(), fields[3].getName() };
		DefaultTableModel model = new DefaultTableModel(colName, 0);
		return new JTable(model);
	}
	
	/**
	 * Constructorul clasei prin care se initializeaza componentelor Swing ce sunt folosite, se seteaza pozitiile acestora si se adauga containerului
	 */
	public CustomersPanel(){
		
		setLayout(null);
		List<Object> objects = new ArrayList<Object>();
		objects.add(new Customer(0,"", "", ""));
		table = createTable(objects);
		table.setFillsViewportHeight(true);
		tableScroll = new JScrollPane(table); 
		description = new JLabel("<html>Search by name: <br><br><br>Search by address: "
				+ "<br><br><br>Search by email: <br><br><br><br><br><br>"
				+ "________________________________________________________________"
				+ "<br>Insert a new customer:" + "<br><br>"
				+ " Name*: <br><br>Address:<br><br>Email:</html>");
		deleteLabel = new JLabel("Delete a customer:");
		searchByNameButton = new JButton("Search by Name");
		searchByAddressButton = new JButton("Search by Address");
		searchByEmailButton = new JButton("Search by Email");
		resetFilters = new JButton ("Reset filters");
		addButton = new JButton ("Add customer");
		deleteButton = new JButton("Delete customer");
		nameFilter = new JTextField();
		addressFilter = new JTextField();
		emailFilter = new JTextField();
		customerName = new JTextField();
		customerAddress = new JTextField();
		customerEmail = new JTextField();
		customersCombo = new JComboBox<Customer>();
		panelBg = new JLabel(new ImageIcon("C:\\Users\\Kriszta\\Desktop\\background.jpg"));
		
		description.setBounds(35, 235, 465, 340);		
		nameFilter.setBounds(180, 240, 120, 25);		
		searchByNameButton.setBounds(342, 237, 143, 30);		
		addressFilter.setBounds(180, 290, 120, 25);		
		searchByAddressButton.setBounds(342, 287, 143, 30);
		emailFilter.setBounds(180, 340, 120, 25);
		searchByEmailButton.setBounds(342, 337, 143, 30);
		deleteLabel.setBounds(320, 420, 400, 80);
		resetFilters.setBounds(180, 385, 120, 30);
		customerName.setBounds(110, 483, 115, 25);
		customerAddress.setBounds(110, 515, 115, 25);
		customerEmail.setBounds(110, 547, 115, 25);
		addButton.setBounds(110, 582, 115, 30);
		deleteButton.setBounds(317, 547, 145, 30);
		customersCombo.setBounds(317, 497, 145, 25);
		tableScroll.setBounds(40, 30, 450, 180);
		panelBg.setBounds(0, 0, 700, 700);
		
		add(nameFilter);
		add(description);
		add(searchByNameButton);
		add(addressFilter);
		add(searchByAddressButton);
		add(emailFilter);
		add(searchByEmailButton);
		add(deleteLabel);
		add(resetFilters);
		add(customerName);
		add(customerAddress);
		add(customerEmail);
		add(addButton);
		add(customersCombo);
		add(deleteButton);
		add(tableScroll);
		add(panelBg);
		
	}
	
	/**
	 * metoda prin care se aduaga MoueListener tuturor butoanelor din acest panel 
	 * @param clickListener obiectul ce trateaza actiunile metodelor
	 */
	public void addClickListeners(ClickListener clickListener){
		searchByNameButton.addMouseListener((MouseListener) clickListener);
		searchByAddressButton.addMouseListener((MouseListener) clickListener);
		searchByEmailButton.addMouseListener((MouseListener) clickListener);
		addButton.addMouseListener((MouseListener) clickListener);
		deleteButton.addMouseListener((MouseListener) clickListener);
		resetFilters.addMouseListener((MouseListener) clickListener);
	}
	
	/**
	 * metoda prin care e returneaza tabelul in care sunt afisati clientii pentru a putea fi accesat din alte clase
	 * @return table
	 */
	public JTable getTable() {
		return table;
	}
	
	/**
	 * returneaza valoarea existenta intr-o celula a tabelului identificata prin rand si coloana
	 * @param row randul pentru care se returneaza valoarea
	 * @param column coloana pentru care se returneaza valoarea
	 * @return valoarea din tabel de la indicii indicati prin row si column
	 */
	public Object getValueAt(int row, int column){
		return table.getModel().getValueAt(row, column);
	}
	
	/**
	 * metoda prin care se updateaza continutul tabelului ce contine clientii si a combobox-ului pentru clienti la situatia actuala a clientilor
	 * @param customers lista de clienti ce este afisata
	 */
	public void updateCustomers(ArrayList<Customer> customers) {
		customersCombo.removeAllItems();
		Field[] fields   = Customer.class.getDeclaredFields();
		String[] colName = { fields[0].getName(), fields[1].getName(), fields[2].getName(), fields[3].getName() };
		DefaultTableModel model = new DefaultTableModel(colName, 0);
		table.setModel(model);
		for (Customer customer : customers) {
			
			Object[] row = { "" + customer.getCustomerID() + "", customer.getName(), customer.getAddress(),
					customer.getEmail() };
			model = (DefaultTableModel) table.getModel();
			try {
				model.addRow(row);
			} catch (IndexOutOfBoundsException e) {
			}
			customersCombo.addItem(customer);
		}
		
		sorter = new TableRowSorter<DefaultTableModel>(model);
		table.setRowSorter(sorter);
		
		
	}

	/**
	 * metoda prin care se filtreaza clientii in functie de numele acestora
	 */
	public void searchByName(){
		sorter.setRowFilter(RowFilter.regexFilter(nameFilter.getText(), 1));
	}

	/**
	 *  metoda prin care se filtreaza clientii in functie de adresa acestora
	 */
	public void searchByAddress(){
		sorter.setRowFilter(RowFilter.regexFilter(addressFilter.getText(), 2));
	}
	
	/**
	 * metoda prin care se filtreaza clientii in functie de adresa de email a acestora
	 */
	public void searchByEmail(){
		sorter.setRowFilter(RowFilter.regexFilter(emailFilter.getText(), 3));
	}
	
	/**
	 * metoda prin care se reseteaza toate filtrele
	 */
	public void resetFilters(){
		sorter.setRowFilter(null);
		nameFilter.setText("");
		addressFilter.setText("");
		emailFilter.setText("");
	}
	
	/**
	 * metoda prin care se returneaza numele clientului introdus in JTextField-ul customerName la adaugarea unui nou client
	 * @return name
	 */
	public String getCustomerName(){
		String name = customerName.getText();
		customerName.setText("");
		return name;
	}
	
	/**
	 * 
	 * metoda prin care se returneaza adresa introdusa in JTextField-ul customerAddress la adaugarea unui nou client
	 * @return address
	 */
	public String getCustomerAddress(){
		String address = customerAddress.getText();
		customerAddress.setText("");
		return address;
	}
	
	/**
	 * metoda prin este returnata valoarea introdusa in JTextField-ul customerEmail atunci cand e doreste adaugarea unui nou client
	 * @return email
	 */
	public String getCustomerEmail(){
		String email = customerEmail.getText();
		customerEmail.setText("");
		return email;
	}

	/**
	 * metoda prin care este returnat clientul selectat in ComboBox, atunci cand se doreste stergerea unui client
	 * @return customer
	 */
	public Customer getCustomer() {
		return (Customer) customersCombo.getSelectedItem();
	}
}
