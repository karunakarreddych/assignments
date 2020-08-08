package com.chase.chaseelasticsearch.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chase.chaseelasticsearch.exceptions.UserNotFoundException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class UtilService<T> {

	

	public static Claims jwtParser(String jwtToken) {
		// jwtToken = jwtToken.replace("Bearer ", "");
		String[] splitToken = jwtToken.split("\\.");
		String accessTocken = splitToken[0].replace("Bearer ", "");
		Claims claims = Jwts.parser().parseClaimsJwt(accessTocken + "." + splitToken[1] + ".").getBody();

		return claims;

	}

	public static String getUserNameFromClaim(Claims claims) {
		String userName = claims.get("preferred_username", String.class);
		if (userName == null) {
			throw new UserNotFoundException("Invalid JWT Token, Username not found");
		}
		return userName;
	}

	
	public Page<T> convertListToPaginationList(List<T> list,
			Pageable pageable) {
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > list.size() ? list.size()
				: (start + pageable.getPageSize());
		return new PageImpl<T>(start>end?list.subList(0, 0):list.subList(start, end), pageable, list.size());
	}

}
