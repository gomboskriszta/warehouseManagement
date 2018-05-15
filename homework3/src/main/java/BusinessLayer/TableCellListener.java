package BusinessLayer;

import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
/**
 * Clasa ce implementeaza interfetele PropertyChangeListener si Runnable pentru a determina schimbarile
 * realizate in obiectele JTable 
 * @author Gombos Kriszta
 */
public class TableCellListener implements PropertyChangeListener, Runnable {
	private JTable table;
	private Action action;
	private int row;
	private int column;
	private Object oldValue;
	private Object newValue;

	/**
	 * Constructorul clasei ce primeste ca parametrii tabelul in care se realizeaza schimbarile si actiunile specifice
	 * @param table tabelul pentru care se asculta evenimentele
	 * @param action actiunea realizata
	 */
	public TableCellListener(JTable table, AbstractAction action) {
		this.table = table;
		this.action = action;
		this.table.addPropertyChangeListener(this);
	}

	/**
	 * metoda ce returneaza coloana la care s-a realizat schimbarea
	 * @return column
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * metoda ce returneaza randul la care s-a realizat schimbarea
	 * @return column
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * metoda ce returneaza valoare regasita in urma schimbarii la una dintre celule tabelului
	 * @return newValue
	 */
	public Object getNewValue() {
		return newValue;
	}

	/**
	 * metoda ce returneaza vechea valoare a celulei tabelului
	 * @return oldValue
	 */
	public Object getOldValue() {
		return oldValue;
	}

	/**
	 * metoda ce se activeaza in momentul aparitiei unei schimbari la una dintre celulele tabelului
	 */
	public void propertyChange(PropertyChangeEvent e) {
		if ("tableCellEditor".equals(e.getPropertyName())) {
			if (table.isEditing())
				processEditingStarted();
			else
				processEditingStopped();
		}
	}

	/**
	 * metoda activata in momentul inceperii procesului de editare al celulei tabelului
	 */
	private void processEditingStarted() {
		SwingUtilities.invokeLater(this);
	}

	/**
	 * metoda activata la sfarsitul procesului de editare prin care se verifica daca valorile 
	 * celulelor s-au schimbat, iar apoi se creeaza actiunea corespunzatoare ce va fi foloita in clasa Controller
	 */
	private void processEditingStopped() {
		newValue = table.getModel().getValueAt(row, column);
		if (!newValue.equals(oldValue)) {
			ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");		
			action.actionPerformed(event);
		}
	}
	
	/**
	 * metoda activata in momentul pornirii threadului prin care se seteaza atributele clasei la valoarile preluate din tabel 
	 */
	public void run() {
		row = table.convertRowIndexToModel(table.getEditingRow());
		column = table.convertColumnIndexToModel(table.getEditingColumn());
		oldValue = table.getModel().getValueAt(row, column);
		newValue = null;
	}
}