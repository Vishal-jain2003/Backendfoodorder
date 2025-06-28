// io/ContactRequest.java
package in.vishal.foodiesapi.io;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String message;
}

