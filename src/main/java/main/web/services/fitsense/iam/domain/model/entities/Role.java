package main.web.services.fitsense.iam.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import main.web.services.fitsense.iam.domain.model.valueobjects.Roles;

import java.util.List;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Roles name;

    public Role(Roles name) {
        this.name = name;
    }

    /** Get the name of the role as a string
     * @return the name of the role as a string
     */

    public String getStringName() {
        return name.name();
    }

    /** Get the default role
     * @return the default role
     */

    public static Role getDefaultRole() {
        return new Role(Roles.ATHLETE);
    }

    /** Get the role from its name
     * @param name the name of the role
     * @return the role
     */

    public static Role toRoleFromName(String name) {
        return new Role(Roles.valueOf(name));
    }

    /** Validate a set of roles
     * @param roles the set of roles to validate
     * @return the validated set of roles
     * <p>
     *     If the set is null or empty, it returns a set with the default role.
     * </p>
     */

    public static List<Role> validateRoleSet(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of(getDefaultRole());
        }
        return roles;
    }
}
