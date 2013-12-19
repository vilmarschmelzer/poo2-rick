package forms;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import dao.*;

public class FrmInit extends JFrame {
	
	private JTabbedPane tabbInit;
	private PnCor pnCor;
	private CorDAO corDAO;
	private PnTipo pnTipo;
	private TipoDAO tipoDAO;
	private PnModelo pnModelo;
	private ModeloDAO modeloDAO;
	private PnMadeira pnMadeira;
	private MadeiraDAO madeiraDAO;
	private PnCaracteristica pnCaracteristica;
	private CaracteristicaDAO caracteristicaDAO;
	private PnFabricante pnFabricante;
	private FabricanteDAO fabricanteDAO;
	private PnInstrumento pnInstrumento;
	private InstrumentoDAO instrumentoDAO;
	
	private PnConsulta pnConsulta;
	
	public FrmInit(){
		
		
		this.setResizable(false);
		
		JPanel pnInit = new JPanel();   
		pnInit.setLayout(new BoxLayout(pnInit, BoxLayout.Y_AXIS));
		
		JPanel pnButtons=new JPanel();
		
		
		JButton btnNovo =new JButton("Novo");
		JButton btnSalvar =new JButton("Salvar");
		JButton btnRemover =new JButton("Remover");
		
		pnButtons.add(btnNovo);
		pnButtons.add(btnSalvar);
		pnButtons.add(btnRemover);
		
		
		JPanel pnTabb=new JPanel();
		tabbInit = new JTabbedPane();
		pnConsulta=new PnConsulta();
		
		pnInstrumento=new PnInstrumento();
		tabbInit.addTab("Instrumento", pnInstrumento);
		tabbInit.addTab("Consulta", pnConsulta);
		pnFabricante=new PnFabricante();
		tabbInit.addTab("Fabricante", pnFabricante);
		pnModelo=new PnModelo();
		tabbInit.addTab("Modelo", pnModelo);
		pnMadeira=new PnMadeira();
		tabbInit.addTab("Madeira", pnMadeira);
		pnTipo=new PnTipo();
		tabbInit.addTab("Tipo", pnTipo);
		pnCor=new PnCor();
		tabbInit.addTab("Cor", pnCor);
		pnCaracteristica=new PnCaracteristica();
		tabbInit.addTab("Caracteristica", pnCaracteristica);
		tabbInit.setPreferredSize(new Dimension(1010,600));
		
		pnTabb.add(tabbInit);
		
		pnInit.add(pnButtons);
        pnInit.add(pnTabb);  
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().add(pnInit);
		pack();

		setVisible(true);
		corDAO= new CorDAO();
		pnCor.AtualizaTabela(corDAO.getAll());
		
		tipoDAO= new TipoDAO();
		pnTipo.AtualizaTabela(tipoDAO.getAll());
		
		modeloDAO= new ModeloDAO();
		pnModelo.AtualizaTabela(modeloDAO.getAll());
		
		madeiraDAO= new MadeiraDAO();
		pnMadeira.AtualizaTabela(madeiraDAO.getAll());
		
		caracteristicaDAO= new CaracteristicaDAO();
		pnCaracteristica.AtualizaTabela(caracteristicaDAO.getAll());
		
		fabricanteDAO= new FabricanteDAO();
		pnFabricante.AtualizaTabela(fabricanteDAO.getAll());
		
		instrumentoDAO= new InstrumentoDAO();
		pnInstrumento.AtualizaComboTipo(tipoDAO.getAllComboTexto());
		pnInstrumento.AtualizaComboModelo(modeloDAO.getAllComboTexto());
		pnInstrumento.AtualizaComboFabricante(fabricanteDAO.getAllComboTexto());
		
		pnInstrumento.AtualizaTabela(instrumentoDAO.getAll());
		
		btnNovo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (tabbInit.getSelectedIndex()) {
				case 0:
					pnInstrumento.LimparCampos();
					break;
				case 1:
					
					break;
				case 2:
					pnFabricante.LimparCampos();
					break;
				case 3:
					pnModelo.LimparCampos();
					break;
				case 4:
					pnMadeira.LimparCampos();
					break;
				case 5:
					pnTipo.LimparCampos();
					break;
				case 6:					
					pnCor.LimparCampos();
					break;
				case 7:
					pnCaracteristica.LimparCampos();
					break;
				}
				
			}
		});
		
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				switch (tabbInit.getSelectedIndex()) {
				case 0:
					if(pnInstrumento.ValidarCampos()){
						
						instrumentoDAO.salvar(pnInstrumento.getObjetoTela());
						pnInstrumento.LimparCampos();
						pnInstrumento.AtualizaTabela(instrumentoDAO.getAll());
					}
					else{
						JOptionPane.showMessageDialog(null,"Informe os campos obrigatórios");
					}
					break;
				case 1:
					
					break;
				case 2:
					if(pnFabricante.ValidarCampos()){
						
						fabricanteDAO.salvar(pnFabricante.getObjetoTela());
						pnFabricante.LimparCampos();
						pnFabricante.AtualizaTabela(fabricanteDAO.getAll());
						pnInstrumento.AtualizaComboFabricante(fabricanteDAO.getAllComboTexto());
					}
					else{
						JOptionPane.showMessageDialog(null,"Informe os campos obrigatórios");
					}
					break;
				case 3:
					if(pnModelo.ValidarCampos()){
						
						modeloDAO.salvar(pnModelo.getObjetoTela());
						pnModelo.LimparCampos();
						pnModelo.AtualizaTabela(modeloDAO.getAll());
						pnInstrumento.AtualizaComboModelo(modeloDAO.getAllComboTexto());
					}
					else{
						JOptionPane.showMessageDialog(null,"Informe os campos obrigatórios");
					}
					break;
				case 4:
					if(pnMadeira.ValidarCampos()){
						
						madeiraDAO.salvar(pnMadeira.getObjetoTela());
						pnMadeira.LimparCampos();
						pnMadeira.AtualizaTabela(madeiraDAO.getAll());
						pnInstrumento.AtualizaComboCaracteristica();
					}
					else{
						JOptionPane.showMessageDialog(null,"Informe os campos obrigatórios");
					}
					break;
				case 5:
					if(pnTipo.ValidarCampos()){
						
						tipoDAO.salvar(pnTipo.getObjetoTela());
						pnTipo.LimparCampos();
						pnTipo.AtualizaTabela(tipoDAO.getAll());
						pnInstrumento.AtualizaComboTipo(tipoDAO.getAllComboTexto());
					}
					else{
						JOptionPane.showMessageDialog(null,"Informe os campos obrigatórios");
					}
					break;
				case 6:
					
					if(pnCor.ValidarCampos()){
						
						corDAO.salvar(pnCor.getObjetoTela());
						pnCor.LimparCampos();
						pnCor.AtualizaTabela(corDAO.getAll());
						pnInstrumento.AtualizaComboCaracteristica();
					}
					else{
						JOptionPane.showMessageDialog(null,"Informe os campos obrigatórios");
					}
					break;
				case 7:
					if(pnCaracteristica.ValidarCampos()){
						
						caracteristicaDAO.salvar(pnCaracteristica.getObjetoTela());
						pnCaracteristica.LimparCampos();
						pnCaracteristica.AtualizaTabela(caracteristicaDAO.getAll());
						pnInstrumento.AtualizaComboCaracteristica();
					}
					else{
						JOptionPane.showMessageDialog(null,"Informe os campos obrigatórios");
					}
					break;
				}
			}
		});
		
		btnRemover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (tabbInit.getSelectedIndex()) {
				case 0:
					if(pnInstrumento.ValidarCampos()){
						if(instrumentoDAO.remover(pnInstrumento.getObjetoTela())){
							pnInstrumento.LimparCampos();
							pnInstrumento.AtualizaTabela(instrumentoDAO.getAll());
							pnInstrumento.AtualizaComboCaracteristica();
						}
						else{
							JOptionPane.showMessageDialog(null,"Não foi possível remover o cadastro.\nCadastro já associado a outro!");
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"Selecione um item para remover!");
					}
					break;
				case 1:
					
					break;
				case 2:
					if(pnFabricante.ValidarCampos()){
						if(fabricanteDAO.remover(pnFabricante.getObjetoTela().getCnpj())){
							pnFabricante.LimparCampos();
							pnFabricante.AtualizaTabela(fabricanteDAO.getAll());
							pnInstrumento.AtualizaComboFabricante(fabricanteDAO.getAllComboTexto());
						}
						else{
							JOptionPane.showMessageDialog(null,"Não foi possível remover o cadastro.\nCadastro já associado a outro!");
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"Selecione um item para remover!");
					}
					break;
				case 3:
					if(pnModelo.ValidarCampos()){
						if(modeloDAO.remover(pnModelo.getObjetoTela().getId())){
							pnModelo.LimparCampos();
							pnModelo.AtualizaTabela(modeloDAO.getAll());
							pnInstrumento.AtualizaComboModelo(modeloDAO.getAllComboTexto());
						}
						else{
							JOptionPane.showMessageDialog(null,"Não foi possível remover o cadastro.\nCadastro já associado a outro!");
						}	
					}
					else{
						JOptionPane.showMessageDialog(null,"Selecione um item para remover!");
					}
					break;
				case 4:
					if(pnMadeira.ValidarCampos()){
						if(madeiraDAO.remover(pnMadeira.getObjetoTela().getId())){
							pnMadeira.LimparCampos();
							pnMadeira.AtualizaTabela(madeiraDAO.getAll());
							pnInstrumento.AtualizaComboCaracteristica();
						}
						else{
							JOptionPane.showMessageDialog(null,"Não foi possível remover o cadastro.\nCadastro já associado a outro!");
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"Selecione um item para remover!");
					}
					break;
				case 5:
					if(pnTipo.ValidarCampos()){
						if(tipoDAO.remover(pnTipo.getObjetoTela().getId())){
							pnTipo.LimparCampos();
							pnTipo.AtualizaTabela(tipoDAO.getAll());
							pnInstrumento.AtualizaComboTipo(tipoDAO.getAllComboTexto());
						}
						else{
							JOptionPane.showMessageDialog(null,"Não foi possível remover o cadastro.\nCadastro já associado a outro!");
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"Selecione um item para remover!");
					}
					break;
				case 6:
					
					if(pnCor.ValidarCampos()){
						if(corDAO.remover(pnCor.getObjetoTela().getId())){
							pnCor.LimparCampos();
							pnCor.AtualizaTabela(corDAO.getAll());
							pnInstrumento.AtualizaComboCaracteristica();
						}
						else{
							JOptionPane.showMessageDialog(null,"Não foi possível remover o cadastro.\nCadastro já associado a outro!");
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"Selecione um item para remover!");
					}
					break;
				case 7:
					if(pnCaracteristica.ValidarCampos()){
						if(caracteristicaDAO.remover(pnCaracteristica.getObjetoTela().getId())){
							pnCaracteristica.LimparCampos();
							pnCaracteristica.AtualizaTabela(caracteristicaDAO.getAll());
							pnInstrumento.AtualizaComboCaracteristica();
						}
						else{
							JOptionPane.showMessageDialog(null,"Não foi possível remover o cadastro.\nCadastro já associado a outro!");
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"Selecione um item para remover!");
					}
					break;
				}
			}
		});
	}
}
