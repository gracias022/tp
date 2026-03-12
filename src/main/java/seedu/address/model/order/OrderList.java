package seedu.address.model.order;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.order.exceptions.OrderNotFoundException;

public class OrderList {
    private final ObservableList<Order> internalList = FXCollections.observableArrayList();
    private final ObservableList<Order> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public void add(Order order) {
        requireNonNull(order);
        internalList.add(order);
    }

    public void remove(Order order) {
        requireNonNull(order);
        if (!internalList.remove(order)) {
            throw new OrderNotFoundException();
        }
    }

    public void removeOrdersForCustomer(Index customerIndex) {
        requireNonNull(customerIndex);
        internalList.removeIf(order -> order.getCustomerIndex().equals(customerIndex));
    }

    public void setOrders(List<Order> orders) {
        internalList.setAll(orders);
    }

    public ObservableList<Order> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }
}
