package component;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.CaracteristicaDAO;
import dao.CorDAO;
import dao.InstrumentoDAO;
import dao.MadeiraDAO;
import models.*;

public class DSelecionaCaract extends JDialog {
	
	private DefaultTableModel tableModel;
	private JRadioButton rbtnInst;
	private JRadioButton rbtnCor;
	private JRadioButton rbtnMadeira;
	private JRadioButton rbtnOutros;
	private JTable table;
	final JTextField txtSetTexto;
	
	private InstrumentoDAO instrumentoDAO;
	private CorDAO corDAO;
	private MadeiraDAO madeiraDAO;
	private CaracteristicaDAO caracteristicaDAO;
	
	public void setListTipo(ArrayList<Tipo> listTipo){
		tableModel = new DefaultTableModel(null,new String[]{"Id","Nome"});
	
		for (Tipo tipo : listTipo) {
			tableModel.addRow(new String[] {tipo.getId()+"",tipo.getNome()});
		}
		setTable();
	}
	
	public void setListModelo(ArrayList<Modelo> listModelo){
		tableModel = new DefaultTableModel(null,new String[]{"Id","Nome"});
	
		for (Modelo modelo : listModelo) {
			tableModel.addRow(new String[] {modelo.getId()+"",modelo.getNome()});
		}
		setTable();
	}
	
	public void setListFabricante(ArrayList<Fabricante> listFabricante){
		tableModel = new DefaultTableModel(null,new String[]{"CNPJ","Nome"});
		
		for (Fabricante fabricante : listFabricante) {
			tableModel.addRow(new String[] {fabricante.getCnpj(),fabricante.getNome()});
		}
		setTable();
	}
	
	public DSelecionaCaract(JFrame frame, String titulo) {
		this(frame,null,titulo);
	}
	
	public DSelecionaCaract(JFrame frame,final JTextField txtSetTexto, String titulo) {
		super(frame, true);
		this.txtSetTexto=txtSetTexto;
		setLayout(null);
		setResizable(false);
		setBounds(new Rectangle(335,200));
		setTitle(titulo);
		
		JButton btnSelecionado=new JButton("Selecionado");
		btnSelecionado.setBounds(195,140,130,25);
		
		btnSelecionado.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtSetTexto!=null)
					txtSetTexto.setText(getItemSelecionado());
				dispose();
			}
		});
		
		add(btnSelecionado);
		
		
		super.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(txtSetTexto!=null)
					txtSetTexto.setText("");
				super.windowClosing(e);
			}
		});
		
	}
	
	public void setTable(){
		table = new JTable(tableModel){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				
				return false;
			}
		};
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
	    JScrollPane scrollPane = new JScrollPane(table);  
		
	    scrollPane.setBounds(5, 5, 320, 130);
		add(scrollPane);
	}
	
	ActionListener rbtnActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			AtualizaTabela();
		}
	};
	
	public void AtualizaTabela(){
		
		int rows = tableModel.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
			tableModel.removeRow(i); 
		}
		
		ArrayList items=new ArrayList();
		
		if(rbtnCor.isSelected()){
			
			if(corDAO==null)
				corDAO=new CorDAO();
			items=corDAO.getAll();
		}
		else if(rbtnInst.isSelected()){
			
			if(instrumentoDAO==null)
				instrumentoDAO=new InstrumentoDAO();
			
			items=instrumentoDAO.getAll();
		}
		else if(rbtnMadeira.isSelected()){
			
			if(madeiraDAO==null)
				madeiraDAO=new MadeiraDAO();
			items=madeiraDAO.getAll();
		}
		else if(rbtnOutros.isSelected()){
			if(caracteristicaDAO==null)
				caracteristicaDAO=new CaracteristicaDAO();
			items=caracteristicaDAO.getAll();
		}
		
		if(items.size()>0){
			
			if(items.get(0) instanceof Cor){
				ArrayList<Cor> its=items;
				for (Cor item : its) {
						tableModel.addRow(new String[] {item.getId()+"",item.getNome(),item.getValor()+""});
				}
			}
			else if(items.get(0) instanceof Madeira){
				ArrayList<Madeira> its=items;
				for (Madeira item : its) {
						tableModel.addRow(new String[] {item.getId()+"",item.getNome(),item.getValor()+""});
				}
			}
			else if(items.get(0) instanceof Instrumento){
				ArrayList<Instrumento> its=items;
				for (Instrumento item : its) {
						tableModel.addRow(new String[] {item.getId()+"",item.getNome(),item.getValor()+""});
				}
			}
			else{
				ArrayList<Caracteristica> its=items;
				for (Caracteristica item : its) {
						tableModel.addRow(new String[] {item.getId()+"",item.getNome(),item.getValor()+""});
				}
			}
		}
	}
	
	public void setCaracteristica(){
		tableModel = new DefaultTableModel(null,new String[]{"Id","Nome","Valor"});
		
		table = new JTable(tableModel){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				
				return false;
			}
		};
	    JScrollPane scrollPane = new JScrollPane(table);  
		
	    scrollPane.setBounds(5, 30, 320, 105);
		add(scrollPane);
		
		ButtonGroup btnRbGroup=new ButtonGroup();
		
		rbtnInst=new JRadioButton("Instrumento");
		rbtnInst.setSelected(true);
		rbtnInst.addActionListener(rbtnActionListener);
		btnRbGroup.add(rbtnInst);
		add(rbtnInst);
		
		rbtnInst.setBounds(5, 5, 118, 25);
		
		rbtnCor=new JRadioButton("Cor");
		rbtnCor.addActionListener(rbtnActionListener);
		btnRbGroup.add(rbtnCor);
		rbtnCor.setBounds(120, 5, 50, 25);
		add(rbtnCor);
		
		rbtnMadeira=new JRadioButton("Madeira");
		rbtnMadeira.addActionListener(rbtnActionListener);
		btnRbGroup.add(rbtnMadeira);
		rbtnMadeira.setBounds(170, 5, 85, 25);
		add(rbtnMadeira);
		
		rbtnOutros=new JRadioButton("Outros");
		rbtnOutros.addActionListener(rbtnActionListener);
		rbtnOutros.setBounds(255, 5, 85, 25);
		btnRbGroup.add(rbtnOutros);
		add(rbtnOutros);
		
		AtualizaTabela();
	}
	private String getItemSelecionado(){
		int row = table.getSelectedRow();
		if(row>=0)
			return ((String)tableModel.getValueAt(row, 0))+" - "+((String)tableModel.getValueAt(row, 1));
		return "";
	}
	
	public Caracteristica getCaractSelecionada(){
		
		int row = table.getSelectedRow();
		if(row>=0)
			return new Caracteristica(Integer.parseInt(tableModel.getValueAt(row, 0).toString()),
					tableModel.getValueAt(row, 1).toString(),
					Double.parseDouble(tableModel.getValueAt(row, 2).toString()));
		return null;
	}
}
