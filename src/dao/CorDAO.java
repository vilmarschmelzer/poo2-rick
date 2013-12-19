package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Caracteristica;
import models.Cor;

public class CorDAO extends AbstractDAO<Cor> {
	
	public CorDAO(){
		init();
	}
	
	@Override
	public Cor salvar(Cor obj)  {
		PreparedStatement pre;
		ResultSet rs;
		
		try {
			
			CaracteristicaDAO caractDAO=new CaracteristicaDAO();
			
			obj.setId(caractDAO.salvar(obj).getId());
			
			pre = conn.prepareStatement("select id_caracteristica from cor where id_caracteristica = ?");
			pre.setInt(1, obj.getId());
			
			rs=pre.executeQuery();
			
			if (!rs.next()){
				pre = conn.prepareStatement("insert into cor (id_caracteristica) values (?)");
				pre.setInt(1, obj.getId());
				pre.execute();	
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;
	}
	
	@Override
	public boolean remover(Object id) {
		PreparedStatement pre;
		
		CaracteristicaDAO caractDAO=new CaracteristicaDAO();
		
		try {
			
			if(!caractDAO.validarRemover((int)id))
				return false;
			
			pre=conn.prepareStatement("delete from cor where id_caracteristica = ?");
			pre.setInt(1, (int)id);
			
			pre.execute();
			if(caractDAO.remover(id));
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String sql="select a.id as id,a.nome as nome,a.valor as valor from caracteristica a "
			+ "inner join cor b on (a.id=b.id_caracteristica)";
	
	@Override
	public ArrayList<Cor> getAll() {
		
		try {
			PreparedStatement pre=conn.prepareStatement(sql);
			
			ResultSet rs=pre.executeQuery();
			ArrayList<Cor> listCor=new ArrayList<Cor>();
			
			while(rs.next())
				listCor.add(new Cor(rs.getInt("id"),rs.getString("nome"),rs.getDouble("valor")));
			
			return listCor;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public ArrayList<String> getAllComboTexto() {
		
		ArrayList<String> items=new ArrayList();
		items.add("");
		for (Cor cor : getAll())
			items.add(cor.getTextoCombo());
		
		return items;
	}
	
	@Override
	public Cor get(Object id) {
		try {
			PreparedStatement pre=conn.prepareStatement(sql+" where a.id = ?");
			pre.setInt(1, (int)id);
			
			ResultSet rs=pre.executeQuery();
			
			if(rs.next())
				return new Cor(rs.getInt("id"),rs.getString("nome"),rs.getDouble("valor"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
