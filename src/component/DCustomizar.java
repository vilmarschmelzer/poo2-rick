package component;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import dao.CaracteristicaDAO;
import dao.CorDAO;
import dao.InstrumentoDAO;
import dao.MadeiraDAO;
import decorator.CaractDecorator;
import decorator.InstAbsDecorator;
import decorator.InstDecorator;
import models.*;

public class DCustomizar extends JDialog {
	
	private final Instrumento inst;
	
	private DefaultTableModel tableModelBase;
	private JTable tableBase;
	
	private DefaultTableModel tableModelNovo;
	private JTable tableNovo;
	
	private JLabel lblCustoNovo;
	
	private ArrayList<Inst_Caract> listCaract;
	
	public DCustomizar(JFrame frame,final Instrumento inst){
		super(frame, true);
		this.inst=inst;
		setLayout(null);
		setResizable(false);
		setBounds(new Rectangle(700,300));
		setTitle("Adicione novas caracteristicas");
		
		JLabel lblInstrumento=new JLabel("Inst.: "+inst.getId_inst()+" - "+inst.getNome()+", Tipo: "+inst.getTipo().getTextoCombo()+", Modelo: "+inst.getModelo().getTextoCombo()+", Fabricante: "+inst.getFabricante().getTextoCombo());
		lblInstrumento.setBounds(5, 5, 700, 25);
		add(lblInstrumento);
		
		JButton btnAdd=new JButton("Adicionar");
		btnAdd.setBounds(350,200,100,25);
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DSelecionaCaract seleciona=new DSelecionaCaract(null,"Selecione uma caracteristica");				
				seleciona.setCaracteristica();
				
				seleciona.addWindowListener(new WindowAdapter() {
			
					@Override
					public void windowClosed(WindowEvent e) {
						Caracteristica caract=((DSelecionaCaract)e.getSource()).getCaractSelecionada();
						if(caract!=null){
							AddTabelaCaractNovo(caract);
						}
					}
				});
				seleciona.show();
			}
		});
		add(btnAdd);
		
		JButton btnRemover=new JButton("Remover");
		btnRemover.setBounds(460,200,100,25);
		btnRemover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RemoverTabelaCaractNovo();
			}
		});
		add(btnRemover);
		
		
		JButton btnCalcular=new JButton("Calcular");
		btnCalcular.setBounds(585,200,100,25);
		btnCalcular.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lblCustoNovo.setText("Novo custo: "+ Calcular.CustoCaracteristicas(inst,tableModelNovo));
			}
		});
		add(btnCalcular);
		
		
		tableModelBase = new DefaultTableModel(null,new String []{"Nome","Qtd","Valor"});
		
		tableBase = new JTable(tableModelBase){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				
				return false;
			}
		};
		
		JPanel pnCaractBase=new JPanel();
		pnCaractBase.setBounds(5, 35, 340, 160);
		TitledBorder titleCaractBase= BorderFactory.createTitledBorder("Caracteristicas base");
		pnCaractBase.setBorder(titleCaractBase);
		pnCaractBase.setLayout(null);
		
	    JScrollPane scrollPane = new JScrollPane(tableBase);  
		
	    scrollPane.setBounds(10, 20, 320, 130);
	    pnCaractBase.add(scrollPane);
		
	    add(pnCaractBase);
	    
		tableModelNovo = new DefaultTableModel(null,new String []{"id","Nome","Qtd","Valor"});
		tableNovo = new JTable(tableModelNovo){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				
				if(colIndex==2)
					return true;
				
				return false;
			}
		};
		
		
		JPanel pnNovasCaract=new JPanel();
		pnNovasCaract.setBounds(350, 35, 340, 160);
		
		TitledBorder titleCaract= BorderFactory.createTitledBorder("Caracteristicas adicionadas");
		pnNovasCaract.setBorder(titleCaract);
		pnNovasCaract.setLayout(null);
		
		
	    JScrollPane scrollPaneNovo = new JScrollPane(tableNovo);  
		
	    scrollPaneNovo.setBounds(10, 20, 320, 130);
	    pnNovasCaract.add(scrollPaneNovo);
		add(pnNovasCaract);
		
		tableNovo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		JLabel lblCustoNormal=new JLabel("Custo normal: ");
		lblCustoNormal.setBounds(10, 225, 150, 25);
		add(lblCustoNormal);
		
		lblCustoNovo=new JLabel("Novo custo: ");
		lblCustoNovo.setBounds(350, 225, 150, 25);
		add(lblCustoNovo);
		
		InstrumentoDAO instDAO=new InstrumentoDAO();
		ArrayList<Inst_Caract> listCaract= instDAO.getCaracteristicas(inst);
		AtualizaTabelaCaractBase(listCaract);
		
		
		lblCustoNormal.setText("Custo normal: "+inst.getValor());
	}
	
	public void AtualizaTabelaCaractBase(ArrayList<Inst_Caract> list){
		int rows = tableModelBase.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
			tableModelBase.removeRow(i); 
		}
		
		if(list!=null){
			for (Inst_Caract caract : list) {
				tableModelBase.addRow(new String[] {caract.getCaracteristica().getNome(),caract.getQtd()+"",caract.getCaracteristica().getValor()+""});
			}
		}
	}
	public void AddTabelaCaractNovo(Caracteristica caract){
		
		if(caract!=null){
			
			Inst_Caract instCaract=new Inst_Caract(caract,1);
			tableModelNovo.addRow(new Object[] {instCaract.getCaracteristica().getId(),instCaract.getCaracteristica().getNome(),new Integer(instCaract.getQtd()),instCaract.getCaracteristica().getValor()+""});
		}
	}
	public void RemoverTabelaCaractNovo(){
		
		int row=tableNovo.getSelectedRow();
		
		if(row>-1){
			tableModelNovo.removeRow(row);
		}
	}
	
}
