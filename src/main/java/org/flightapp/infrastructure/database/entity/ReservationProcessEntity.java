package org.flightapp.infrastructure.database.entity;


import jakarta.persistence.*;
import lombok.*;
import org.flightapp.domain.ReservationStatus;

@Getter
@Setter
@EqualsAndHashCode(of = "processId")
@ToString(exclude = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation_process")
public class ReservationProcessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id", nullable = false)
    private Integer processId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationsEntity reservation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;
}
