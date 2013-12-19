package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.Oneway;

import models.Cor;
import models.Fabricante;
import models.Modelo;

public class FabricanteDAO extends AbstractDAO<Fabricante> {
	
	public FabricanteDAO(){
		init();
	}
	@Override
	public Fabricante salvar(Fabricante obj)  {
		
		try {
			
			PreparedStatement pre;
			ResultSet rs;
			
			pre= conn.prepareStatement("select cnpj from fabricante where cnpj = ?");
			pre.setString(1, obj.getCnpj());
			
			rs=pre.executeQuery();
			
			if(!rs.next())
				pre = conn.prepareStatement("insert into fabricante (nome,nome_fantasia,cnpj) values (?,?,?)");
			else{
				pre = conn.prepareStatement("update fabricante set nome=?,nome_fantasia=? where cnpj=?");
				
			}
			pre.setString(1, obj.getNome());
			pre.setString(2, obj.getNome_fantasia());
			pre.setString(3, obj.getCnpj());
			
			
			pre.execute();
			
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
			pre=conn.prepareStatement("delete from fabricante where cnpj = ?");
			pre.setString(1, (String)id);
			
			pre.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String sql="select cnpj,nome,nome_fantasia from fabricante";
	
	@Override
	public ArrayList<Fabricante> getAll() {
		
		try {
			PreparedStatement pre=conn.prepareStatement(sql);
			ResultSet rs=pre.executeQuery();
			ArrayList<Fabricante> listFabricante=new ArrayList<Fabricante>();
			
			while(rs.next())
				listFabricante.add(new Fabricante(
						rs.getString("cnpj")
						,rs.getString("nome"),
						rs.getString("nome_fantasia"))
				);
			return listFabricante;
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
		for (Fabricante fabricante : getAll())
			items.add(fabricante.getTextoCombo());
		
		return items;
	}
	
	@Override
	public Fabricante get(Object id) {
		
		try {
			PreparedStatement pre=conn.prepareStatement(sql+" where cnpj=?");
			pre.setString(1,(String)id);
			
			ResultSet rs=pre.executeQuery();
			
			
			if(rs.next())
				return new Fabricante(
						rs.getString("cnpj")
						,rs.getString("nome"),
						rs.getString("nome_fantasia"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
