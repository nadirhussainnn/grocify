package sst.swam.grocify.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    // Admin does not have additional fields , infact admin will not be added to db
}
