package kitchenpos.domain.order.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.domain.support.Guest;
import kitchenpos.domain.support.Name;

import java.util.UUID;

@Table(name = "order_table")
@Entity
class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Embedded
    private OrderTableName name;

    @AttributeOverride(name = "count", column = @Column(name = "count_of_guest"))
    @Embedded
    private Guest guest;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(Name name, Guest guest) {
        this.name = new OrderTableName(name);
        this.guest = guest;
        this.occupied = false;
    }

    public OrderTable(UUID orderId, Name name, Guest guest) {
        this.orderId = orderId;
        this.name = new OrderTableName(name);
        this.guest = guest;
        this.occupied = false;
    }

    public void occupy() {
        this.occupied = true;
    }

    public void clear() {
        this.occupied = false;
    }

    public void changeCountOfGuest(int count) {
        if (!occupied) {
            throw new IllegalArgumentException("Cleared Order Table이면 손님 수를 변경할 수 없습니다.");
        }
        this.guest = new Guest(count);
    }

    public String name() {
        return name.getName();
    }

    public int countOfGuest() {
        return guest.getCount();
    }

    public boolean isOccupied() {
        return occupied;
    }
}
