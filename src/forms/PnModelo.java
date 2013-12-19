package forms;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import models.*;

public class PnModelo extends JPanel {
	
	private JTextField txtId;
	private JTextField txtNome;
	private DefaultTableModel tableModel;
	private JTable table;
	
	
	public JTextField getTxtId(){
		return txtId;
	}
	
	public JTextField getTxtNome(){
		return txtNome;
	}
	
	public DefaultTableModel getTableModel(){
		return tableModel;
	}
	
	public PnModelo(){
		
		setLayout(null);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(10, 5, 50, 30);
		lblId.setHorizontalAlignment(JLabel.RIGHT);
		add(lblId);
		
		JLabel lblNome = new JLabel("Nome *");
		lblNome.setBounds(10, 35, 50, 30);
		lblNome.setHorizontalAlignment(JLabel.RIGHT);
		
		add(lblNome);
		
		txtId=new JTextField();
		txtId.setBounds(70, 5, 50, 25);
		txtId.setEditable(false);
		
		add(txtId);
		
		txtNome=new JTextField();
		txtNome.setBounds(70, 35, 150, 25);
		
		add(txtNome);
		
		
		String[] colunas = new String []{"Id","Nome"}; 
		tableModel = new DefaultTableModel(null,colunas);  
		
		table = new JTable(tableModel){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		}; 
	    
	    JScrollPane scrollPane = new JScrollPane(table);  
		
	    scrollPane.setBounds(10, 70, 300, 200);
		add(scrollPane);
		
		LimparCampos();
		//add linha
		//model.addRow(new String[]{"t3","t4"});
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				
				txtId.setText((String)tableModel.getValueAt(row, 0));
				txtNome.setText((String)tableModel.getValueAt(row, 1));
			}
		});
	}
	
	public void LimparCampos(){
		txtId.setText("0");
		txtNome.setText("");	
	}
	
	public void AtualizaTabela(ArrayList<Modelo> list){
		int rows = tableModel.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
			tableModel.removeRow(i); 
		}
		
		for (Modelo modelo : list) {
			tableModel.addRow(new String[] {modelo.getId()+"",modelo.getNome()});
		}
	}
	
	public Modelo getObjetoTela(){
		return new Modelo(Integer.parseInt(txtId.getText()),txtNome.getText());
	}
	
	public boolean ValidarCampos(){
		
		if(txtId.getText().equals("")||txtNome.getText().equals(""))
			return false;
		
		return true;
	}
	
}
