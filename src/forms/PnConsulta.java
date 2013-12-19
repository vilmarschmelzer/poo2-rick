package forms;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import component.CFiltros;
import component.DCustomizar;

import dao.InstrumentoDAO;
import models.*;

public class PnConsulta extends JPanel {
	
	private JTable tableInst;
	private JTable tableCaract;
	private CFiltros cFiltros;
	private JButton btnPesquisa;
	private DefaultTableModel tableModelInst;
	private DefaultTableModel tableModelCaract;
	private InstrumentoDAO instDAO;
	
	
	public PnConsulta(){
		
		setLayout(null);
		
		btnPesquisa=new JButton("Pesquisar");
		btnPesquisa.setBounds(5, 5, 120, 25);
		btnPesquisa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(instDAO==null)
					instDAO=new InstrumentoDAO();
				AtualizaTabelaCaract(null);
				AtualizaTabelaInst(instDAO.pesquisar(cFiltros.getFiltros()));
			}
		});
		add(btnPesquisa);
		
		cFiltros=new CFiltros();
		cFiltros.setBounds(5, 35, 1000,180);
		add(cFiltros);
		
		JPanel pnInstrumentos=new JPanel();
		pnInstrumentos.setBounds(5, 230, 995, 340);
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Instrumentos encontrados");
		pnInstrumentos.setBorder(title);
		pnInstrumentos.setLayout(null);
		add(pnInstrumentos);
		
		String[] colunas = new String []{"Id","Nome","Tipo","Modelo","Fabricante","Valor"}; 
		tableModelInst = new DefaultTableModel(null,colunas);
		
		
		tableInst = new JTable(tableModelInst){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		
	    JScrollPane scrollPaneInst = new JScrollPane(tableInst);  
		
	    scrollPaneInst.setBounds(10, 20, 500, 280);
	    pnInstrumentos.add(scrollPaneInst);
		
	    JButton btnCustomizar=new JButton("Customizar");
	    btnCustomizar.setBounds(10, 305, 120, 25);
	    btnCustomizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row=tableInst.getSelectedRow();
				if(row >-1){
					
					String[] tipo=tableModelInst.getValueAt(row, 2).toString().split(" - ");
					String[] modelo=tableModelInst.getValueAt(row, 3).toString().split(" - ");
					String[] fabricante=tableModelInst.getValueAt(row, 4).toString().split(" - ");
					
					Instrumento inst=new Instrumento(
							Integer.parseInt(tableModelInst.getValueAt(row, 0).toString()),0,
							tableModelInst.getValueAt(row, 1).toString(),
							new Tipo(Integer.parseInt(tipo[0]),tipo[1]),
							new Modelo(Integer.parseInt(modelo[0]),modelo[1]),
							new Fabricante(fabricante[0] ,fabricante[1],""),Double.parseDouble(tableModelInst.getValueAt(row, 5).toString()));
					
					new DCustomizar(null,inst).show();
				}
				else
					JOptionPane.showMessageDialog(null, "Selecione um instrumento para customizar!");
			}
		});
	    
	    pnInstrumentos.add(btnCustomizar);
	    
		JPanel pnCaracteristicas=new JPanel();
		pnCaracteristicas.setBounds(520, 10, 470, 325);
		
		TitledBorder titleCaract= BorderFactory.createTitledBorder("Caracteristicas");
		pnCaracteristicas.setBorder(titleCaract);
		pnCaracteristicas.setLayout(null);
		
		tableModelCaract = new DefaultTableModel(null,new String[]{"Nome","Qtd"});
		
		tableCaract = new JTable(tableModelCaract){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		
		JScrollPane scrollPaneCaract = new JScrollPane(tableCaract);
		scrollPaneCaract.setBounds(10,20,455,295);
		pnCaracteristicas.add(scrollPaneCaract);
		pnInstrumentos.add(pnCaracteristicas);
		
		
		tableInst.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tableInst.getSelectedRow();
				
				Instrumento inst=new Instrumento();
				inst.setId_inst(Integer.parseInt(tableInst.getValueAt(row, 0).toString()));
				AtualizaTabelaCaract(instDAO.getCaracteristicas(inst));
			}
		});
	}
	
	public void AtualizaTabelaInst(ArrayList<Instrumento> list){
		int rows = tableModelInst.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
			tableModelInst.removeRow(i); 
		}
		if(list!=null){
			for (Instrumento inst : list) {
				tableModelInst.addRow(new String[] {inst.getId_inst()+"",
						inst.getNome(),
						inst.getTipo().getTextoCombo(),
						inst.getModelo().getTextoCombo(),
						inst.getFabricante().getTextoCombo(),
						inst.getValor()+""});
			}
		}
	}
	
	public void AtualizaTabelaCaract(ArrayList<Inst_Caract> list){
		int rows = tableModelCaract.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
			tableModelCaract.removeRow(i); 
		}
		
		if(list!=null){
			for (Inst_Caract caract : list) {
				tableModelCaract.addRow(new String[] {caract.getCaracteristica().getNome(),caract.getQtd()+""});
			}
		}
	}
}
