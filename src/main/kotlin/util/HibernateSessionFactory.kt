package util

import model.Coordinates
import model.Location
import model.Route
import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import org.hibernate.service.ServiceRegistry

class HibernateSessionFactory {
    companion object {
        var sessionFactory: SessionFactory? = null
            get() {
                if (field == null) {
                    try {
                        val configuration = Configuration()
                        configuration.apply {
                            addAnnotatedClass(Route::class.java)
                            addAnnotatedClass(Coordinates::class.java)
                            addAnnotatedClass(Location::class.java)
                        }
                        val serviceRegistry: ServiceRegistry = StandardServiceRegistryBuilder()
                            .applySettings(configuration.properties).build()

                        field = configuration.buildSessionFactory(serviceRegistry)

                        println("Hibernate instantiated")

                        return field
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return field
            }
    }
}