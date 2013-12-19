package forms;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import models.*;

public class PnFabricante extends JPanel {
	
	private JFormattedTextField txtCnpj;
	private JTextField txtNome;
	private JTextField txtNome_fantasia;
	private DefaultTableModel tableModel;
	private JTable table;
	
	
	public JFormattedTextField getTxtCnpj(){
		return txtCnpj;
	}
	
	public JTextField getTxtNome(){
		return txtNome;
	}
	
	public JTextField getTxtNome_fantasia(){
		return txtNome_fantasia;
	}
	
	public DefaultTableModel getTableModel(){
		return tableModel;
	}
	
	public PnFabricante(){
		
		setLayout(null);
		
		JLabel lblCnpj = new JLabel("CNPJ *");
		lblCnpj.setBounds(10, 5, 120, 30);
		lblCnpj.setHorizontalAlignment(JLabel.RIGHT);
		add(lblCnpj);
		
		JLabel lblNome = new JLabel("Nome *");
		lblNome.setBounds(10, 35, 120, 30);
		lblNome.setHorizontalAlignment(JLabel.RIGHT);
		
		add(lblNome);
		
		JLabel lblNome_fantasia = new JLabel("Nome fantasia");
		lblNome_fantasia.setBounds(10, 65, 120, 30);
		lblNome_fantasia.setHorizontalAlignment(JLabel.RIGHT);
		
		add(lblNome_fantasia);
		
		MaskFormatter mask=new MaskFormatter();
		try {
			mask = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtCnpj=new JFormattedTextField(mask);
		txtCnpj.setBounds(135, 5, 150, 25);
		
		add(txtCnpj);
		
		txtNome=new JTextField();
		txtNome.setBounds(135, 35, 150, 25);
		
		add(txtNome);
		
		txtNome_fantasia=new JTextField();
		txtNome_fantasia.setBounds(135, 65, 150, 25);
		
		add(txtNome_fantasia);
		
		
		String[] colunas = new String []{"CNPJ","Nome","Nome fantasia"}; 
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
		//add linha
		//model.addRow(new String[]{"t3","t4"});
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				
				txtCnpj.setText((String)tableModel.getValueAt(row, 0));
				txtNome.setText((String)tableModel.getValueAt(row, 1));
				txtNome_fantasia.setText((String)tableModel.getValueAt(row, 2));
				txtCnpj.setEditable(false);
			}
		});
	}
	
	public void LimparCampos(){
		txtCnpj.setText("");
		txtCnpj.setEditable(true);
		txtNome.setText("");
		txtNome_fantasia.setText("");
	}
	
	public void AtualizaTabela(ArrayList<Fabricante> list){
		int rows = tableModel.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
			tableModel.removeRow(i); 
		}
		
		for (Fabricante fabricante : list) {
			tableModel.addRow(new String[] {fabricante.getCnpj()+"",fabricante.getNome(),fabricante.getNome_fantasia()});
		}
	}
	
	public Fabricante getObjetoTela(){
		
		System.out.println(txtCnpj.getText());
		String cnpj=txtCnpj.getText();
		System.out.println("cnpj "+cnpj);
		
		cnpj=cnpj.replace(".", "").replace("-", "").replace("/", "");
		System.out.println(cnpj);
		
		return new Fabricante(cnpj,txtNome.getText(),txtNome_fantasia.getText());
	}
	
	public boolean ValidarCampos(){
		
		if(txtCnpj.getText().equals("")||txtNome.getText().equals(""))
			return false;
		
		return true;
	}
	
}
