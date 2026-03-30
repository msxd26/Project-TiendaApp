package pe.jsaire.tiendaapp.security;

public final class AuthorityConstants {

    private AuthorityConstants() {

    }

    public static final String ADMIN_ONLY = "hasAuthority('ROLE_ADMIN')";

    public static final String READ_CATALOG = "hasAnyAuthority('ROLE_ADMIN','ROLE_GERENTE','ROLE_VENDEDOR','ROLE_ALMACENERO','ROLE_CLIENTE')";

    public static final String READ_PEOPLE = "hasAnyAuthority('ROLE_ADMIN','ROLE_GERENTE','ROLE_VENDEDOR','ROLE_ALMACENERO')";

    public static final String READ_WAREHOUSE = "hasAnyAuthority('ROLE_ADMIN','ROLE_GERENTE','ROLE_ALMACENERO')";

    public static final String READ_SALES = "hasAnyAuthority('ROLE_ADMIN','ROLE_GERENTE','ROLE_VENDEDOR')";

    public static final String WRITE_ARTICLE = "hasAnyAuthority('ROLE_ADMIN','ROLE_ALMACENERO')";

    public static final String WRITE_PERSON = "hasAnyAuthority('ROLE_ADMIN','ROLE_VENDEDOR')";

    public static final String WRITE_INGRESO = "hasAnyAuthority('ROLE_ADMIN','ROLE_ALMACENERO')";

    public static final String WRITE_VENTA = "hasAnyAuthority('ROLE_ADMIN','ROLE_VENDEDOR')";

    public static final String ROLE_ASSIGN = "hasAnyAuthority('ROLE_ADMIN','ROLE_INVITADO')";
}
