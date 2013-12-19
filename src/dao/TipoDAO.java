package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Caracteristica;
import models.Cor;
import models.Tipo;

public class TipoDAO extends AbstractDAO<Tipo> {
	
	public TipoDAO(){
		init();
	}
	
	@Override
	public Tipo salvar(Tipo obj)  {
		
		try {
			
			PreparedStatement pre;
			ResultSet rs;
			
			if(obj.getId()==0)
				pre = conn.prepareStatement("insert into tipo (nome) values (?)");
			else{
				pre = conn.prepareStatement("update tipo set nome=? where id=?");
				pre.setInt(2, obj.getId());
			}
			pre.setString(1, obj.getNome());
			
			pre.execute();
			
			if(obj.getId()==0){
				pre = conn.prepareStatement("select max(id) as id from tipo");
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
	
	@Override
	public boolean remover(Object id) {
		PreparedStatement pre;
		
		try {
			pre=conn.prepareStatement("delete from tipo where id = ?");
			pre.setInt(1, (int)id);
			
			pre.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String sql="select id,nome from tipo";
	
	@Override
	public ArrayList<Tipo> getAll() {
		
		ArrayList<Tipo> listTipo=new ArrayList<Tipo>();
		
		try {
			PreparedStatement pre=conn.prepareStatement(sql);
			ResultSet rs=pre.executeQuery();
			
			while(rs.next()){
				listTipo.add(new Tipo(rs.getInt("id"),rs.getString("nome")));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listTipo;
	}
	
	@Override
	public ArrayList<String> getAllComboTexto() {
		
		ArrayList<String> items=new ArrayList();
		items.add("");
		for (Tipo tipo : getAll())
			items.add(tipo.getTextoCombo());
		
		return items;
	}
	
	@Override
	public Tipo get(Object id) {
		
		try {
			PreparedStatement pre=conn.prepareStatement(sql+" where id=?");
			pre.setInt(1, (int)id);
			
			ResultSet rs=pre.executeQuery();
			
			if(rs.next()){
				return new Tipo(rs.getInt("id"),rs.getString("nome"));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
