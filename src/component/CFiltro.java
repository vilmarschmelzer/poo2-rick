package component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CFiltro extends JPanel {
	
	private JButton btnRemover;
	private JButton btnAdd;
	private JComboBox cbxOpcao;
	private DefaultComboBoxModel cbxModelOpcao;
	private JButton btnSelecionar;
	private JTextField txtFiltro;
	
	public JButton getBtnRemover(){
		
		return btnRemover;
	}
	
	public JButton getBtnAdd(){
		
		return btnAdd;
	}
	
	public JComboBox getCbxOpcao(){
		return cbxOpcao;
	}
	
	public DefaultComboBoxModel getCbxModelOpcao(){
		return cbxModelOpcao;
	}
	
	public JButton getBtnSelecionar(){
		return btnSelecionar;
	}
	
	public JTextField getTxtFiltro(){
		return txtFiltro;
	}
	
	public CFiltro(){
		
		setLayout(null);
		
		btnRemover=new JButton("-");
		btnRemover.setBounds(5, 5, 45, 25);
		add(btnRemover);
		
		btnAdd=new JButton("+");
		btnAdd.setBounds(55, 5, 45, 25);
		add(btnAdd);
		
		JLabel lblFiltro=new JLabel("Filtrar");
		lblFiltro.setBounds(105,5,50,30);
		add(lblFiltro);
		
		cbxModelOpcao=new DefaultComboBoxModel();
		cbxOpcao=new JComboBox(cbxModelOpcao);
		cbxOpcao.setBounds(155,5,100,25);
		add(cbxOpcao);
		
		btnSelecionar=new JButton("Selecionar");
		txtFiltro=new JTextField();
		add(btnSelecionar);
		add(txtFiltro);
	}
	
	public void addBtnSelecionar(){
		
		btnSelecionar.setVisible(true);
		btnSelecionar.setBounds(260,5,110,25);
		txtFiltro.setText("");
		txtFiltro.setEditable(false);
		txtFiltro.setBounds(380,5,110,25);
	}
	
	public void addTxtFitro(){
		
		btnSelecionar.setVisible(false);
		txtFiltro.setText("");
		txtFiltro.setEditable(true);
		txtFiltro.setBounds(260,5,110,25);
	}
}
