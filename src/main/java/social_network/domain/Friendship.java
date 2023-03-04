package social_network.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship extends Entity<Long> {
    private final Long id1;
    private final Long id2;

    private LocalDateTime date;

    private FriendshipStatus status;

    public Friendship(Long user1, Long user2, LocalDateTime date) {
        this.id1 = user1;
        this.id2 = user2;
        this.date = date;
        this.status = FriendshipStatus.PENDING;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
    public Long getId1() {
        return id1;
    }

    public Long getId2() {
        return id2;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isInFriendships(User user) {
        return Objects.equals(this.id1, user) || Objects.equals(this.id2, user);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(id1, that.id1) && Objects.equals(id2, that.id2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id1, id2);
    }
}
