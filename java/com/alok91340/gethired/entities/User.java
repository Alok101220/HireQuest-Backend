/**
 * 
 */
package com.alok91340.gethired.entities;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author alok91340
 *
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String username;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String headline;
	
	
	@OneToOne(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private Image image;
	
	private String birthdate;
	
	private String currentOccupation;
	
	private boolean status;
	
	private String phone;
	
	private int isRecuriter;
	
	@OneToOne(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private Address address;
	
	private String fcmToken;
	
	// relation with role
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
				
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return false;
	}

	@Override
	public boolean isEnabled() {

		return false;
	}

}
