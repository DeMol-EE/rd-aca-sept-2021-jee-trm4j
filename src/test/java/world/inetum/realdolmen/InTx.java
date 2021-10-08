package world.inetum.realdolmen;

import javax.persistence.EntityManager;

public interface InTx {
    void apply(EntityManager em) throws Exception;
}
