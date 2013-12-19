package component;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import dao.FabricanteDAO;
import dao.ModeloDAO;
import dao.TipoDAO;

public class CFiltros extends JPanel {
	
	ArrayList<String> listOpcao;
	
	public CFiltros(){
		listOpcao=new ArrayList();
		
		listOpcao.add("Nome");
		listOpcao.add("Tipo");
		listOpcao.add("Modelo");
		listOpcao.add("Fabricante");
		listOpcao.add("Caracteristica");
		
		setLayout(new GridLayout(0,2));
		addFiltro();
	}
	
	private void setListOpcao(DefaultComboBoxModel model){
		
		model.removeAllElements();
		for (String string : listOpcao) {
			model.addElement(string);
		}
	}
	
	private void addFiltro(){
		
		CFiltro cFiltro=new CFiltro();
		
		cFiltro.getBtnAdd().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getComponentCount()<12){
					addFiltro();
				}
			}
		});
		
		cFiltro.getCbxOpcao().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if((((JComboBox)e.getSource()).getSelectedItem().toString()).equals("Nome"))
					((CFiltro)((JComboBox)e.getSource()).getParent()).addTxtFitro();
				else{
					((CFiltro)((JComboBox)e.getSource()).getParent()).addBtnSelecionar();
				}
			}
		});
		
		cFiltro.getBtnSelecionar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String opcao=((CFiltro)((JButton)e.getSource()).getParent()).getCbxOpcao().getSelectedItem().toString();
				
				DSelecionaCaract selecionar = null;
				
				switch (opcao) {
				case "Tipo":
					selecionar=new DSelecionaCaract(null,((CFiltro)((JButton)e.getSource()).getParent()).getTxtFiltro(),"Selecione um Tipo");
					selecionar.setListTipo(new TipoDAO().getAll());
					break;
				
				case "Modelo":
					selecionar=new DSelecionaCaract(null,((CFiltro)((JButton)e.getSource()).getParent()).getTxtFiltro(),"Selecione um Modelo");
					selecionar.setListModelo(new ModeloDAO().getAll());
					break;
					
				case "Fabricante":
					selecionar=new DSelecionaCaract(null,((CFiltro)((JButton)e.getSource()).getParent()).getTxtFiltro(),"Selecione um Fabricante");
					selecionar.setListFabricante(new FabricanteDAO().getAll());
					break;
					
				default:
					selecionar=new DSelecionaCaract(null,((CFiltro)((JButton)e.getSource()).getParent()).getTxtFiltro(),"Selecione uma caracteristica");
					selecionar.setCaracteristica();
					break;
				}
				selecionar.show();
			}
		});
		
		cFiltro.getBtnRemover().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if(getComponentCount()>1){
					((CFiltro)((JButton)e.getSource()).getParent()).setVisible(false);
					remove(((JButton)e.getSource()).getParent());
					revalidate();
				}
			}
		});
		
		setListOpcao(cFiltro.getCbxModelOpcao());
		
		add(cFiltro);
		revalidate();
	}
	
	public Map<String, String> getFiltros(){
		Map<String, String> filtros = new HashMap<String, String>();
		
		String chave;
		String valor;
		
		for (Component filtro : getComponents()) {
			if(filtro instanceof CFiltro){
				chave=((CFiltro)filtro).getCbxOpcao().getSelectedItem().toString();
				valor=((CFiltro)filtro).getTxtFiltro().getText();
				
				if(!chave.equals("Nome")&&valor.equals(""))
					continue;
				
				if(filtros.containsKey(chave)&&!chave.equals("Nome")){
					filtros.put(chave, filtros.get(chave)+","+valor.split(" - ")[0]);
				}
				else
					filtros.put(chave, valor.split(" - ")[0]);
			}
		}
		return filtros;
	}
}
