package ua.com.smiddle.task.core.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.smiddle.task.core.models.Table2;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Added by A.Osadchuk on 04.08.2016 at 13:49.
 * Project: Task
 */
@Transactional
@Repository("Table2DAO")
public class Table2DAO {
    @PersistenceContext
    private EntityManager em;

    public Table2 getTable2ByPhone(String phonenumber) {
        Query query = em.createQuery("SELECT t FROM Table2 t WHERE t.phonenumber=:phonenumber", Table2.class);
        query.setParameter("phonenumber", phonenumber);
        return (Table2) query.getSingleResult();
    }

    public Table2 mergeTable2(Table2 table) {
        return em.merge(table);
    }

    public void removeTable2(Long id){
        em.remove(em.getReference(Table2.class, id));
    }
}
