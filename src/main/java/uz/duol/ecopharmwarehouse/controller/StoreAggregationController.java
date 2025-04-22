package uz.duol.ecopharmwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;
import uz.duol.ecopharmwarehouse.module.store.service.StoreAggregationService;

@RestController
@RequestMapping("/api/v1/wms/store-aggregation")
@RequiredArgsConstructor
public class StoreAggregationController {
    private final StoreAggregationService storeAggregationService;

    @PostMapping("/store-sync")
    public StoreSyncRequest storeSync(@RequestBody StoreSyncRequest request) {
        return storeAggregationService.createAndReturnBarCode(request);
    }

    @GetMapping("/store-sync/{id}")
    public StoreSyncRequest storeSync(@PathVariable Long id) {
        return storeAggregationService.findById(id);
    }

    @GetMapping("/store-sync/list")
    public Page<StoreSyncRequest> storeSyncList(@RequestParam(value = "search", required = false) String search, Pageable pageable) {
        return storeAggregationService.findAll(search, pageable);
    }

    @DeleteMapping("/store-sync/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStoreSync(@PathVariable Long id) {
        storeAggregationService.delete(id);
    }

    @PutMapping("/store-sync/{id}")
    public StoreSyncRequest updateStoreSync(@PathVariable Long id, @RequestBody StoreSyncRequest request) {
        return storeAggregationService.update(id, request);
    }
}
