package org.hibernate.orm.test.jpa.criteria.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SessionFactory
@DomainModel(annotatedClasses = CriteriaFromHqlTest.Message.class)
public class CriteriaFromHqlTest {
    @Test void test(SessionFactoryScope scope) {
        scope.inSession( s -> {
            JpaCriteriaQuery<Object[]> query =
                    s.getFactory().getCriteriaBuilder()
                            .createQuery("select id, text from Msg order by id", Object[].class);
            assertEquals(2, query.getSelection().getSelectionItems().size());
            assertEquals(1, query.getOrderList().size());
            assertEquals(1, query.getRoots().size());
            s.createSelectionQuery(query).getResultList();
        });
    }
    @Entity(name="Msg")
    static class Message {
        @Id
        Long id;
        String text;
    }
}
