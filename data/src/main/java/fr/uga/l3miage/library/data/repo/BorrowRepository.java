package fr.uga.l3miage.library.data.repo;

import fr.uga.l3miage.library.data.domain.Borrow;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class BorrowRepository implements CRUDRepository<String, Borrow> {

    private final EntityManager entityManager;

    @Autowired
    public BorrowRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Borrow save(Borrow entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Borrow get(String id) {
        return entityManager.find(Borrow.class, id);
    }

    @Override
    public void delete(Borrow entity) {
        entityManager.remove(entity);
    }

    @Override
    public List<Borrow> all() {
        return entityManager.createQuery("from Borrow", Borrow.class).getResultList();
    }

    /**
     * Trouver des emprunts en cours pour un emprunteur donné
     *
     * @param userId l'id de l'emprunteur
     * @return la liste des emprunts en cours
     */
    public List<Borrow> findInProgressByUser(String userId) {
        // TODO
        return entityManager.createQuery(
            "SELECT b FROM Borrow b WHERE b.borrower.id = :userId AND b.finished = false", Borrow.class)
            .setParameter("userId", userId)
            .getResultList();

    }

    /**
     * Compte le nombre total de livres emprunté par un utilisateur.
     *
     * @param userId l'id de l'emprunteur
     * @return le nombre de livre
     */
    public int countBorrowedBooksByUser(String userId) {
        // TODO
        return entityManager.createQuery(
            "SELECT COUNT(DISTINCT book) FROM Borrow b JOIN b.books book WHERE b.borrower.id = :userId", Long.class)
            .setParameter("userId", userId)
            .getSingleResult().intValue();
    }

    /**
     * Compte le nombre total de livres non rendu par un utilisateur.
     *
     * @param userId l'id de l'emprunteur
     * @return le nombre de livre
     */
    public int countCurrentBorrowedBooksByUser(String userId) {
        // TODO
        return entityManager.createQuery(
            "SELECT COUNT(DISTINCT book) FROM Borrow b JOIN b.books book WHERE b.borrower.id = :userId AND finished = false ", Long.class)
            .setParameter("userId", userId)
            .getSingleResult().intValue();
    }

    /**
     * Recherche tous les emprunt en retard trié
     *
     * @return la liste des emprunt en retard
     */
    public List<Borrow> foundAllLateBorrow() {
        Date now = new Date();

        return entityManager.createQuery(
                "SELECT b FROM Borrow b WHERE b.requestedReturn <= :now AND b.finished = false ORDER BY b.requestedReturn", Borrow.class)
                .setParameter("now", now)
                .getResultList();
    }

    /**
     * Calcul les emprunts qui seront en retard entre maintenant et x jours.
     *
     * @param days le nombre de jour avant que l'emprunt soit en retard
     * @return les emprunt qui sont bientôt en retard
     */
    public List<Borrow> findAllBorrowThatWillLateWithin(int days) {
        Date now = new Date();
        Calendar xxxdays = Calendar.getInstance();
        xxxdays.setTime(now);
        xxxdays.add(Calendar.DATE, days);
    
        return entityManager.createQuery(
            "SELECT b FROM Borrow b WHERE b.finished = false AND b.requestedReturn <= :dueDate", Borrow.class)
            .setParameter("dueDate", xxxdays.getTime())
            .getResultList();
    }

}
