package shook.shook.song.domain.killingpart;

import jakarta.persistence.AttributeConverter;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class LikeCountConverter implements AttributeConverter<AtomicInteger, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final AtomicInteger attribute) {
        return attribute.get();
    }

    @Override
    public AtomicInteger convertToEntityAttribute(final Integer dbData) {
        return new AtomicInteger(dbData);
    }
}
