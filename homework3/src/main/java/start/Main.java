package start;

import BusinessLayer.Controller;
import DataAccessLayer.DBConnection;
import PresentationLayer.MainFrame;
import PresentationLayer.OrdersPanel;
/**
 * Clasa ce contine metoda main prin care se porneste aplicatia
 * @author Gombos Kriszta
 */
public class Main {
	
	/**
	 * metoda in care se creeaza obiectul view, db si controller pentru ce tine de design patternul MVC
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		MainFrame view = new MainFrame();
		view.setVisible(true);
		DBConnection db = new DBConnection();
		Controller controller = new Controller(view, db);
		
	
	}	
}