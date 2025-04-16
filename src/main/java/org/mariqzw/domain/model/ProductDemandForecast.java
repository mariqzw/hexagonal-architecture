package org.mariqzw.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDemandForecast {
    private final List<OrderItem> items;

    public ProductDemandForecast(List<OrderItem> items) {
        this.items = new ArrayList<>(items);
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
