package web.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import domain.Sale;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("querysales")
@ViewScoped
public class QuerySalesBean implements Serializable {
	
	private String desc;
	private List<Sale> sales;
	
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<Sale> getSales() {
		return sales;
	}
	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
	
	
	public void searchSales() {
		BLFacade bl = FacadeBean.getBussinesLogic();
		if (desc == null) {
			desc = "";
		}
		sales = bl.getPublishedSales(desc, new Date());
	}
}
