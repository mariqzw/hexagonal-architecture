package org.mariqzw.domain.service;

import org.mariqzw.domain.model.Order;
import org.mariqzw.domain.model.OrderStatus;
import org.mariqzw.domain.model.ProductDemandForecast;
import org.mariqzw.domain.port.primary.SupplierOrderUseCase;
import org.mariqzw.domain.port.secondary.NotificationService;
import org.mariqzw.domain.port.secondary.OrderRepository;

import java.util.UUID;

public class OrderService implements SupplierOrderUseCase {
    private final OrderRepository repository;
    private final NotificationService notificationService;

    public OrderService(OrderRepository repository,
                        NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    @Override
    public Order createOrder(ProductDemandForecast forecast) {
        Order order = new Order(UUID.randomUUID(), forecast.getItems());
        repository.save(order);
        return order;
    }

    @Override
    public void sendOrder(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot send order in status: " + order.getStatus());
        }
        notificationService.notifyOrder(order);
        order.setStatus(OrderStatus.SENT);
        repository.save(order);
    }

    @Override
    public void receiveConfirmation(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        if (order.getStatus() != OrderStatus.SENT) {
            throw new IllegalStateException("Cannot confirm order in status: " + order.getStatus());
        }
        order.setStatus(OrderStatus.CONFIRMED);
        repository.save(order);
    }

    @Override
    public OrderStatus trackStatus(UUID orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId))
                .getStatus();
    }

    @Override
    public void acceptDelivery(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        if (order.getStatus() != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Cannot accept delivery in status: " + order.getStatus());
        }
        order.setStatus(OrderStatus.DELIVERED);
        order.setStatus(OrderStatus.QUALITY_CHECKED);
        repository.save(order);
    }

    @Override
    public void processReturn(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        if (order.getStatus() != OrderStatus.QUALITY_CHECKED) {
            throw new IllegalStateException("Cannot process return in status: " + order.getStatus());
        }
        order.setStatus(OrderStatus.RETURNED);
        notificationService.notifyReturn(order);
        repository.save(order);
    }
}
