package web.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import domain.Sale;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("querysales")
@ViewScoped
public class QuerySalesBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private IndexBean index;

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
		if (sales.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(index.getString("QuerySalesGUI.NoProducts")));
		}
	}
}
