package E2EEcommerceAPI;

public class OrderDetails {
    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getProductOrderedId() {
        return ProductOrderedId;
    }

    public void setProductOrderedId(String productOrderedId) {
        ProductOrderedId = productOrderedId;
    }

    private String Country;
    private String ProductOrderedId;


}
