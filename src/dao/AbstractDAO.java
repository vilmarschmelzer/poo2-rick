package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import util.ConnFactory;


public abstract class AbstractDAO<T> implements IDAO<T> {
	
	public Connection conn;
	/*private Class<T> typeClass;   
	 
    public AbstractDAO(Class<T> tClass) {
        this.typeClass = tClass;
    }*/
	
	@Override
	public void init(){
		try {
			conn= ConnFactory.getConnetion();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public T salvar(T obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<T> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean remover(Object id) {
		
		return true;
	}

} 
