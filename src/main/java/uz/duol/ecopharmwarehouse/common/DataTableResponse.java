package uz.duol.ecopharmwarehouse.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTableResponse<T> {
    private List<T> data;
    private long totalElements;
    private int totalPages;
    private int currentPage;

    public DataTableResponse(Page<T> page) {
        this.data = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
    }
}
