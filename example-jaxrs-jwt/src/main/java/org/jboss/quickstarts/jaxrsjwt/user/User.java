package org.jboss.quickstarts.jaxrsjwt.user;

public class User {

    private String name;
    private String password;
    private String[] roles;

    public User( String name, String password, String... roles ) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String[] getRoles() {
        return roles;
    }
}
