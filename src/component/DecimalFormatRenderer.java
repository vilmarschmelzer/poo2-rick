package component;

import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DecimalFormatRenderer extends DefaultTableCellRenderer {
	private static final DecimalFormat formatter = new DecimalFormat( "#.00" );
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		System.out.println(value.toString());
		
		try {
			value = formatter.format((Number)value);
		} catch (Exception e) {
			value=0.0;
			// TODO: handle exception
		}
		
		// And pass it on to parent class
	 
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column );
	}
}

