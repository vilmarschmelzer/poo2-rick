package forms;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

public class PnCor extends JPanel {
	
	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtValor;
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
	
	public PnCor(){
		
		setLayout(null);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(10, 5, 50, 30);
		lblId.setHorizontalAlignment(JLabel.RIGHT);
		add(lblId);
		
		JLabel lblNome = new JLabel("Nome *");
		lblNome.setBounds(10, 35, 50, 30);
		lblNome.setHorizontalAlignment(JLabel.RIGHT);
		
		add(lblNome);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(10, 65, 50, 30);
		lblValor.setHorizontalAlignment(JLabel.RIGHT);
		
		add(lblValor);
		
		txtId=new JTextField();
		txtId.setBounds(70, 5, 50, 25);
		txtId.setEditable(false);
		
		add(txtId);
		
		txtNome=new JTextField();
		txtNome.setBounds(70, 35, 150, 25);
		
		add(txtNome);
		
		txtValor=new JTextField();
		txtValor.setBounds(70, 65, 150, 25);
		
		txtValor.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
				try {
					Double.parseDouble(txtValor.getText());
				} catch (Exception e2) {
					txtValor.setText("0.0");
					// TODO: handle exception
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		add(txtValor);
		
		String[] colunas = new String []{"Id","Nome","Valor"}; 
		tableModel = new DefaultTableModel(null,colunas);  
		
		table = new JTable(tableModel){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		}; 
	    
	    JScrollPane scrollPane = new JScrollPane(table);  
		
	    scrollPane.setBounds(10, 100, 300, 200);
		add(scrollPane);
		
		LimparCampos();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				
				txtId.setText((String)tableModel.getValueAt(row, 0));
				txtNome.setText((String)tableModel.getValueAt(row, 1));
				txtValor.setText((String)tableModel.getValueAt(row, 2));
			}
		});
	}
	
	public void LimparCampos(){
		txtId.setText("0");
		txtNome.setText("");
		txtValor.setText("0.0");
	}
	
	public void AtualizaTabela(ArrayList<Cor> list){
		int rows = tableModel.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
			tableModel.removeRow(i); 
		}
		
		for (Cor cor : list) {
			tableModel.addRow(new String[] {cor.getId()+"",cor.getNome(),cor.getValor()+""});
		}
	}
	
	public Cor getObjetoTela(){
		return new Cor(Integer.parseInt(txtId.getText()),txtNome.getText(),Double.parseDouble(txtValor.getText()));
	}
	
	public boolean ValidarCampos(){
		
		if(txtId.getText().equals("")||txtNome.getText().equals(""))
			return false;
		
		return true;
	}
	
}
