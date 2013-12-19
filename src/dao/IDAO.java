package dao;

import java.util.ArrayList;

public interface IDAO<T> {
	
	T salvar(T obj);
	boolean remover(Object id);
	T get(Object id);
	ArrayList<T> getAll();
	ArrayList<String> getAllComboTexto();
	void init();
}
