package sst.swam.grocify.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    // Admin-specific functionality can be added later
}
