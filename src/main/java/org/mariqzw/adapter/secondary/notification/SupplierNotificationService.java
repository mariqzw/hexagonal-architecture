package org.mariqzw.adapter.secondary.notification;

import org.mariqzw.domain.model.Order;
import org.mariqzw.domain.port.secondary.NotificationService;

public class SupplierNotificationService implements NotificationService {
    @Override
    public void notifyOrder(Order order) {
        System.out.println(">>> Уведомление поставщику: новый заказ "
                + order.getId() + " items=" + order.getItems());
    }

    @Override
    public void notifyReturn(Order order) {
        System.out.println(">>> Уведомление поставщику: возврат по заказу "
                + order.getId());
    }
}
