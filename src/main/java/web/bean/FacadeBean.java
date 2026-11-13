package web.bean;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;

public class FacadeBean {
	private static BLFacade facadeInterface;
	private static FacadeBean singleton = new FacadeBean();
	
	private FacadeBean() {
			DataAccess da = new DataAccess();
			facadeInterface = new BLFacadeImplementation(da);
	}
	
	public static BLFacade getBussinesLogic() {
		return facadeInterface;
	}
}
