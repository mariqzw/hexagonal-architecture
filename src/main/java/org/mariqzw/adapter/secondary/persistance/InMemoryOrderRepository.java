package org.mariqzw.adapter.secondary.persistance;

import org.mariqzw.domain.model.Order;
import org.mariqzw.domain.port.secondary.OrderRepository;

import java.util.*;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> store = new HashMap<>();

    @Override
    public void save(Order order) {
        store.put(order.getId(), order);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }
}
