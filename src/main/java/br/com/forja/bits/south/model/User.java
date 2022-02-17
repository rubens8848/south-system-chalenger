package br.com.forja.bits.south.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String uuid;

    private Date createdAt;

    //como foi adicionado como atividade extra a validação do CPF na hora do voto,
    // ao invés de garantir um cpf válido no cadastro irei deixar para cada solicitação de voto verificar, mesmo não sendo a melhor estratégia
//    @CPF
    @Column(nullable = false)
    private String cpf;

    @Builder.Default
    private boolean accountNonLocked = true;

    @Builder.Default
    private boolean accountNonExpired = true;

    @Builder.Default
    private boolean credentialsNonExpired = true;

    @Builder.Default
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    public HashMap<String, Object> convertToResponse() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", this.username);
        return map;
    }

}
