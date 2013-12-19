package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Caracteristica;

public class CaracteristicaDAO extends AbstractDAO<Caracteristica> {
	
	public CaracteristicaDAO(){
		init();
	}
	
	@Override
	public Caracteristica salvar(Caracteristica obj)  {
		
		try {
			
			PreparedStatement pre;
			ResultSet rs;
			
			if(obj.getId()==0)
				pre = conn.prepareStatement("insert into caracteristica (nome,valor) values (?,?)");
			else{
				pre = conn.prepareStatement("update caracteristica set nome=?,valor=? where id=?");
				pre.setInt(3, obj.getId());
			}
			pre.setString(1, obj.getNome());
			pre.setDouble(2, obj.getValor());
			
			pre.execute();
			
			if(obj.getId()==0){
				pre = conn.prepareStatement("select max(id) as id from caracteristica");
				rs=pre.executeQuery();
				rs.next();
				obj.setId(rs.getInt("id"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;
	}
	
	public boolean validarRemover(int id) throws SQLException{
		PreparedStatement pre=conn.prepareStatement("select id_caracteristica from  inst_caract where id_caracteristica=?");
		pre.setInt(1, id);
		
		ResultSet rs=pre.executeQuery();
		
		if(rs.next())
			return false;
		return true;
	}
	
	@Override
	public boolean remover(Object id) {
		PreparedStatement pre;
		
		try {
			
			if(!validarRemover((int)id))
				return false;
			
			
			pre=conn.prepareStatement("delete from caracteristica where id = ?");
			pre.setInt(1, (int)id);
			
			pre.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String sql="select id,nome,valor from caracteristica "
			+ "where id not in (select id_caracteristica from instrumento) and "
			+ "id not in (select id_caracteristica from cor) and "
			+ "id not in (select id_caracteristica from madeira)";
	
	@Override
	public ArrayList<Caracteristica> getAll() {
		
		ArrayList<Caracteristica> listCaract=new ArrayList<Caracteristica>();
		
		try {
			PreparedStatement pre=conn.prepareStatement(sql);
			ResultSet rs=pre.executeQuery();
			
			while(rs.next()){
				
				listCaract.add(new Caracteristica(rs.getInt("id"),rs.getString("nome"),rs.getDouble("valor")));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listCaract;
	}
	
	@Override
	public ArrayList<String> getAllComboTexto() {
		
		ArrayList<String> items=new ArrayList();
		items.add("");
		for (Caracteristica carac : getAll())
			items.add(carac.getTextoCombo());
		
		return items;
	}
	
	@Override
	public Caracteristica get(Object id) {
		
		PreparedStatement pre;
		try {
			pre = conn.prepareStatement(sql+" where id = ?");
			pre.setInt(1,(int)id);
			
			ResultSet rs=pre.executeQuery();
			
			if(rs.next())
				return new Caracteristica(rs.getInt("id"),rs.getString("nome"),rs.getDouble("valor"));
			
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
