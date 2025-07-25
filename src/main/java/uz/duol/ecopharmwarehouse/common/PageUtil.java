package uz.duol.ecopharmwarehouse.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {
    public static Pageable getPageable(DataTableRequest request) {

        return PageRequest.of(
                request.getPage(),
                request.getSize(),
                request.getSortField() != null && request.getSortDirection() != null ? Sort.by(Sort.Direction.fromString(request.getSortDirection()), request.getSortField()) : Sort.by(Sort.Direction.DESC, "createdAt")
        );
    }
}
