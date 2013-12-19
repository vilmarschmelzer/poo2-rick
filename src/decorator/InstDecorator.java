package decorator;

import models.Instrumento;

public class InstDecorator extends InstAbsDecorator {

	private Instrumento inst;
	
	public InstDecorator(Instrumento inst){
		this.inst=inst;
	}
	
	@Override
	public double custo() {
		// TODO Auto-generated method stub
		return inst.getValor();
	}
	
}
