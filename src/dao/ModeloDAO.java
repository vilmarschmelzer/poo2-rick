package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Caracteristica;
import models.Cor;
import models.Modelo;
import models.Tipo;

public class ModeloDAO extends AbstractDAO<Modelo> {
	
	public ModeloDAO(){
		init();
	}
	
	@Override
	public Modelo salvar(Modelo obj)  {
		
		try {
			
			PreparedStatement pre;
			ResultSet rs;
			
			if(obj.getId()==0)
				pre = conn.prepareStatement("insert into modelo (nome) values (?)");
			else{
				pre = conn.prepareStatement("update modelo set nome=? where id=?");
				pre.setInt(2, obj.getId());
			}
			pre.setString(1, obj.getNome());
			
			pre.execute();
			
			if(obj.getId()==0){
				pre = conn.prepareStatement("select max(id) as id from modelo");
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
			pre=conn.prepareStatement("delete from modelo where id = ?");
			pre.setInt(1, (int)id);
			
			pre.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String sql="select id,nome from modelo";
	
	@Override
	public ArrayList<Modelo> getAll() {
		
		try {
			PreparedStatement pre=conn.prepareStatement(sql);
			ResultSet rs=pre.executeQuery();
			
			ArrayList<Modelo> listModelo=new ArrayList<Modelo>();
			while(rs.next())
				listModelo.add(new Modelo(rs.getInt("id"),rs.getString("nome")));	
			return listModelo;
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
		for (Modelo modelo : getAll())
			items.add(modelo.getTextoCombo());
		
		return items;
	}
	
	@Override
	public Modelo get(Object id) {
		
		try {
			PreparedStatement pre=conn.prepareStatement(sql+" where id = ?");
			pre.setInt(1, (int)id);
			ResultSet rs=pre.executeQuery();
			
			if(rs.next())
				return new Modelo(rs.getInt("id"),rs.getString("nome"));	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
