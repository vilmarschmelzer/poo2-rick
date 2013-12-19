package component;

import java.util.ArrayList;

import javax.swing.table.TableModel;

import models.Caracteristica;
import models.Inst_Caract;
import models.Instrumento;
import decorator.CaractDecorator;
import decorator.InstAbsDecorator;
import decorator.InstDecorator;

public class Calcular {
	
	public static double CustoCaracteristicas(Instrumento inst,TableModel tableModel){
		
		ArrayList<Inst_Caract> listCaract=new ArrayList();
		
		int rows = tableModel.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
			try {
				listCaract.add(new Inst_Caract(new Caracteristica(
						Integer.parseInt(tableModel.getValueAt(i, 0).toString()),
						tableModel.getValueAt(i, 1).toString(),
						Double.parseDouble(tableModel.getValueAt(i, 3).toString())),
						Integer.parseInt(tableModel.getValueAt(i, 2).toString())));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		InstAbsDecorator decorator=new InstDecorator(inst);
		
		for (Inst_Caract inst_Caract : listCaract) {
			
			inst_Caract.getCaracteristica().setValor(inst_Caract.getCaracteristica().getValor()*inst_Caract.getQtd());
			
			decorator=new CaractDecorator(decorator,inst_Caract.getCaracteristica());
			System.out.println(decorator.custo());
		}
		
		return decorator.custo();
	}
}
