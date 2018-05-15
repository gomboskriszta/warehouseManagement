package PresentationLayer;

import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import BusinessLayer.ClickListener;
import Model.Order;
import Model.Product;
import Model.Warehouse;

/**
 * Clasa ce extinde superclasa JPanel si foloseste componentele neceare din Swing pentru afisarea produselor existente in depozit
 * @author Gombos Kriszta
 */
public class ProductsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private TableRowSorter<DefaultTableModel> sorter;
	private JTextField nameFilter, minQuantity, maxQuantity, minPrice,
			maxPrice, productName, productPrice, productQuantity;
	private JLabel description, insertLabel, panelBg;
	private JButton searchByNameButton, searchByQuantityButton,
			searchByPriceButton, resetFilters, addButton, deleteButton;
	private JScrollPane tableScroll;
	private JComboBox<Product> productCombo;
	
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
		Field[] fields2   = objects.get(1).getClass().getDeclaredFields();
		String[] colName = { fields[0].getName(), fields[1].getName(), fields[2].getName(), fields2[4].getName(), "OverStock", "UnderStock" };
		DefaultTableModel model = new DefaultTableModel(colName, 0);
		return new JTable(model);
	}
	
	/**
	 * Contructorul clasei in care se initializeaza componentele necesare afisarii, adaugari, modificarii si stergerii unui produs 
	 */
	public ProductsPanel(){
		
		setLayout(null);
		List<Object> objects = new ArrayList<Object>();
		objects.add(new Product(0, "", 0));
		objects.add(new Order(0, null));
		table = createTable(objects);
		table.setFillsViewportHeight(true);
		tableScroll = new JScrollPane(table); 
		description = new JLabel("<html>Search by name: <br><br><br>Search by quantity range: "
				+ "<br><br><br>Search by price range: <br><br><br><br><br>"
				+ "___________________________________________________________________________"
				+ "<br>Insert a new product:" + "<br><br><br><br><br><br>"
				+ "___________________________________________________________________________"
				+ "<br><br><br>Delete a product :  </html>");
		insertLabel = new JLabel("Name:                                           Price:"
				+ "                      Quantity:");
		searchByNameButton = new JButton("Search by Name");
		searchByQuantityButton = new JButton("Search by Quantity");
		searchByPriceButton = new JButton("Search by Price");
		resetFilters = new JButton ("Reset filters");
		addButton = new JButton ("Add product");
		deleteButton = new JButton("Delete product");
		nameFilter = new JTextField();
		minQuantity = new JTextField();
		maxQuantity = new JTextField();
		minPrice = new JTextField();
		maxPrice = new JTextField();
		productName = new JTextField();
		productPrice = new JTextField();
		productQuantity = new JTextField();
		productCombo = new JComboBox<Product>();
		panelBg = new JLabel(new ImageIcon("C:\\Users\\Kriszta\\Desktop\\background.jpg"));
		
		description.setBounds(35, 235, 465, 360);		
		nameFilter.setBounds(205, 240, 110, 25);		
		searchByNameButton.setBounds(342, 237, 143, 30);		
		minQuantity.setBounds(205, 290, 45, 25);		
		maxQuantity.setBounds(270, 290, 45, 25);
		searchByQuantityButton.setBounds(342, 287, 143, 30);
		minPrice.setBounds(205, 340, 45, 25);
		maxPrice.setBounds(270, 340, 45, 25);	
		searchByPriceButton.setBounds(342, 337, 143, 30);
		insertLabel.setBounds(35, 460, 400,30);
		resetFilters.setBounds(205, 385, 110, 30);
		productName.setBounds(35, 495, 115, 25);
		productPrice.setBounds(200, 495, 50, 25);
		productQuantity.setBounds(300, 495, 50, 25);
		addButton.setBounds(380, 470, 120, 50);
		deleteButton.setBounds(380, 570, 120, 30);
		productCombo.setBounds(160, 572, 190, 25);
		tableScroll.setBounds(40, 30, 450, 180);
		panelBg.setBounds(0, 0, 700, 700);
		
		add(nameFilter);
		add(description);
		add(searchByNameButton);
		add(minQuantity);
		add(maxQuantity);
		add(searchByQuantityButton);
		add(minPrice);
		add(maxPrice);
		add(searchByPriceButton);
		add(insertLabel);
		add(resetFilters);
		add(productName);
		add(productPrice);
		add(productQuantity);
		add(addButton);
		add(productCombo);
		add(deleteButton);
		add(tableScroll);
		add(panelBg);
		
	}
	
	/**
	 * metoda prin care se adauga MouseListener butoanelor din aceasta clasa
	 * @param clickListener obiectul ce trateaza actiunile butoanelor
	 */
	public void addClickListeners(ClickListener clickListener){
		searchByNameButton.addMouseListener((MouseListener) clickListener);
		searchByQuantityButton.addMouseListener((MouseListener) clickListener);
		searchByPriceButton.addMouseListener((MouseListener) clickListener);
		addButton.addMouseListener((MouseListener) clickListener);
		deleteButton.addMouseListener((MouseListener) clickListener);
		resetFilters.addMouseListener((MouseListener) clickListener);
	}
	
	/**
	 * metoda prin care e returneaza tabelul ce contine produsele afisate
	 * @return table
	 */
	public JTable getTable() {
		return table;
	}
	
	/**
	 * metoda prin care se returneaza valoare dintr-o anumita celula specificata prin linie si coloana
	 * @param row randul de la care se doreste valoarea
	 * @param column coloana de la care se doreste valoarea
	 * @return valoare de la randul si coloana specificata
	 */
	public Object getValueAt(int row, int column){
		return table.getModel().getValueAt(row, column);
	}
	
	/**
	 * metoda prin care se updateaza datele din tabel in cazul aparitiei unei modificari
 	 * @param warehouse noile date ce trebuie afisate
	 */
	public void updateWarehouse(Warehouse warehouse) {
		String[] colName = { "ProductID", "Name", "Price", "Quantity", "OverStock", "UnderStock" };
		DefaultTableModel model = new DefaultTableModel(colName, 0);
		table.setModel(model);
		for (Product product : warehouse.getProducts()) {
			Object[] row = { "" + product.getProductID() + "", product.getName(), product.getPrice(),
					warehouse.getStock().get(product), warehouse.checkOverStock(product),
					warehouse.checkUnderStock(product) };
			model = (DefaultTableModel) table.getModel();
			try {
				model.addRow(row);
			}
			catch(IndexOutOfBoundsException e){}
		}
		
		sorter = new TableRowSorter<DefaultTableModel>(model);
		table.setRowSorter(sorter);
		productCombo.removeAllItems();
		Set<Product> productList = warehouse.getProducts();
		for (Product product : productList)
			productCombo.addItem(product);
	}

	/**
	 * metoda prin care se filtreaza datele din tabel in functie de un interval si de coloana pe care e face filtrarea
	 * @param min limita inferioara a intervalului
	 * @param max limita superioara a intervalului
	 * @param column coloana pe care se face filtrarea
	 */
	public void rangeFilter(Double min, Double max, int column) {
		RowFilter<DefaultTableModel, Object> rf = null;
		RowFilter<DefaultTableModel, Object> low = RowFilter.numberFilter(ComparisonType.AFTER, min - 0.0001, column);
		RowFilter<DefaultTableModel, Object> high = RowFilter.numberFilter(ComparisonType.BEFORE, max + 0.0001, column);
		ArrayList<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<RowFilter<DefaultTableModel, Object>>();
		filters.add(low);
		filters.add(high);
		rf = RowFilter.andFilter(filters);
		sorter.setRowFilter(rf);
	}

	/**
	 * metoda prin care se filtreaza datele din tabel in functie de numele produsului specificat de utilizator
	 */
	public void searchByName() {
		sorter.setRowFilter(RowFilter.regexFilter(nameFilter.getText(), 1));
	}

	/**
	 * metoda prin care se filtreaza produsele in functie de un interval de cantitati specificat de utilizator
	 */
	public void searchByQuantity() {
		try{
			rangeFilter(Double.parseDouble(minQuantity.getText()), Double.parseDouble(maxQuantity.getText()), 3);
		} catch(NumberFormatException err) {
			JOptionPane.showMessageDialog(null, "Please insert valid values", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * metoda prin care se filtreaza produsele in functie de un interval de pret specificat de utilizator
	 */
	public void searchByPrice() {
		try{
			rangeFilter(Double.parseDouble(minPrice.getText()), Double.parseDouble(maxPrice.getText()), 2);
		} catch(NumberFormatException err) {
			JOptionPane.showMessageDialog(null, "Please insert valid values", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * metoda prin care se reseteaza filtrele de cautare
	 */
	public void resetFilters(){
		sorter.setRowFilter(null);
		nameFilter.setText("");
		minQuantity.setText("");
		maxQuantity.setText("");
		minPrice.setText("");
		maxPrice.setText("");
	}
	
	/**
	 * metoda ce returneaza numele unui produs specificat de utilizator in cazul adaugarii unui nou produ
	 * @return name
	 */
	public String getProductName(){
		if (productName.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please complete the name field", "Error", JOptionPane.ERROR_MESSAGE);
			return null;			
		}
		String name = productName.getText();
		productName.setText("");
		return name;
	}

	/**
	 * metoda ce returneaza cantitatea unui produs specificat de utilizator in cazul adaugarii unui nou produ
	 * @return quantity
	 */
	public Integer getProductQuantity (){
		Integer quantity = null;
		try {
			quantity = Integer.parseInt(productQuantity.getText());
		}
		catch (NumberFormatException e){}
		productQuantity.setText("");
		return quantity;
	}

	/**
	 * metoda ce returneaza pretul unui produs specificat de utilizator in cazul adaugarii unui nou produ
	 * @return price
	 */
	public Double getProductPrice (){
		Double price = null;
		try {
			price = Double.parseDouble(productPrice.getText());
		}
		catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Please insert a valid value for price", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		productPrice.setText("");
		if (price > 0) return price;
		else return null;
	}

	/**
	 * metoda prin care se returneaza produsul selectat in comboBox in cazul stergerii unui produs
	 * @return product
	 */
	public Product getProduct() {
		return (Product) productCombo.getSelectedItem();
	}
}
