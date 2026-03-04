package com.sagarboyal.job_platform_api.core.specification;

import com.sagarboyal.job_platform_api.core.dto.JobFilterInput;
import com.sagarboyal.job_platform_api.core.entity.Job;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public final class JobSpecifications {

    private JobSpecifications() {
    }

    public static Specification<Job> build(String search, JobFilterInput filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isBlank()) {
                String like = "%" + search.toLowerCase().trim() + "%";
                Predicate title = cb.like(cb.lower(root.get("title")), like);
                Predicate organization = cb.like(cb.lower(root.get("organization")), like);
                Predicate description = cb.like(cb.lower(root.get("description")), like);
                Predicate providerName = cb.like(cb.lower(root.get("providerName")), like);
                Predicate location = cb.like(cb.lower(root.get("jobLocation")), like);
                predicates.add(cb.or(title, organization, description, providerName, location));
            }

            if (filter != null) {
                if (filter.getProviderName() != null && !filter.getProviderName().isBlank()) {
                    predicates.add(cb.equal(
                            cb.lower(root.get("providerName")),
                            filter.getProviderName().toLowerCase().trim()
                    ));
                }

                if (filter.getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), filter.getStatus()));
                }

                if (filter.getCategory() != null && !filter.getCategory().isBlank()) {
                    predicates.add(cb.equal(
                            cb.lower(root.get("category")),
                            filter.getCategory().toLowerCase().trim()
                    ));
                }

                if (filter.getEmploymentType() != null) {
                    predicates.add(cb.equal(root.get("employmentType"), filter.getEmploymentType()));
                }

                if (filter.getLocation() != null && !filter.getLocation().isBlank()) {
                    String like = "%" + filter.getLocation().toLowerCase().trim() + "%";
                    predicates.add(cb.like(cb.lower(root.get("jobLocation")), like));
                }

                if (filter.getPostedFrom() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("postedDate"), filter.getPostedFrom()));
                }

                if (filter.getPostedTo() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("postedDate"), filter.getPostedTo()));
                }

                if (filter.getCreatedFrom() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.getCreatedFrom()));
                }

                if (filter.getCreatedTo() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filter.getCreatedTo()));
                }

                if (filter.getUpdatedFrom() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("updatedAt"), filter.getUpdatedFrom()));
                }

                if (filter.getUpdatedTo() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("updatedAt"), filter.getUpdatedTo()));
                }
            }

            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
