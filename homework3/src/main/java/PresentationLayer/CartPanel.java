package PresentationLayer;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import BusinessLayer.ClickListener;
import Model.Product;

/**
 * Clasa ce extinde superclasa JPanel si reprezinta acea portiune din interfata in care afiseaza produsele cumparate de un client
 * @author Gombos Kriszta
 */
public class CartPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int pos;
	private float totalPrice;
	private JLabel totalPriceLbl;
	JButton removeButton;

	/**
	 * Constructorul clasei in care se stabilesc anumite proprietati ale panelulului
	 */
	public CartPanel() {
		setLayout(null);
		setBackground(new Color(130, 200, 240));
		init();
	}

	/**
	 * metoda prin care se initializeaza atributele panelului (butoane si etichete)
	 */
	public void init() {
		totalPrice = 0;
		removeAll();
		repaint();
		revalidate();
		JLabel description = new JLabel("Products in cart               Quantity");
		JLabel line = new JLabel("__________________________");
		removeButton = new JButton("Remove All");
		totalPriceLbl = new JLabel("<html>______________________________<br>Total: " 
							+ totalPrice + " __________________________________<html>");
		description.setBounds(7, 7, 186, 20);
		line.setBounds(7, 10, 186, 20);
		totalPriceLbl.setBounds(15, 265, 170, 45);
		removeButton.setBounds(50, 320, 100, 30);
		add(description);
		add(line);
		add(removeButton);
		add(totalPriceLbl);
		pos = 30;
		
		
	}

	/**
	 * metoda prin care se adauga MouseListener pentru butonul existent in acest panel
	 * @param clickListener obiect ce gestioneaza evenimentele in urma click-ului
	 */
	public void addClickListeners(ClickListener clickListener) {
		removeButton.addMouseListener(clickListener);
	}

	/**
	 * metoda prin care se afiseaza un produs adaugat in acest panel
	 * @param product produsul cumparat
	 * @param quantity cantitatea de produs cumparata
	 */
	public void addProduct(Product product, Integer quantity) {
		repaint();
		revalidate();
		JLabel pLabel = new JLabel(product.getName());
		pLabel.setBounds(7, pos, 170, 25);
		add(pLabel);
		JLabel qLabel = new JLabel("" + quantity);
		if (quantity > 9 && quantity < 100)
			qLabel.setBounds(175, pos, 30, 25);
		else if (quantity < 10)
			qLabel.setBounds(180, pos, 30, 25);
		else
			qLabel.setBounds(175, pos, 30, 25);
		add(qLabel);
		
		totalPrice += product.getPrice() * quantity;
		totalPriceLbl.setText("<html>______________________________<br>Total: " 
				+ totalPrice + " __________________________________<html>");
		pos += 20;
	}

}
