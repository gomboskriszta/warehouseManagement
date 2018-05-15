package PresentationLayer;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Model.Order;
/**
 * Clasa ce extinde superclasa JFrame si in care vor fi afisate detaliile unei comenzi intr-o fereastra separata
 * @author Gombos Kriszta
 */
public class OrderFrame extends JFrame{	
	
	private static final long serialVersionUID = 1L;
	private JTextArea afisare;
	private JScrollPane scroll;

	/**
	 * Constructorul clasei ce primeste ca parametru comanda ce trebuie sa o afiseze si creeaza anumite componente Swing necesare afisarii
	 * @param order comanda afisata
	 */
	public OrderFrame(Order order) {
		
		this.setSize(400, 450);
		this.setTitle("Order " + order.getOrderID());
		this.setLayout(null);
		this.setBackground(new Color(255, 255, 255));
		this.setLocation(600, 50);
		afisare = new JTextArea();
		afisare.setText(order.toString());
		afisare.setEditable(false);
		scroll = new JScrollPane(afisare);
		scroll.setBounds(20, 20, 350, 350);
		scroll.setVisible(true);
		add(scroll);
		
	}

}
