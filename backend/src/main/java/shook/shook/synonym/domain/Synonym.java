package shook.shook.synonym.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "synonym")
@Entity
public class Synonym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "value", nullable = false)
    private String value;

    private Synonym(final Long id, final Long targetId, final Type type, final String value) {
        this.id = id;
        this.targetId = targetId;
        this.type = type;
        this.value = value;
    }

    public Synonym(final Long targetId, final Type type, final String value) {
        this(null, targetId, type, value);
    }
}
