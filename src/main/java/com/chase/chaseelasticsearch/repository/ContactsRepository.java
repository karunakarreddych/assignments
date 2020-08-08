
package com.chase.chaseelasticsearch.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chase.chaseelasticsearch.entities.Contacts;
import com.chase.chaseelasticsearch.enums.UserType;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Serializable> {

	Contacts findByTypeId(String userName);
	Optional<Contacts> findByTypeAndTypeIdAndActive(UserType user, String username, boolean active);
	Contacts findByTypeIdAndActive(String userName, boolean b);


}
