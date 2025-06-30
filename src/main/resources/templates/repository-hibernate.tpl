package ${package}.repository;

import ${package}.model.${entityName};
import org.hibernate.SessionFactory;

public class ${entityName}Repository {
    private final SessionFactory sessionFactory;

    public ${entityName}Repository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // TODO: implement CRUD with Hibernate sessions
}
