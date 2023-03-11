package fr.uga.l3miage.library.data.domain;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

//@Entity
public class Librarian extends Person {
    //@Column(name = "manager",nullable = true, columnDefinition = "BLOB")
    private Librarian manager;

    //@OneToMany(mappedBy = "manager")
    //private Set<Librarian> subordinates;

    public Librarian getManager() {
        return manager;
    }

    public void setManager(Librarian manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Librarian librarian)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(manager, librarian.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), manager);
    }
}
