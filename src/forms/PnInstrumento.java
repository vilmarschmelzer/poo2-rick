package forms;

import java.awt.CheckboxGroup;
import java.awt.ItemSelectable;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TabableView;

import component.Calcular;

import dao.CaracteristicaDAO;
import dao.CorDAO;
import dao.InstrumentoDAO;
import dao.MadeiraDAO;
import models.*;

public class PnInstrumento extends JPanel {
	
	private InstrumentoDAO instrumentoDAO;
	private CorDAO corDAO;
	private MadeiraDAO madeiraDAO;
	private CaracteristicaDAO caracteristicaDAO;
	
	private JTextField txtId;
	private JTextField txtNome;
	private JComboBox cbxTipo;
	private DefaultComboBoxModel cbxTipoModel;
	private JComboBox cbxModelo;
	private DefaultComboBoxModel cbxModeloModel;
	private JComboBox cbxFabricante;
	private DefaultComboBoxModel cbxFabricanteModel;
	private JTextField txtValor;
	
	private JComboBox cbxCaract;
	private DefaultComboBoxModel cbxCaractModel;
	private JTextField txtQtdCaract;
	
	private JRadioButton rbtnInst;
	private JRadioButton rbtnCor;
	private JRadioButton rbtnMadeira;
	private JRadioButton rbtnOutros;
	
	private DefaultTableModel tableInstModel;
	private JTable tableInst;
	
	private JTable tableCaract;
	private DefaultTableModel tableCaractModel;
	
	public JTextField getTxtId(){
		return txtId;
	}
	
	public JTextField getTxtNome(){
		return txtNome;
	}
	
	public JComboBox getCbxTipo(){
		return cbxTipo;
	}
	
	public JComboBox getCbxModelo(){
		return cbxModelo;
	}
	
	public JComboBox getCbxFabricante(){
		return cbxFabricante;
	}
	
	public JTextField getTxtValor(){
		return txtValor;
	}
	
	public DefaultTableModel getTableInstModel(){
		return tableInstModel;
	}
	
