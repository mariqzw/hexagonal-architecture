package org.mariqzw.domain;

import org.mariqzw.adapter.primary.console.ConsoleOrderManager;
import org.mariqzw.adapter.secondary.notification.SupplierNotificationService;
import org.mariqzw.adapter.secondary.persistance.InMemoryOrderRepository;
import org.mariqzw.domain.port.primary.SupplierOrderUseCase;
import org.mariqzw.domain.port.secondary.NotificationService;
import org.mariqzw.domain.port.secondary.OrderRepository;
import org.mariqzw.domain.service.OrderService;

public class Application {
    public static void main(String[] args) {
        OrderRepository repository = new InMemoryOrderRepository();
        NotificationService notifier = new SupplierNotificationService();
        SupplierOrderUseCase service = new OrderService(repository, notifier);

        ConsoleOrderManager ui = new ConsoleOrderManager(service);

        System.out.println("Запуск системы управления заказами");
        ui.run();
    }
}
