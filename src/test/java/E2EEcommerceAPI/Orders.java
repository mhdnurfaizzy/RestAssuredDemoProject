package E2EEcommerceAPI;

import java.util.List;

public class Orders {
    private List<OrderDetails> Orders;

    public List<OrderDetails> getOrders() {
        return Orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        Orders = orders;
    }
}
