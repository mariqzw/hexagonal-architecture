package org.mariqzw.domain.port.secondary;

import org.mariqzw.domain.model.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(UUID id);
}
