package org.mariqzw.domain.port.secondary;

import org.mariqzw.domain.model.Order;

public interface NotificationService {
    void notifyOrder(Order order);
    void notifyReturn(Order order);
}
