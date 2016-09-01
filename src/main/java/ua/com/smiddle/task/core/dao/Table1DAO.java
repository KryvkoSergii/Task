package ua.com.smiddle.task.core.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.smiddle.task.core.models.Table1;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Added by A.Osadchuk on 04.08.2016 at 13:49.
 * Project: Task
 */
@Transactional
@Repository("Table1DAO")
public class Table1DAO {
    @PersistenceContext
    private EntityManager em;

    public List<Table1> getTable1() {
        Query query = em.createQuery("SELECT t FROM Table1 t", Table1.class);
        return (List<Table1>) query.getResultList();
    }

    public Table1 getTable1ById(Long id) {
        Query query = em.createQuery("SELECT t FROM Table1 t WHERE t.id=:id", Table1.class);
        query.setParameter("id", id);
        return (Table1) query.getSingleResult();
    }

    public Table1 mergeTable1(Table1 table) {
        return em.merge(table);
    }
}
