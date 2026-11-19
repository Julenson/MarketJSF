package web.bean;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.file.UploadedFile;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import businessLogic.BLFacade;
import configuration.UtilDate;
import gui.Utils;

@Named("createSale")
@ViewScoped
public class CreateSaleBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String description;
    private float price;
    private Date publicationDate;
    private String sellerMail;

    private String statusSelected;
    private List<String> statusList = new ArrayList<String>();

    private UploadedFile uploadedFile;

    public CreateSaleBean() {
        publicationDate = new Date();
        try {
            List<String> loadedStatus = Utils.getStatus();
            if (loadedStatus != null && !loadedStatus.isEmpty()) {
                statusList = loadedStatus;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Si Utils.getStatus() devolvió null, lista vacía o lanzó excepción, rellenamos por defecto
        if (statusList == null || statusList.isEmpty()) {
            statusList = new ArrayList<String>();
            statusList.add("Disponible");
            statusList.add("Reservado");
            statusList.add("Vendido");
        }

        if (statusSelected == null) {
            statusSelected = statusList.get(0);
        }
    }

    public String crearProducto() {
        if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: El título y la descripción son obligatorios.", null));
            return null;
        }

        if (price <= 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: El precio debe ser mayor que 0.", null));
            return null;
        }

        try {
            BLFacade facade = FacadeBean.getBussinesLogic();
            int numStatus = statusList.indexOf(statusSelected);

            // Conversión del archivo subido
            java.io.File targetFile = convertUploadedFileToFile(uploadedFile);

            facade.createSale(title, description, numStatus, price, UtilDate.trim(publicationDate), sellerMail, targetFile);

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito: Producto creado correctamente.", null));

            return "ok";

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear: " + e.getMessage(), null));
            return "error";
        }
    }

    public void onDateSelect(SelectEvent<Date> event) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage("Fecha escogida: " + event.getObject()));
    }

    public void listener(AjaxBehaviorEvent event) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage("Estado del producto cambiado a: " + statusSelected));
    }

    private java.io.File convertUploadedFileToFile(UploadedFile file) {
        if (file == null || file.getFileName() == null || file.getFileName().isEmpty()) {
            return null;
        }
        try {
            java.io.File tempFile = java.io.File.createTempFile("upload_", file.getFileName());
            try (java.io.InputStream input = file.getInputStream()) {
                Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            return tempFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getters y Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public Date getPublicationDate() { return publicationDate; }
    public void setPublicationDate(Date publicationDate) { this.publicationDate = publicationDate; }

    public String getSellerMail() { return sellerMail; }
    public void setSellerMail(String sellerMail) { this.sellerMail = sellerMail; }

    public String getStatusSelected() { return statusSelected; }
    public void setStatusSelected(String statusSelected) { this.statusSelected = statusSelected; }

    public List<String> getStatusList() { return statusList; }
    public void setStatusList(List<String> statusList) { this.statusList = statusList; }

    public UploadedFile getUploadedFile() { return uploadedFile; }
    public void setUploadedFile(UploadedFile uploadedFile) { this.uploadedFile = uploadedFile; }
}
