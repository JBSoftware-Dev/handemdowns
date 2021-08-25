package com.handemdowns.persistence.dao.specification;

import com.handemdowns.persistence.model.Ad;
import com.handemdowns.persistence.model.Category;
import com.handemdowns.persistence.model.Location;
import com.handemdowns.persistence.model.Status;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AdSpecification {
    public static Specification<Ad> search(String topic, Category category, Location location, Boolean willDeliver) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.like(cb.lower(root.get("title")), "%" + topic.toLowerCase() + "%"));
            predicates.add(cb.like(cb.lower(root.get("description")), "%" + topic.toLowerCase() + "%"));
            if (category != null) {
                predicates.add(cb.equal(root.get("category"), category));
            }
            if (location != null) {
                predicates.add(cb.equal(root.get("location"), location));
            }
            if (willDeliver != null) {
                predicates.add(cb.equal(root.get("willDeliver"), willDeliver));
            }
            predicates.add(cb.equal(root.get("status"), Status.ACTIVE));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}