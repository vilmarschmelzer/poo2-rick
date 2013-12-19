package dao;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import models.Caracteristica;
import models.Cor;
import models.Fabricante;
import models.Inst_Caract;
import models.Instrumento;
import models.Modelo;
import models.Tipo;

public class InstrumentoDAO extends AbstractDAO<Instrumento> {
	
	public InstrumentoDAO(){
		init();
	}
	
	private int getId_Caracteristica(int id_inst) throws SQLException{
		if(id_inst!=0){
			PreparedStatement pre=conn.prepareStatement("Select id_caracteristica from instrumento where id=?");
			pre.setInt(1, id_inst);
			
			ResultSet rs=pre.executeQuery();
			if(rs.next())
				return rs.getInt("id_caracteristica");
		}
		return 0;
	}
	
	@Override
	public Instrumento salvar(Instrumento obj)  {
		PreparedStatement pre;
		ResultSet rs;
		
		try {
			
			
			obj.setId(getId_Caracteristica(obj.getId_inst()));
			
			CaracteristicaDAO caractDAO=new CaracteristicaDAO();
			obj.setId(caractDAO.salvar(obj).getId());
			
			if(obj.getId_inst()==0){
				
				pre=conn.prepareStatement("insert into instrumento (id_caracteristica,id_tipo,id_modelo,cnpj_fabricante) values (?,?,?,?)");
			}
			else{
				pre=conn.prepareStatement("update instrumento set id_caracteristica=?,id_tipo=?,id_modelo=?,cnpj_fabricante=? where id=?");
				pre.setInt(5, obj.getId_inst());
				
				
			}
			pre.setInt(1, obj.getId());
			pre.setInt(2, obj.getTipo().getId());
			pre.setInt(3, obj.getModelo().getId());
			pre.setString(4, obj.getFabricante().getCnpj());
			
			pre.execute();
			
			if(obj.getId_inst()==0){
				pre=conn.prepareStatement("Select max(id) as id from instrumento");
				rs=pre.executeQuery();
				if(rs.next())
					obj.setId_inst(rs.getInt("id"));
			}
			
			salvarCaracteristicas(obj.getId_inst(),obj.getItems());
			
			return obj;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	
	private void salvarCaracteristicas(int id_inst,ArrayList<Inst_Caract> items) throws SQLException{
		
		PreparedStatement pre;
		ResultSet rs;
		
		String listId_Caract="";
		
		for (Inst_Caract caract : items) {
			if(listId_Caract.equals(""))
				listId_Caract=caract.getCaracteristica().getId()+"";
			
			else
				listId_Caract+=","+caract.getCaracteristica().getId();
			
			pre=conn.prepareStatement("select id from inst_caract where id_instrumento=? and id_caracteristica=?");
			pre.setInt(1, id_inst);
			pre.setInt(2, caract.getCaracteristica().getId());
			rs = pre.executeQuery();
			
			if(rs.next()){
				pre=conn.prepareStatement("update inst_caract set id_caracteristica=?,qtd=? where id=?");
				pre.setInt(3, rs.getInt("id"));
			}
			else{
				pre=conn.prepareStatement("insert into inst_caract (id_caracteristica,qtd,id_instrumento) values (?,?,?)");
				pre.setInt(3, id_inst);
			}
			
			pre.setInt(1, caract.getCaracteristica().getId());
			pre.setInt(2, caract.getQtd());
			pre.execute();
		}
		
		String sql="delete from inst_caract where id_instrumento=?";
		if(!listId_Caract.equals(""))
			sql+=" and id_caracteristica not in ("+listId_Caract+")";
		
		pre=conn.prepareStatement(sql);
		pre.setInt(1, id_inst);
		pre.execute();
	}
	
	@Override
	public boolean remover(Object id) {
		PreparedStatement pre;
		
		CaracteristicaDAO caractDAO=new CaracteristicaDAO();
		caractDAO.init();
		
		try {
			
			((Instrumento)id).setId(getId_Caracteristica(((Instrumento)id).getId_inst()));
			
			if(!caractDAO.validarRemover(((Instrumento)id).getId()))
				return false;
			
			pre=conn.prepareStatement("delete from inst_caract where id_instrumento=?");
			pre.setInt(1, ((Instrumento)id).getId_inst());
			pre.execute();
			
			pre=conn.prepareStatement("delete from instrumento where id = ?");
			pre.setInt(1, ((Instrumento)id).getId_inst());
			
			pre.execute();
			caractDAO.remover(((Instrumento)id).getId());
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String sql="select distinct a.id as id_inst,b.id as id,b.nome as nome,"
			+ "c.id as id_tipo,c.nome as nome_tipo, "
			+ "d.id as id_modelo,d.nome as nome_modelo, "
			+ "e.cnpj as cnpj_fabricante,e.nome as nome_fabricante, "
			+ "e.nome_fantasia as nome_fantasia_fabricante,b.valor as valor from instrumento a "
			+ "inner join caracteristica b on (a.id_caracteristica=b.id) "
			+ "inner join tipo c on (a.id_tipo=c.id) "
			+ "inner join modelo d on (a.id_modelo=d.id) "
			+ "inner join fabricante e on (a.cnpj_fabricante=e.cnpj)";
	
	@Override
	public ArrayList<Instrumento> getAll() {
		
		try {
			PreparedStatement pre=conn.prepareStatement(sql);
			
			ResultSet rs=pre.executeQuery();
			ArrayList<Instrumento> listInstrumento=new ArrayList<Instrumento>();
			
			while(rs.next()){
				listInstrumento.add(new Instrumento(rs.getInt("id_inst"),rs.getInt("id"),rs.getString("nome"),
						new Tipo(rs.getInt("id_tipo"),rs.getString("nome_tipo")),
						new Modelo(rs.getInt("id_modelo"),rs.getString("nome_modelo")),
						new Fabricante(rs.getString("cnpj_fabricante"),rs.getString("nome_fabricante"),rs.getString("nome_fantasia_fabricante")),
						rs.getDouble("valor"))
				);
			}
			return listInstrumento;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public ArrayList<String> getAllComboTexto() {
		return getAllComboText(0);
	}
	
	public ArrayList<String> getAllComboText(int naoId_inst) {
		try {
			
			int id_caracteristica=getId_Caracteristica(naoId_inst);
			System.out.println(id_caracteristica);
			
			ArrayList<String> items=new ArrayList();
			items.add("");
			for (Instrumento inst : getAll()){
				if(inst.getId()!=id_caracteristica)
				items.add(inst.getTextoCombo());
			}
			
			return items;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public ArrayList<Inst_Caract> getCaracteristicas(Instrumento inst){
		try {
			PreparedStatement pre;
			ResultSet rs;
			ArrayList<Inst_Caract> listCaract=new ArrayList<Inst_Caract>();
			pre = conn.prepareStatement("select a.id as id,a.id_instrumento as id_instrumento,"
					+ "a.id_caracteristica as id_caracteristica,b.nome as nome_carct,a.qtd as qtd,b.valor as valor "
					+ "from inst_caract a "
					+ "inner join caracteristica b on (a.id_caracteristica=b.id) "
					+ "where a.id_instrumento=?");
			
			System.out.println(inst.getId_inst());
			
			pre.setInt(1, inst.getId_inst());
			rs=pre.executeQuery();
			
			while(rs.next()){
				listCaract.add(new Inst_Caract(inst,
						new Caracteristica(rs.getInt("id_caracteristica"),
								rs.getString("nome_carct"),rs.getDouble("valor")),
								rs.getInt("qtd")
								));
				
			}
			return listCaract;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	@Override
	public Instrumento get(Object id) {
		try {
			PreparedStatement pre=conn.prepareStatement(sql+ " where a.id=?");
			pre.setInt(1, (int)id);
			ResultSet rs=pre.executeQuery();
			Instrumento inst=null;
			if(rs.next()){
				inst= new Instrumento(rs.getInt("id_inst"),rs.getInt("id"),rs.getString("nome"),
						new Tipo(rs.getInt("id_tipo"),rs.getString("nome_tipo")),
						new Modelo(rs.getInt("id_modelo"),rs.getString("nome_modelo")),
						new Fabricante(rs.getString("cnpj_fabricante"),rs.getString("nome"),rs.getString("nome_fantasia")),
						rs.getDouble("valor")
				);
			}
			
			if(inst==null)
				return null;
			
			inst.setItems(getCaracteristicas(inst));
			
			return inst;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<Instrumento> pesquisar(Map<String, String> filtros){
		
		try {
			
			
			if(filtros==null|| filtros.size()==0)
				return getAll();
			
			String sqlFiltro=sql+"left join inst_caract f on (a.id=f.id_instrumento) where ";
			String and="";
			String key="Tipo";
			
			if(filtros.containsKey(key)){
				sqlFiltro+="a.id_"+key.toLowerCase()+" = "+filtros.get(key).split(" - ")[0];
				and=" and ";
			}
			key="Modelo";
			
			if(filtros.containsKey(key)){
				sqlFiltro+=and+"a.id_"+key.toLowerCase()+" = "+filtros.get(key).split(" - ")[0];
				and=" and ";
			}
			key="Fabricante";
			if(filtros.containsKey(key)){
				sqlFiltro+=and+"a.cnpj_"+key.toLowerCase()+" = '"+filtros.get(key).split(" - ")[0]+"'";
				and=" and ";
			}
			
			key="Nome";
			if(filtros.containsKey(key)){
				sqlFiltro+=and+"lower(b.nome) like '%"+(filtros.get(key).split(" - ")[0]).toLowerCase()+"%'";
				and=" and ";
			}
			
			key="Caracteristica";
			if(filtros.containsKey(key)){
				
				if(filtros.get(key).contains(",")){
					for (String condicao : filtros.get(key).split(",")) {
						sqlFiltro+=and+"f.id_caracteristica = "+condicao;
						and=" and ";
					}
					
				}
				else
					sqlFiltro+=and+"f.id_caracteristica = "+filtros.get(key);
			}
			
			PreparedStatement pre=conn.prepareStatement(sqlFiltro);
			ResultSet rs=pre.executeQuery();
			
			ArrayList<Instrumento> list=new ArrayList();
			while(rs.next()){
				list.add(new Instrumento(rs.getInt("id_inst"),rs.getInt("id"),rs.getString("nome"),
						new Tipo(rs.getInt("id_tipo"),rs.getString("nome_tipo")),
						new Modelo(rs.getInt("id_modelo"),rs.getString("nome_modelo")),
						new Fabricante(rs.getString("cnpj_fabricante"),rs.getString("nome_fabricante"),rs.getString("nome_fantasia_fabricante")),
						rs.getDouble("valor"))
				);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
}
