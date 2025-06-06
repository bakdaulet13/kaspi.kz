package kaspi.kz.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import kaspi.kz.model.enums.RepairOrderStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "repair_order")
public class RepairOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;


    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private RepairOrderStatus status;

    @OneToMany(mappedBy = "repairOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<StatusHistory> historyList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RepairOrderStatus getStatus() {
        return status;
    }

    public void setStatus(RepairOrderStatus status) {
        this.status = status;
    }

    public List<StatusHistory> getHistoryList() {
        return historyList;
    }

    public void addHistory(StatusHistory history) {
        historyList.add(history);
    }

}