package org.mariqzw.domain.port.primary;

import org.mariqzw.domain.model.Order;
import org.mariqzw.domain.model.OrderStatus;
import org.mariqzw.domain.model.ProductDemandForecast;

import java.util.List;
import java.util.UUID;


public interface SupplierOrderUseCase {
    Order createOrder(ProductDemandForecast forecast);
    void sendOrder(UUID orderId);
    void receiveConfirmation(UUID orderId);
    OrderStatus trackStatus(UUID orderId);
    void acceptDelivery(UUID orderId);
    void processReturn(UUID orderId);
}
