package edu.ttu.retaileye.entities;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Builder;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.Objects;
import java.util.UUID;

@Table(name = "recordings")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Recording {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @ManyToOne
    private EmployeeShift employeeShift;

    @ManyToOne
    private BodyCamera bodyCamera;

    @OneToMany(mappedBy = "recording")
    @ToString.Exclude
    private Set<Incident> incidents;

    private String filePath;
    private String fileName;
    private String fileType;
    private String fileSize;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isAnalyzed;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Recording recording = (Recording) o;
        return getId() != null && Objects.equals(getId(), recording.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
