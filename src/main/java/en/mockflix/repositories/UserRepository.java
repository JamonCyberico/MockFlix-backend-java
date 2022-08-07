package en.mockflix.repositories;

import en.mockflix.services.UserJPADAO;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends UserJPADAO {

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}

