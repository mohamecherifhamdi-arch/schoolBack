package com.school.management.config;

import com.school.management.service.SequenceGeneratorService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class MongoEventListener extends AbstractMongoEventListener<Object> {
    private final SequenceGeneratorService sequenceGenerator;

    public MongoEventListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object entity = event.getSource();
        var entityClass = entity.getClass();

        try {
            var idField = entityClass.getDeclaredField("id");
            idField.setAccessible(true);
            if (idField.get(entity) == null && idField.getType() == Long.class) {
                String collectionName = entityClass.getAnnotation(org.springframework.data.mongodb.core.mapping.Document.class).collection();
                long nextId = sequenceGenerator.generateSequence(collectionName);
                idField.set(entity, nextId);
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }
}