	public PnInstrumento(){
		
		setLayout(null);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(10, 5, 100, 30);
		lblId.setHorizontalAlignment(JLabel.RIGHT);
		add(lblId);
		
		JLabel lblNome = new JLabel("Nome *");
		lblNome.setBounds(10, 35, 100, 30);
		lblNome.setHorizontalAlignment(JLabel.RIGHT);
		
		add(lblNome);
		
		JLabel lblTipo = new JLabel("Tipo *");
		lblTipo.setBounds(10, 65, 100, 30);
		lblTipo.setHorizontalAlignment(JLabel.RIGHT);
		
		add(lblTipo);
		
		JLabel lblModelo = new JLabel("Modelo *");
		lblModelo.setBounds(10, 100, 100, 30);
		lblModelo.setHorizontalAlignment(JLabel.RIGHT);
		
		add(lblModelo);
		
		JLabel lblFabricante = new JLabel("Fabricante *");
		lblFabricante.setBounds(10, 135, 100, 30);
		lblFabricante.setHorizontalAlignment(JLabel.RIGHT);
		
		add(lblFabricante);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(10, 165, 100, 30);
		lblValor.setHorizontalAlignment(JLabel.RIGHT);
		
		add(lblValor);
		
		txtId=new JTextField();
		txtId.setBounds(120, 5, 50, 25);
		txtId.setEditable(false);
		
		add(txtId);
		
		txtNome=new JTextField();
		txtNome.setBounds(120, 35, 250, 25);
		
		add(txtNome);
		
		cbxTipoModel=new DefaultComboBoxModel();
		cbxTipo=new JComboBox(cbxTipoModel);
		cbxTipo.setBounds(120, 65, 250, 25);
		
		add(cbxTipo);
		
		cbxModeloModel=new DefaultComboBoxModel();
		cbxModelo=new JComboBox(cbxModeloModel);
		cbxModelo.setBounds(120, 100, 250, 25);
		
		add(cbxModelo);
		
		cbxFabricanteModel=new DefaultComboBoxModel();
		cbxFabricante=new JComboBox(cbxFabricanteModel);
		cbxFabricante.setBounds(120, 135, 250, 25);
		
		add(cbxFabricante);
		
		txtValor=new JTextField();
		txtValor.setBounds(120, 165, 250, 25);
		txtValor.setText("0.0");
		
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
		
		String[] colunasTableInst = new String []{"Id","Nome","Tipo","Modelo","Fabricante","Valor"}; 
		tableInstModel = new DefaultTableModel(null,colunasTableInst);
		
		tableInst = new JTable(tableInstModel){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		 
	    JScrollPane scrollPane = new JScrollPane(tableInst);  
		
	    scrollPane.setBounds(10, 200, 380, 200);
		add(scrollPane);
		
		JPanel pnCaracteristica=new JPanel();
		pnCaracteristica.setBounds(400, 5, 480, 380);
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Caracteristicas");
		pnCaracteristica.setBorder(title);
		pnCaracteristica.setLayout(null);
		
		JButton btnAdd=new JButton("Adicionar");
		btnAdd.setBounds(10, 25, 100, 25);
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(cbxCaractModel.getSelectedItem().equals("")){
					JOptionPane.showMessageDialog(null, "Informe a caracteristica?");
				}
				else{
					
					boolean existe=false;
					for (int i = 0; i < tableCaractModel.getRowCount(); i++) {
						
						if(cbxCaractModel.getSelectedItem().toString().equals(tableCaractModel.getValueAt(i, 0).toString())){
							existe=true;
							break;
						}
					}
					
					if(!existe){
					
						String qtd="0";
						if(!txtQtdCaract.getText().equals(""))
							qtd=txtQtdCaract.getText();
												
						String[] caracteristica=cbxCaractModel.getSelectedItem().toString().split(" - ");
						
						tableCaractModel.addRow(new String[]{caracteristica[0],caracteristica[1],qtd,caracteristica[2]});
						LimpaCamposCaract();
					}
					else
						JOptionPane.showMessageDialog(null, "Caracteristica já adicionada ao instrumento!");
				}
				
			}
		});
		
		
		JButton btnRemover=new JButton("Remover");
		btnRemover.setBounds(110, 25, 100, 25);
		
		btnRemover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableCaract.getSelectedRow()==-1){
					JOptionPane.showMessageDialog(null, "Selecione uma caracteristica para remover!");
				}
				else{
					LimpaCamposCaract();
					tableCaractModel.removeRow(tableCaract.getSelectedRow());
					
				}
				
			}
		});
		
		JButton btnCalcular=new JButton("Calcular");
		btnCalcular.setBounds(210,25,100,25);
		btnCalcular.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txtValor.setText(Calcular.CustoCaracteristicas(new Instrumento(), tableCaractModel)+"");
			}
		});
		
		
		
		JLabel lblFiltro=new JLabel("Filtrar");
		lblFiltro.setBounds(10, 55, 110, 30);
		lblFiltro.setHorizontalAlignment(JLabel.RIGHT);
		
		JLabel lblCaract=new JLabel("Caracteristica *");
		lblCaract.setBounds(10, 85, 110, 30);
		lblCaract.setHorizontalAlignment(JLabel.RIGHT);
		
		JLabel lblQtdCaract=new JLabel("Quantidade *");
		lblQtdCaract.setBounds(10, 115, 110, 30);
		lblQtdCaract.setHorizontalAlignment(JLabel.RIGHT);
		
		
		//filtro
		
		ActionListener rbtnActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AtualizaComboCaracteristica();
			}
		};
		
		
		ButtonGroup btnRbGroup=new ButtonGroup();
		
		rbtnInst=new JRadioButton("Instrumento");
		rbtnInst.setSelected(true);
		rbtnInst.addActionListener(rbtnActionListener);
		btnRbGroup.add(rbtnInst);
		
		rbtnInst.setBounds(130, 55, 118, 25);
		
		rbtnCor=new JRadioButton("Cor");
		rbtnCor.addActionListener(rbtnActionListener);
		btnRbGroup.add(rbtnCor);
		rbtnCor.setBounds(245, 55, 50, 25);
		
		
		rbtnMadeira=new JRadioButton("Madeira");
		rbtnMadeira.addActionListener(rbtnActionListener);
		btnRbGroup.add(rbtnMadeira);
		rbtnMadeira.setBounds(300, 55, 85, 25);
		
		rbtnOutros=new JRadioButton("Outros");
		rbtnOutros.addActionListener(rbtnActionListener);
		rbtnOutros.setBounds(385, 55, 85, 25);
		btnRbGroup.add(rbtnOutros);
		
		cbxCaractModel=new DefaultComboBoxModel();
		cbxCaract=new JComboBox(cbxCaractModel);
		cbxCaract.setBounds(130, 85, 250, 25);
		
		
		
		txtQtdCaract=new JTextField();
		txtQtdCaract.setBounds(130, 115, 100, 25);
		txtQtdCaract.setText("0");
		
		txtQtdCaract.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				txtQtdCaract.setText(txtQtdCaract.getText().replaceAll("[^0-9]", ""));
			}
		});
		
		String[] colunasTableCaract = new String []{"id","Caracteristica","Qtd","Valor"}; 
		tableCaractModel = new DefaultTableModel(null,colunasTableCaract);
		
		tableCaract = new JTable(tableCaractModel){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				
				if(colIndex==2)
					return true;
				
				return false;
			}
		};  
	    
	    JScrollPane scrollPaneCaract = new JScrollPane(tableCaract);  
		
	    scrollPaneCaract.setBounds(10, 155, 370, 150);
		
		
		pnCaracteristica.add(btnAdd);
		pnCaracteristica.add(btnRemover);
		pnCaracteristica.add(btnCalcular);
		pnCaracteristica.add(lblFiltro);
		pnCaracteristica.add(lblCaract);
		pnCaracteristica.add(lblQtdCaract);
		pnCaracteristica.add(rbtnInst);
		pnCaracteristica.add(rbtnCor);
		pnCaracteristica.add(rbtnMadeira);
		pnCaracteristica.add(rbtnOutros);
		pnCaracteristica.add(cbxCaract);
		pnCaracteristica.add(txtQtdCaract);
		pnCaracteristica.add(scrollPaneCaract);
		
		add(pnCaracteristica);
		LimparCampos();
		AtualizaComboCaracteristica();
		
		tableInst.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tableInst.getSelectedRow();
				
				txtId.setText((String)tableInstModel.getValueAt(row, 0));
				txtNome.setText((String)tableInstModel.getValueAt(row, 1));
				cbxTipo.setSelectedItem((String)tableInstModel.getValueAt(row, 2));
				cbxModelo.setSelectedItem((String)tableInstModel.getValueAt(row, 3));
				cbxFabricante.setSelectedItem((String)tableInstModel.getValueAt(row, 4));
				txtValor.setText((String)tableInstModel.getValueAt(row, 5));
				
				if(instrumentoDAO==null)
					instrumentoDAO=new InstrumentoDAO();
				
				AtualizaTabelaCaracteristica(instrumentoDAO.getCaracteristicas
						(new Instrumento(Integer.parseInt(txtId.getText()),0,null,null,null,null,0)
						));
				
				AtualizaComboCaracteristica();
			}
		});
	}
	
	private void LimpaCamposCaract(){
		cbxCaractModel.setSelectedItem("");
		txtQtdCaract.setText("0");
	}
	
	public void LimparCampos(){
		txtId.setText("0");
		txtNome.setText("");
		cbxTipo.setSelectedItem("");
		cbxModelo.setSelectedItem("");
		cbxFabricante.setSelectedItem("");
		txtValor.setText("0");
		tableCaract.getSelectionModel().clearSelection();
		
		LimpaCamposCaract();
		LimpaTabelaCaracteristica();
	}
	
	public void AtualizaTabela(ArrayList<Instrumento> list){
		int rows = tableInstModel.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
			tableInstModel.removeRow(i); 
		}
		
		for (Instrumento inst : list) {
			tableInstModel.addRow(new String[] 
					{inst.getId_inst()+"",
					inst.getNome(),
					inst.getTipo().getTextoCombo(),
					inst.getModelo().getTextoCombo(),
					inst.getFabricante().getTextoCombo(),
					inst.getValor()+""});
		}
	}
	
	public void LimpaTabelaCaracteristica(){
		int rows = tableCaractModel.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
			tableCaractModel.removeRow(i); 
		}
	}
	
	public void AtualizaTabelaCaracteristica(ArrayList<Inst_Caract> list){
		LimpaTabelaCaracteristica();
		
		if(list==null)
			return;
		
		for (Inst_Caract inst_caract : list) {
			tableCaractModel.addRow(new String[] 
					{inst_caract.getCaracteristica().getId()+"",
					inst_caract.getCaracteristica().getNome(),
					inst_caract.getQtd()+"",
					inst_caract.getCaracteristica().getValor()+""});
		}
	}
	
	public Instrumento getObjetoTela(){
		String[] tipo=cbxTipo.getSelectedItem().toString().split(" - ");
		String[] modelo=cbxModelo.getSelectedItem().toString().split(" - ");
		String[] fabricante=cbxFabricante.getSelectedItem().toString().split(" - ");
		
		Instrumento inst=new Instrumento(
				Integer.parseInt(txtId.getText()),0,
				txtNome.getText(),
				new Tipo(Integer.parseInt(tipo[0]),tipo[1]),
				new Modelo(Integer.parseInt(modelo[0]),modelo[1]),
				new Fabricante(fabricante[0] ,fabricante[1],""),Double.parseDouble(txtValor.getText()));
		
		inst.setItems(getCaracteristicas());
		
		return inst;
	}
	
	public boolean ValidarCampos(){
		
		if(txtId.getText().equals("")||txtNome.getText().equals(""))
			return false;
		
		return true;
	}
	
	public void AtualizaComboTipo(ArrayList<String> items){
		
		cbxTipoModel.removeAllElements();
		for (String string : items) {
			cbxTipoModel.addElement(string);
		}
		cbxTipoModel.setSelectedItem("");
	}
	
	public void AtualizaComboModelo(ArrayList<String> items){
		
		cbxModeloModel.removeAllElements();
		for (String string : items) {
			cbxModeloModel.addElement(string);
		}
		cbxModeloModel.setSelectedItem("");
		
	}
	public void AtualizaComboFabricante(ArrayList<String> items){
		
		cbxFabricanteModel.removeAllElements();
		for (String string : items) {
			cbxFabricanteModel.addElement(string);
		}
		cbxFabricanteModel.setSelectedItem("");
	}
	
	public void AtualizaComboCaracteristica(){
		cbxCaractModel.removeAllElements();
		ArrayList<String> items=new ArrayList();
		if(rbtnCor.isSelected()){
			
			if(corDAO==null)
				corDAO=new CorDAO();
			items=corDAO.getAllComboTexto();
		}
		else if(rbtnInst.isSelected()){
			
			if(instrumentoDAO==null)
				instrumentoDAO=new InstrumentoDAO();
			
			items=instrumentoDAO.getAllComboText(Integer.parseInt(txtId.getText()));
		}
		else if(rbtnMadeira.isSelected()){
			
			if(madeiraDAO==null)
				madeiraDAO=new MadeiraDAO();
			items=madeiraDAO.getAllComboTexto();
		}
		else if(rbtnOutros.isSelected()){
			if(caracteristicaDAO==null)
				caracteristicaDAO=new CaracteristicaDAO();
			items=caracteristicaDAO.getAllComboTexto();
		}
			
		for (String string : items) {
			cbxCaractModel.addElement(string);
		}
		cbxCaractModel.setSelectedItem("");
	}
	public ArrayList<Inst_Caract> getCaracteristicas(){
		
		ArrayList<Inst_Caract> listCarac=new ArrayList();
		
		String[] caracteristica;
		
		for (int i = 0; i < tableCaract.getRowCount(); i++) {
			caracteristica=tableCaractModel.getValueAt(i, 0).toString().split(" - ");
			
			listCarac.add(new Inst_Caract(new 
					Caracteristica(Integer.parseInt(tableCaractModel.getValueAt(i, 0).toString()),
							tableCaractModel.getValueAt(i, 1).toString(),
							Double.parseDouble(tableCaractModel.getValueAt(i, 3).toString())),
					Integer.parseInt(tableCaractModel.getValueAt(i, 2).toString())));
		}
		
		return listCarac;
	}
	
}
